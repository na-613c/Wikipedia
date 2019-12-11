package com.example.wikipedia.Domain;

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
