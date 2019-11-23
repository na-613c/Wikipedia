package com.example.wikipedia.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wikipedia.Data.SearchWord;
import com.example.wikipedia.RecyclerView.DataAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FireBase {

    private List<SearchWord> value = new ArrayList<>();

    public List<SearchWord> getValue() {
        return value;
    }

    public void setValue(List<SearchWord> value) {
        this.value = value;
    }

    private static DataAdapter newAdapter;



    public void read(DataAdapter adapter) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        newAdapter = adapter;
        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                SearchWord searchWordFromDb = dataSnapshot.getValue(SearchWord.class);

                searchWordFromDb.setKey(dataSnapshot.getKey());

                Log.d("_FB__", searchWordFromDb.getKey());
                value.add(searchWordFromDb);


                Log.d("_FB__", searchWordFromDb.getWord());

                newAdapter.updateItems();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                newAdapter.updateItems();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void delete(final String key) {


        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


        /** *********************** Удаление из бд по названию searchWord ************************/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /** *** ***  Неопнятная фигня, которую заменю ** * * */

                myRef.child(key).removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        newAdapter.updateItems();

    }



}
