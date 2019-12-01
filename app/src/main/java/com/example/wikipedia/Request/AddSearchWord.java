package com.example.wikipedia.Request;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               01.12.2019             *
 ***************************************/

import com.example.wikipedia.Data.Launcher;
import com.example.wikipedia.Data.SearchWord;

public class AddSearchWord {
    private SearchWord searchWord;

    public void addWord(String word){
        Launcher.initSearchWord();

        searchWord = Launcher.searchWord;
        searchWord.setWord(word);

        WikipediaQuery wikipediaQuery = new WikipediaQuery();
        wikipediaQuery.queryApi(word);
    }
}
