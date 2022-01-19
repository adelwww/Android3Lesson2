package com.example.android3lesson2.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.android3lesson2.utils.App;
import com.example.android3lesson2.R;
import com.example.android3lesson2.data.model.PostModel;
import com.example.android3lesson2.databinding.FragmentFormBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;

    private NavController controller;

    public static final int user_id = 0;
    public static final int group_id = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);
        controller = navHostFragment.getNavController();

        setUpListener();
    }

    private void setUpListener() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });
    }

    private void createPost() {

        String title  = binding.etTitle.getText().toString();
        String content = binding.edContent.getText().toString();

        PostModel post = new PostModel(
                title,
                content,
                user_id,
                group_id
        );

        App.postApi.createPosts(post).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    controller.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {

            }
        });
    }
}