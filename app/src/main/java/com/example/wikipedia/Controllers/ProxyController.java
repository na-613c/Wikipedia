package com.example.wikipedia.Controllers;

import static com.example.wikipedia.MainActivity.searchWordModel;
import static com.example.wikipedia.ui.SearchFragment.oldWord;

public class ProxyController {

    private FireBaseController fireBaseController;

    public void preparation(String inputWord){
        if (!inputWord.equals("")) {
            String first = inputWord.substring(0, 1).toUpperCase();
            String all = inputWord.substring(1);
            inputWord = first + all;
        }

        searchWordModel.setWord(inputWord);

        QueryController queryController = new QueryController();
        queryController.queryApi(inputWord);

        if (!inputWord.equals(oldWord) & !(inputWord.equals(""))) {
            fireBaseController = new FireBaseController();
            fireBaseController.write(searchWordModel);
        }
    }
}
