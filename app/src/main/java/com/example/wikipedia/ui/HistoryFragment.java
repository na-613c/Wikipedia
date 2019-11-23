package com.example.wikipedia.ui;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               16.11.2019             *
 ***************************************/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;
import com.example.wikipedia.RecyclerView.DataAdapter;

public class HistoryFragment extends Fragment {

    private FireBase fireBase = new FireBase();
    private DataAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.history_fragment, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.list);

        /******************** создаем адаптер *******************/

        adapter = new DataAdapter(inflater.getContext(), fireBase.getValue());
        /**************** устанавливаем для списка адаптер **************/
        recyclerView.setAdapter(adapter);

        fireBase.read(adapter);


        return v;
    }



}
