package com.example.android3lesson2.ui.post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.android3lesson2.utils.App;
import com.example.android3lesson2.R;
import com.example.android3lesson2.data.model.PostModel;
import com.example.android3lesson2.databinding.FragmentPostBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostFragment extends Fragment {

    private FragmentPostBinding binding;

    private PostAdapter adapter;

    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        binding = FragmentPostBinding.inflate(
                inflater,
                container,
                false
        );
        adapter = new PostAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerPosts.setAdapter(adapter);
        setUpListener();
        getPosts();
        deletePosts();
    }

    private void deletePosts() {
        adapter.setListener(new PostAdapter.Listener() {
            @Override
            public void itemLongClick(int position) {
                PostModel post0 = adapter.getPost(position);
                if (post0.getUserId() == 0) {
                    new AlertDialog.Builder(requireContext()).setTitle("Предупреждение!").setMessage("Вы действительно хотите удалить эту запись?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    App.postApi.deletePost(getId()).enqueue(new Callback<PostModel>() {
                                        @Override
                                        public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                                            adapter.deleteItem(position);
                                        }

                                        @Override
                                        public void onFailure(Call<PostModel> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                } else {
                    Toast.makeText(requireActivity(), "Нельзя удалить запись", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void itemClick(int position) {
                openFragment(adapter.getPost(position));
            }
        });
    }

    private void getPosts() {
        App.postApi.getPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {

            }
        });
    }

    private void setUpListener() {
        binding.fub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_postFragment_to_formFragment);
            }
        });
    }

    private void openFragment(PostModel post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ololo", post);
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        controller.navigate(R.id.formFragment, bundle);
    }
}