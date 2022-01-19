package com.example.android3lesson2.data.remote;

import com.example.android3lesson2.data.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {

    @GET("/posts")
    Call<List<PostModel>> getPosts();

    @POST("/posts")
    Call<PostModel> createPosts(
            @Body PostModel postModel
    );

    @DELETE("/posts/{id}")
    Call<PostModel> deletePost(
            @Path("id") int id
    );

    @PUT("/posts/{id}")
    Call<PostModel> updatePost(
            @Path("id") int id,
            @Body PostModel post
    );

}
