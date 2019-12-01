package com.example.wikipedia.Data;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               16.11.2019             *
 ***************************************/

public class Launcher {

    public static RequestInformation requestInformation;
    public static SearchWord searchWord;

    public static void initRequestInformation() {
        requestInformation = new RequestInformation();
    }

    public static void initSearchWord() {
        searchWord = new SearchWord();
    }
}
