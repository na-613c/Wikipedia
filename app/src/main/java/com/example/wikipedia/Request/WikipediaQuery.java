package com.example.wikipedia.Request;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               01.12.2019             *
 ***************************************/

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.wikipedia.ApiInterface.ApiInterface;
import com.example.wikipedia.Data.Launcher;
import com.example.wikipedia.Data.RequestInformation;
import com.example.wikipedia.Data.SearchWord;

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

//    private Observable<Response> observable;

    private String url;
    private String title;
    private String extract;
    private SearchWord searchWord;
    private Call<String> call;



//    public static String url;

    public void queryApi(String searchTermForQuery) {
/************* Launcher *************/
        Launcher.initRequestInformation();
/************* Launcher *************/

        requestInformation = new RequestInformation();
        searchWord = Launcher.searchWord;

        Retrofit retrofit;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://ru.wikipedia.org/")/*
                .addConverterFactory(GsonConverterFactory.create())     //В 2.0.0+, вам нужно явно указать, что вы хотите конвертер Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())      //RXjava2 */
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //&exintro
         url = "w/api.php?format=json&utf8&action=query&prop=extracts&explaintext&indexpageids=1&titles=" + searchTermForQuery;

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        call = apiInterface.getPostWithInfo(url);

        /** не рабочий RXjava
        observable =  apiInterface.getPostWithInfo(url);

        observable.subscribeOn(Schedulers.newThread()) //отдаем новый тред для работы в background
                .observeOn(AndroidSchedulers.mainThread()) //говорим, что обсервить хотим в main thread
                .subscribe(new Observer<Response>() {

                               @Override
                               public void onSubscribe(Disposable d) {
                                   Log.d("__rx_1","onSubscribe" + d.toString());
                               }

                               @Override
                               public void onNext(Response value) {

                                   Log.d("__rx_2","onNext"+ value.toString());
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("__rx_3","onError " + e);
                               }

                               @Override
                               public void onComplete() {
                                   Log.d("__rx_4","onComplete");
                               }
                           }
                );
*/

        call.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        String jsonresponse = response.body();

                        searchInJSON(jsonresponse);

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void searchInJSON(String response) {

        try {
            /************** получаем весь объект JSON из ответа *********/
            JSONObject obj = new JSONObject(response);
            String strQuery = obj.optString("query");

            /************************* получаем pageids **********************/

            JSONObject objQuery = new JSONObject(strQuery);
            String pageids = objQuery.getJSONArray("pageids").getString(0);
            Log.d("__RETROFIT_pageids", pageids);

            /************************* получаем страницу **********************/

            JSONObject objPages = new JSONObject(strQuery);
            String strPages = objPages.optString("pages");
            Log.d("__RETROFIT_pages", strPages);

            /************************* получаем данные с страницы c id = pageids **********************/

            JSONObject objPagesIds = new JSONObject(strPages);
            String strPagesIds = objPagesIds.optString(pageids);
            Log.d("__RETROFIT_" + pageids, strPagesIds);

            /******************************  Если нет страницы ***************************/

            if (pageids.equals("-1")) {
                Log.d("____1", "pageids.equals(\"-1\")");

                requestInformation.setTitle("Ошибка!");
                requestInformation.setExtract("Страница «" + searchWord.getWord() + "» не найдена");
            } else {
                Log.d("____1", "else pageids.equals(\"-1\")");
                JSONObject objData = new JSONObject(strPagesIds);

                title = objData.optString("title");
                extract = objData.optString("extract");

                requestInformation.setTitle(title);
                requestInformation.setExtract(extract);

                /******************************  Если пустой extract  ***************************/

                if (requestInformation.getExtract().equals("")) {
                    Log.d("____2", "requestInformation.getExtract().equals(\"\")");
                    /******************************  запускаем 1 раз  ***************************/
                    if (firstPerformance) {
                        Log.d("____3", "firstPerformance");
                        firstPerformance = false;
                        queryApi(searchWord.getWord() + "_(значения)");
                    }
                }
            }

            writeInSearchFragment(requestInformation.getTitle(),requestInformation.getExtract());


        } catch (JSONException e) {
            writeInSearchFragment("Ошибка!","Напишите искомое слово");
        }
    }


}
