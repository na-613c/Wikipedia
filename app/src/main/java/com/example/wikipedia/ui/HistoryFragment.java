package com.example.wikipedia.ui;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               16.11.2019             *
 ***************************************/

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Data.SearchWord;
import com.example.wikipedia.R;
import com.example.wikipedia.RecyclerView.DataAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HistoryFragment extends Fragment {

    private List<String> value = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.history_fragment, container, false);



        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.list);

        /******************** создаем адаптер *******************/

        final DataAdapter adapter = new DataAdapter(inflater.getContext(), value);
        /**************** устанавливаем для списка адаптер **************/
        recyclerView.setAdapter(adapter);



        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                SearchWord searchWordFromDb = dataSnapshot.getValue(SearchWord.class);


                if (searchWordFromDb != null) {
                    value.add(searchWordFromDb.getWord());
                }

                adapter.updateItems();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return v;
    }

}
