package com.example.wikipedia.ui;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               20.11.2019             *
 ***************************************/

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wikipedia.Data.Launcher;
import com.example.wikipedia.Data.RequestInformation;
import com.example.wikipedia.Data.SearchWord;
import com.example.wikipedia.R;
import com.example.wikipedia.ApiInterface.ApiInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchFragment extends Fragment {

    private Button button;
    private EditText editText;
    private TextView textView;
    private TextView mainText;

    private SearchWord searchWord;
    private RequestInformation requestInformation;
    private Boolean firstPerformance = true;

    private Call<String> call;
    private String url;
    private String title;
    private String extract;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search_fragment, container, false);
        button = (Button) v.findViewById(R.id.button);
        editText = (EditText) v.findViewById(R.id.edit_text);
        textView = (TextView) v.findViewById(R.id.textView);
        mainText = (TextView) v.findViewById(R.id.main_text);


        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    startQuery();

                    return true;
                }
                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startQuery();

            }
        });

        return v;
    }

    private void startQuery(){
        Launcher.init();

        searchWord = Launcher.searchWord;
        requestInformation = Launcher.requestInformation;

        String searchStr = editText.getText().toString();
        searchWord.setWord(searchStr);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        SearchWord wordForDB = new SearchWord();
        wordForDB.setWord(searchStr);
        myRef.push().setValue(wordForDB);

        query(searchWord.getWord());
    }


    private void query(String searchTermForQuery) {

        retrofit2.Retrofit retrofit;

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://ru.wikipedia.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

//&exintro
        url = "w/api.php?format=json&utf8&action=query&prop=extracts&explaintext&indexpageids=1&titles=" + searchTermForQuery;

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        call = apiInterface.getPostWithID(url);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        String jsonresponse = response.body();

                        writeTv(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                } else Log.i("________ERR", "DISCONECT");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


    @SuppressLint("SetTextI18n")
    private void writeTv(String response) {


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
                //                Log.d("__RETROFIT_", extract + '\n');


                /******************************  Если пустой extract  ***************************/

                if (requestInformation.getExtract().equals("")) {
                    Log.d("____2", "requestInformation.getExtract().equals(\"\")");
                    /******************************  запускаем 1 раз  ***************************/
                    if (firstPerformance) {
                        Log.d("____3", "firstPerformance");
                        firstPerformance = false;
                        query(searchWord.getWord() + "_(значения)");
                    }

                }
            }

            mainText.setText(requestInformation.getTitle());
            textView.setText(requestInformation.getExtract());

        } catch (JSONException e) {
            requestInformation.setTitle("Ошибка!");
            requestInformation.setExtract("Напишите искомое слово");
            Log.d("________", e + "");
        }


        mainText.setText(requestInformation.getTitle());
        textView.setText(requestInformation.getExtract());

    }



}
