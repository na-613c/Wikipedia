package com.example.wikipedia.Request;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterface {

    @GET()
    Call<String> getPostWithInfo(@Url String url);

}
