package com.example.wikipedia.Firebase;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               23.11.2019             *
 ***************************************/

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

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

    private SearchWord searchWordFromDb;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private List<SearchWord> value = new ArrayList<>();
    public List<SearchWord> getValue() {
        return value;
    }

    public void read(final DataAdapter adapter) {

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                searchWordFromDb = dataSnapshot.getValue(SearchWord.class);

                if (searchWordFromDb != null) {
                    searchWordFromDb.setKey(dataSnapshot.getKey());

                    value.add(0, searchWordFromDb);

                    adapter.updateItems();
                    Log.d("_FB__", "добавление ");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {

                Log.d("_FB__", "изменение ");
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                searchWordFromDb = dataSnapshot.getValue(SearchWord.class);

                if (searchWordFromDb != null) {

                    searchWordFromDb.setKey(dataSnapshot.getKey());

                    Log.d("_FB__", "удаление " + searchWordFromDb.getWord());

                    for (int i = 0; i < value.size(); i++) {
                        if (value.get(i).getKey().equals(searchWordFromDb.getKey())) {
                            Log.d("_FB__", "Совпало " + value.get(i).getWord());

                            value.remove(i);

                        }
                    }
                    adapter.deleteItems(value);

                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("_FB__", "изменение приоритета");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("_FB__", "ошибка на сервере");
            }
        });


    }

    public void delete(final String key) {

        /** *********************** Удаление из бд по названию searchWord ************************/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child(key).removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

    }

    public void write(SearchWord wordForDB) {

        if (!(wordForDB.getWord().equals(""))) {
            myRef.push().setValue(wordForDB);
        }

    }

}
