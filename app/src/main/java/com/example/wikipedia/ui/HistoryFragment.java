package com.example.wikipedia.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Domain.SearchWord;
import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;
import com.example.wikipedia.RecyclerView.DataAdapter;

import java.util.List;

public class HistoryFragment extends Fragment {

    private static RecyclerView recyclerView;
    private static TextView emptyHistory;
    private FireBase fireBase = new FireBase();
    private DataAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.history_fragment, container, false);

        emptyHistory = (TextView) v.findViewById(R.id.emptyHistory);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);

        adapter = new DataAdapter(inflater.getContext());//создаем адаптер
        recyclerView.setAdapter(adapter);//устанавливаем для списка адаптер

        fireBase.read(adapter);

        return v;
    }

    public static void checkIfEmpty(List<SearchWord> history) {

        if (history.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyHistory.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyHistory.setVisibility(View.INVISIBLE);
        }
    }
}
