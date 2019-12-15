package com.example.wikipedia.Request;


import android.annotation.SuppressLint;

import com.example.wikipedia.Domain.RequestInformation;
import com.example.wikipedia.MainActivity;
import com.example.wikipedia.ui.SearchFragment;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.wikipedia.MainActivity.searchWord;
import static com.example.wikipedia.ui.ResultFragment.writeInSearchFragment;


public class WikipediaQuery {
    private RequestInformation requestInformation;
    private JSONObject obj;

    private Boolean isFirstRequest = true;

    private String url;
    private String title;
    private String extract;
    private String resultStr;
    private String err = "java.net.UnknownHostException: Unable to resolve host \"ru.wikipedia.org\": No address associated with hostname";

    private Call<String> call;


    public void queryApi(final String searchTermForQuery) {

        requestInformation = new RequestInformation();

        if (isFirstRequest) {
            searchWord.setWord(searchTermForQuery);
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

        String strQuery = parseJSON(response, "query");//получаем весь объект JSON из ответа

        String pageids = parseJSON(strQuery, "pageids");//получаем pageids

        String strPages = parseJSON(strQuery, "pages");//получаем страницу

        String strPagesIds = parseJSON(strPages, pageids);//получаем данные с страницы c id = pageids

        if (pageids.equals("-1")) {//Если нет страницы

            title = parseJSON(strPagesIds, "title");

            writeInRequestInformation("Ошибка!", "Страница «" + searchWord.getWord() + "» не найдена");

            SearchFragment.showError("Страница «" + searchWord.getWord() + "» не найдена");

        } else {

            title = parseJSON(strPagesIds, "title");
            extract = parseJSON(strPagesIds, "extract");

            writeInRequestInformation(title, extract);

            if (requestInformation.getExtract().equals("")) {//Если пустой extract

                if (searchWord.getWord().equals("")) {// если пустое слово
                    SearchFragment.showError("Напишите искомое слово!");
                    writeInRequestInformation("𝐖𝐢𝐤𝐢𝐩𝐞𝐝𝐢𝐚", "");
                } else if (isFirstRequest) {//запускаем 1 раз
                    isFirstRequest = false;
                    queryApi(searchWord.getWord() + "_(значения)");
                }

            } else {
                MainActivity.viewPager.setCurrentItem(1);
            }
        }
        writeInSearchFragment(requestInformation.getTitle(), requestInformation.getExtract());
    }

    private String parseJSON(String str, String key) {

        try {
            obj = new JSONObject(str);

            try {
                resultStr = obj.getJSONArray(key).getString(0);
            } catch (JSONException e) {
                resultStr = obj.optString(key);
            }

        } catch (JSONException e) {
            SearchFragment.showError(e.toString());
        }

        return resultStr;
    }

    private void writeInRequestInformation(String title, String extract) {
        requestInformation.setTitle(title);
        requestInformation.setExtract(extract);
    }
}
