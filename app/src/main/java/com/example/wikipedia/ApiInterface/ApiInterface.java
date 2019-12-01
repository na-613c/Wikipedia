package com.example.wikipedia.ApiInterface;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               16.11.2019             *
 ***************************************/

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;




public interface ApiInterface {

    @GET()
    //Observable<Response> getPostWithInfo(@Url String url);
    Call<String> getPostWithInfo(@Url String url);

}
