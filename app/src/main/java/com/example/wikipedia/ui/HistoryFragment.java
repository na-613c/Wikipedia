package com.example.wikipedia.ui;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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

        /******************** создаем адаптер *******************/

        adapter = new DataAdapter(inflater.getContext(), fireBase.getValue());
        /**************** устанавливаем для списка адаптер **************/
        recyclerView.setAdapter(adapter);
        fireBase.read(adapter);

        return v;
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void checkIfEmpty(List<SearchWord> history){

        if(history.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyHistory.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyHistory.setVisibility(View.INVISIBLE);
        }
    }
}
