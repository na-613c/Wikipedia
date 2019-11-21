package com.example.wikipedia.Data;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               16.11.2019             *
 ***************************************/

public class Launcher {

    public static RequestInformation requestInformation;
    public static SearchWord searchWord;

    public static void init() {
        requestInformation = new RequestInformation();
        searchWord = new SearchWord();

    }
}
