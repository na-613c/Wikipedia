package com.example.wikipedia.Request;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.wikipedia.Domain.Launcher;
import com.example.wikipedia.Domain.RequestInformation;
import com.example.wikipedia.Domain.SearchWord;
import com.example.wikipedia.ui.SearchFragment;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.wikipedia.ui.ResultFragment.writeInSearchFragment;


public class WikipediaQuery {
    private RequestInformation requestInformation;
    private Boolean firstPerformance = true;

    private String url;
    private String title;
    private String extract;
    private SearchWord searchWord;
    private Call<String> call;
    private String resultStr;
    private JSONObject obj;

    private String err = "java.net.UnknownHostException: Unable to resolve host \"ru.wikipedia.org\": No address associated with hostname";


    public void queryApi(String searchTermForQuery) {
        /************* Launcher *************/
        Launcher.initRequestInformation();
        /************* Launcher *************/

        requestInformation = new RequestInformation();
        searchWord = Launcher.searchWord;

        Retrofit retrofit;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://ru.wikipedia.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        url = "w/api.php?format=json&utf8&action=query&prop=extracts&explaintext&indexpageids=1&titles=" + searchTermForQuery;

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        call = apiInterface.getPostWithInfo(url);


        call.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        SearchFragment.hideError();

                        String jsonresponse = response.body();

                        searchInJSON(jsonresponse);

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                if (t.toString().equals(err)) {
                    queryApi(searchTermForQuery);

                     SearchFragment.showError("Проверьте интернет соеденение!");

                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void searchInJSON(String response) {

        /************** получаем весь объект JSON из ответа *********/

        String strQuery = parseJSON(response, "query");

        /************************* получаем pageids **********************/

        String pageids = parseJSON(strQuery, "pageids");

        /************************* получаем страницу **********************/

        String strPages = parseJSON(strQuery, "pages");

        /************************* получаем данные с страницы c id = pageids **********************/
        String strPagesIds = parseJSON(strPages, pageids);

        /******************************  Если нет страницы ***************************/

        if (pageids.equals("-1")) {

            title = parseJSON(strPagesIds, "title");

            if (searchWord.getWord().equals("")) {
                SearchFragment.showError("Напишите искомое слово!");
                requestInformation.setTitle("𝐖𝐢𝐤𝐢𝐩𝐞𝐝𝐢𝐚");
            } else {
                requestInformation.setTitle("Ошибка!");
                requestInformation.setExtract("Страница «" + searchWord.getWord() + "» не найдена");
            }

        } else {

            title = parseJSON(strPagesIds, "title");
            extract = parseJSON(strPagesIds, "extract");

            requestInformation.setTitle(title);
            requestInformation.setExtract(extract);

            /******************************  Если пустой extract  ***************************/

            if (requestInformation.getExtract().equals("")) {
                Log.d("____2", "requestInformation.getExtract().equals(\"\")");
                /******************************  запускаем 1 раз  ***************************/
                if (firstPerformance) {
                    firstPerformance = false;
                    queryApi(searchWord.getWord() + "_(значения)");
                }
            }
        }
        writeInSearchFragment(requestInformation.getTitle(), requestInformation.getExtract());
    }

    private String parseJSON(String str, String key) {

        try {
            obj = new JSONObject(str);
        } catch (JSONException e) {
            Log.d("err", e.toString());
        }

        try {
            resultStr = obj.getJSONArray(key).getString(0);
        } catch (JSONException e) {
            resultStr = obj.optString(key);
        }

        return resultStr;
    }
}
