package com.example.wikipedia.Controllers;

import androidx.annotation.NonNull;

import com.example.wikipedia.Models.SearchPageModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import static com.example.wikipedia.MainActivity.history;
import static com.example.wikipedia.MainActivity.timeTester;
import static com.example.wikipedia.ui.HistoryFragment.setAdapterHistoryRV;

public class FireBaseController {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    public void read() {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                SearchPageModel searchWordFromDb = dataSnapshot.getValue(SearchPageModel.class);

                try {
                    timeTester.endTime();
                } catch (Exception e) {
                }

                if (searchWordFromDb != null) {
                    history.add(0, searchWordFromDb);

                    setAdapterHistoryRV();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                /******************************* обновление при удалении из БД ***************************/
//                searchWordFromDb = dataSnapshot.getValue(SearchPageModel.class);
//
//                if (searchWordFromDb != null) {
//                    searchWordFromDb.setKey(dataSnapshot.getKey());
//
//                    for (int i = 0; i < value.size(); i++) {
//                        if (value.get(i).getKey().equals(searchWordFromDb.getKey())) {
//                            value.remove(i);
//                        }
//                    }
//
//                    updateOldWord();
//                    adapter.updateItems(value);
//                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /*********************** очищение БД ***********************/
//    public void delete(final String key) {
//        myRef.removeValue();
//    }
    public void write(SearchPageModel wordForDB) {
        myRef.push().setValue(wordForDB);
        timeTester.startTime();
    }

}
