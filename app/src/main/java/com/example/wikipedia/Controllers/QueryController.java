package com.example.wikipedia.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wikipedia.Models.ApiInterface;
import com.example.wikipedia.ui.SearchFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

class QueryController {

    private String err = "java.net.UnknownHostException: Unable to resolve host \"ru.wikipedia.org\": No address associated with hostname";
    private Call<String> call;

    void queryApi(String url, String type) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ru.wikipedia.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        call = apiInterface.getPostWithInfo(url);
        call.enqueue(new Callback<String>() { //enqueue() для асинхронного вызова

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body();

                        SearchFragment.hideError();
                        //Log.d("__jsonresponse__", jsonresponse);

                        ParseController parseController = new ParseController();
                        parseController.searchInJSON(jsonresponse, type);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if (t.toString().equals(err)) {
                    queryApi(url, type);
                    SearchFragment.showError("Проверьте интернет соеденение!");
                }
            }
        });

    }

}
