package com.example.wikipedia.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.DataBaseController;
import com.example.wikipedia.ui.RecyclerView.PageAdapter;
import com.example.wikipedia.ui.RecyclerView.ResultsAdapter;
import com.example.wikipedia.Models.ResultsModel;
import com.example.wikipedia.R;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.wikipedia.Controllers.ParseController.searchingResults;


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

        resultsAdapter = new ResultsAdapter(inflater.getContext(), searchingResults);//создаем адаптер
        pageAdapter = new PageAdapter(inflater.getContext(), searchingResults);//создаем адаптер

        DataBaseController.startDatabase(inflater.getContext());
        setAdapterResultRV("result");

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
