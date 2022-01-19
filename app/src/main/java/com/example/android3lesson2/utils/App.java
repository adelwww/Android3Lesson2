package com.example.android3lesson2.utils;

import android.app.Application;

import com.example.android3lesson2.data.remote.PostApi;
import com.example.android3lesson2.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient client;

    public static PostApi postApi;

    @Override
    public void onCreate() {
        super.onCreate();

        client = new RetrofitClient();
        postApi = client.provideApi();
    }
}
