package com.example.wikipedia.Controllers;

import androidx.annotation.NonNull;

import com.example.wikipedia.Models.ApiInterface;
import com.example.wikipedia.Models.InformationModel;
import com.example.wikipedia.ui.SearchFragment;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.wikipedia.MainActivity.searchWordModel;

public class QueryController {

    private Boolean isFirstRequest = true;

    private String url;
    private String err = "java.net.UnknownHostException: Unable to resolve host \"ru.wikipedia.org\": No address associated with hostname";

    private Call<String> call;



    public void queryApi(final String searchTermForQuery) {


        if (isFirstRequest) {
            searchWordModel.setWord(searchTermForQuery);
        }

        Retrofit retrofit;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://ru.wikipedia.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        url = "w/api.php?format=json&utf8&action=query&prop=extracts&explaintext&indexpageids=1&titles=" + searchTermForQuery;

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        call = apiInterface.getPostWithInfo(url);

        call.enqueue(new Callback<String>() { //enqueue() для асинхронного вызова

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        SearchFragment.hideError();

                        String jsonresponse = response.body();
                        ParseInformationController parseInformationController = new ParseInformationController();
                        parseInformationController.searchInJSON(jsonresponse);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                if (t.toString().equals(err)) {
                    queryApi(searchTermForQuery);

                    SearchFragment.showError("Проверьте интернет соеденение!");
                }
            }
        });


    }

}
