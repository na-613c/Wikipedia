package com.example.wikipedia.Controllers;

import android.annotation.SuppressLint;
import android.text.Html;
import android.util.Log;

import com.example.wikipedia.MainActivity;
import com.example.wikipedia.Models.ResultsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.wikipedia.ui.ResultFragment.setAdapterResultRV;
import static com.example.wikipedia.ui.SearchFragment.showError;

public class ParseController {
    private JSONObject obj;
    public static List<ResultsModel> searchingResults = new ArrayList<>();
    private List<String> finalData = new ArrayList<>();
    private String resultStr;

    @SuppressLint("SetTextI18n")
    void searchInJSON(String response, String type) {
        searchingResults.clear();

        switch (type) {
            case "search":
                this.parseSearch(response);
                break;
            case "page":
                this.parsePage(response);
                break;
            default:
                break;
        }
    }

    private void parsePage(String response) {
        String strQuery = parseJSON(response, "query");//получаем весь объект JSON из ответа

        String pageids = parseJSON(strQuery, "pageids");//получаем pageids

        String strPages = parseJSON(strQuery, "pages");//получаем страницу

        String strPagesIds = parseJSON(strPages, pageids);//получаем данные с страницы c id = pageids

        String title = parseJSON(strPagesIds, "title");
        String extract = parseJSON(strPagesIds, "extract");

        listParsing(extract);

        if (finalData.size() % 2 == 0) finalData.add("");

        ResultsModel startResultsModel = new ResultsModel(title, Integer.parseInt(pageids), finalData.get(0));
        searchingResults.add(startResultsModel);

        for (int i = 1; i < finalData.size(); i += 2) {
            ResultsModel resultsModel = new ResultsModel(finalData.get(i), Integer.parseInt(pageids), finalData.get(i + 1));
            searchingResults.add(resultsModel);
        }

        setAdapterResultRV("page");
        MainActivity.viewPager.setCurrentItem(1);

    }

    private void listParsing(String extract) {
        String[] arr4 = extract.split("====");

        for (String element4 : arr4) {
            String[] arr3 = element4.split("===");

            for (String element3 : arr3) {
                String[] arr2 = element3.split("==");

                for (String element2 : arr2) {
                    String element = Html.fromHtml(element2).toString();
                    finalData.add(element);
                }
            }

        }
    }

    private void parseSearch(String response) {

        String strQuery = parseJSON(response, "query");//получаем весь объект JSON из ответа

        String searchinfo = parseJSON(strQuery, "searchinfo");
        String error = parseJSON(searchinfo, "totalhits");

        if (error.equals("0")) {
            showError("Совпадений не найдено");
            setAdapterResultRV("result");
        } else {
            try {
                obj = new JSONObject(strQuery);//получаем search
                JSONArray arrayOfResults = obj.getJSONArray("search");

                for (int i = 0; i < arrayOfResults.length(); i++) {

                    String tmpObj = arrayOfResults.getString(i);

                    String  title = parseJSON(tmpObj, "title");
                    int pageid = Integer.parseInt(parseJSON(tmpObj, "pageid"));
                    String snippet = parseJSON(tmpObj, "snippet");
                    String body = "..." + Html.fromHtml(snippet).toString() + "...";

                    Log.d("snippet", snippet);

                    ResultsModel resultsModel = new ResultsModel(title, pageid, body);
                    searchingResults.add(resultsModel);
                }

            } catch (Exception ignored) {
            }

            setAdapterResultRV("result");
            MainActivity.viewPager.setCurrentItem(1);
        }


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
            showError(e.toString());
        }

        return resultStr;
    }

}
