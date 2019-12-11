package com.example.wikipedia.Request;

/********************************
 *      Shavlovskii Ivan        *
 *        01.12.2019            *
 *******************************/

import com.example.wikipedia.Domain.Launcher;
import com.example.wikipedia.Domain.SearchWord;

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
