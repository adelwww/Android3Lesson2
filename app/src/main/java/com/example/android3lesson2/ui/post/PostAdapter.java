package com.example.android3lesson2.ui.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android3lesson2.data.model.PostModel;
import com.example.android3lesson2.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private HashMap<Integer, String> name = new HashMap<>();

    private List<PostModel> posts = new ArrayList<>();

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public PostModel getPost(int position){
        return posts.get(position);
    }

    public void deleteItem(int position){
        posts.remove(position);
        notifyItemRemoved(position);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.itemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBind(PostModel post) {
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
            binding.tvUserId.setText(String.valueOf(post.getUserId()));
        }
    }

    public interface Listener {
        void itemClick(int position);

        void itemLongClick(int position);
    }
}
