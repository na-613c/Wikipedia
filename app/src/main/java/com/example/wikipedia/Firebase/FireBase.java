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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FireBase {

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    private List<String> value = new ArrayList<>();

    private static DataAdapter newAdapter;

    public void read(DataAdapter adapter) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        newAdapter = adapter;
        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                SearchWord searchWordFromDb = dataSnapshot.getValue(SearchWord.class);


                if (searchWordFromDb != null) {
                    value.add(searchWordFromDb.getWord());

                    Log.d("_FB__", searchWordFromDb.getWord());
                }

                newAdapter.updateItems();


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
    }

    public void delete(String searchWord) {


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = myRef.orderByChild("word").equalTo(searchWord);

        /** *********************** Удаление из бд по названию searchWord ************************/
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /** *** ***  Неопнятная фигня, которую заменю ** * * */
                for (DataSnapshot wordSnapshot : dataSnapshot.getChildren()) {
                    wordSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        newAdapter.updateItems();

    }

}
