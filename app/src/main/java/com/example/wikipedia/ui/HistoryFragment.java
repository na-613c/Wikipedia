package com.example.wikipedia.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.RecyclerView.HistoryAdapter;
import com.example.wikipedia.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.wikipedia.MainActivity.addPageList;
import static com.example.wikipedia.MainActivity.fireBaseController;
import static com.example.wikipedia.MainActivity.history;


public class HistoryFragment extends Fragment {

    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static TextView emptyHistory;
    private static HistoryAdapter adapter;
    public static final String TITLE = "ИСТОРИЯ";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.history_fragment, container, false);

        emptyHistory = (TextView) v.findViewById(R.id.emptyHistory);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);

        adapter = new HistoryAdapter(inflater.getContext());//создаем адаптер
        setAdapterHistoryRV();

        fireBaseController.read();
        return v;
    }

    public static void setAdapterHistoryRV() {
        recyclerView.setAdapter(adapter);//устанавливаем для списка адаптер
    }

    public static void checkIfEmpty() {
        if (history.size() == 0) {
            recyclerView.setVisibility(INVISIBLE);
            emptyHistory.setVisibility(VISIBLE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            emptyHistory.setVisibility(INVISIBLE);
        }
    }
}
