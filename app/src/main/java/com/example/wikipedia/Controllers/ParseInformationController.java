package com.example.wikipedia.Controllers;

import android.annotation.SuppressLint;

import com.example.wikipedia.MainActivity;
import com.example.wikipedia.ui.SearchFragment;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.wikipedia.MainActivity.informationModel;
import static com.example.wikipedia.MainActivity.searchWordModel;
import static com.example.wikipedia.ui.ResultFragment.writeInSearchFragment;

public class ParseInformationController {
    private JSONObject obj;

    private Boolean isFirstRequest = true;

    private String title;
    private String extract;
    private String resultStr;


    @SuppressLint("SetTextI18n")
    public void searchInJSON(String response) {

        String strQuery = parseJSON(response, "query");//получаем весь объект JSON из ответа

        String pageids = parseJSON(strQuery, "pageids");//получаем pageids

        String strPages = parseJSON(strQuery, "pages");//получаем страницу

        String strPagesIds = parseJSON(strPages, pageids);//получаем данные с страницы c id = pageids

        if (pageids.equals("-1")) {//Если нет страницы

            title = parseJSON(strPagesIds, "title");

            writeInRequestInformation("Ошибка!", "Страница «" + searchWordModel.getWord() + "» не найдена");

            SearchFragment.showError("Страница «" + searchWordModel.getWord() + "» не найдена");

        } else {

            title = parseJSON(strPagesIds, "title");
            extract = parseJSON(strPagesIds, "extract");

            writeInRequestInformation(title, extract);

            if (informationModel.getExtract().equals("")) {//Если пустой extract

                if (searchWordModel.getWord().equals("")) {// если пустое слово
                    SearchFragment.showError("Напишите искомое слово!");
                    writeInRequestInformation("𝐖𝐢𝐤𝐢𝐩𝐞𝐝𝐢𝐚", "");
                } else if (isFirstRequest) {//запускаем 1 раз
                    isFirstRequest = false;
                    QueryController queryController = new QueryController();
                    queryController.queryApi(searchWordModel.getWord() + "_(значения)");
                }

            } else {
                MainActivity.viewPager.setCurrentItem(1);
            }
        }
        writeInSearchFragment(informationModel.getTitle(), informationModel.getExtract());
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
        informationModel.setTitle(title);
        informationModel.setExtract(extract);
    }
}
