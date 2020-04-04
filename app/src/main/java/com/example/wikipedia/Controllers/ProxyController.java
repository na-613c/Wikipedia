package com.example.wikipedia.Controllers;

import com.example.wikipedia.Models.SearchPageModel;

import static com.example.wikipedia.MainActivity.fireBaseController;
import static com.example.wikipedia.ui.SearchFragment.hideError;
import static com.example.wikipedia.ui.SearchFragment.oldWord;
import static com.example.wikipedia.ui.SearchFragment.showError;

public class ProxyController {

    private String url;
    private String startUrl = "w/api.php?action=query&format=json&utf8&";

    public void preparation(SearchPageModel inputData, String type) {

        switch (type) {
            case "search":
                url = startUrl + "list=search&srsearch=" + inputData.getTitle();
                break;
            case "page":
                url = startUrl + "prop=extracts&explaintext&indexpageids=1&pageids=" + inputData.getId();
                writeInFireBase(inputData);
                break;
            default:
                break;
        }

        if (!inputData.getTitle().equals("")) {
            QueryController queryController = new QueryController();
            queryController.queryApi(url, type);
            hideError();
        } else showError("Введите слово!");

    }

    private void writeInFireBase(SearchPageModel inputData) {
        if (!inputData.getTitle().equals(oldWord) & !(inputData.getTitle().equals(""))) {
            oldWord = inputData.getTitle();
            fireBaseController.write(inputData);
        }
    }
}
