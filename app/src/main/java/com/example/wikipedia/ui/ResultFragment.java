package com.example.wikipedia.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.DataBaseController;
import com.example.wikipedia.Controllers.RecyclerView.PageAdapter;
import com.example.wikipedia.Controllers.RecyclerView.ResultsAdapter;
import com.example.wikipedia.Models.ResultsModel;
import com.example.wikipedia.R;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.wikipedia.Controllers.ParseController.searchingResults;
import static com.example.wikipedia.MainActivity.addPageList;
import static com.example.wikipedia.MainActivity.myContext;


public class ResultFragment extends Fragment {

    private static PageAdapter pageAdapter;
    private static ResultsAdapter resultsAdapter;
    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static TextView emptyResult;
    public static final String TITLE = "РЕЗУЛЬТАТЫ";



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.result_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);

        emptyResult = (TextView) v.findViewById(R.id.emptyResults);


//        String myText = "<a href=\"http://www.google.com\">http://www.google.com</a>";
//
//        emptyResult.setText(myText);



        resultsAdapter = new ResultsAdapter(inflater.getContext(), searchingResults);//создаем адаптер
        pageAdapter = new PageAdapter(inflater.getContext(), searchingResults);//создаем адаптер

        DataBaseController.startDatabase(inflater.getContext());
        setAdapterResultRV("result");

//        Picasso.with(inflater.getContext())
//                .load("https://commons.wikimedia.org/wiki/File:Cat_poster_1.jpg")
//                .into(imageView);
        return v;
    }

    public static void checkIfEmpty(List<ResultsModel> results) {

        if (results.size() == 0) {
            recyclerView.setVisibility(INVISIBLE);
            emptyResult.setVisibility(VISIBLE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            emptyResult.setVisibility(INVISIBLE);
        }
    }

    public static void setAdapterResultRV(String type) {

        switch (type) {
            case "result":
                recyclerView.setAdapter(resultsAdapter);//устанавливаем для списка адаптер
                break;
            case "page":
                recyclerView.setAdapter(pageAdapter);//устанавливаем для списка адаптер
                break;
        }

    }

}
