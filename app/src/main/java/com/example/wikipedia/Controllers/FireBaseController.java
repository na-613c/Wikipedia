package com.example.wikipedia.Controllers;

import androidx.annotation.NonNull;

import com.example.wikipedia.Controllers.RecyclerView.HistoryAdapter;
import com.example.wikipedia.Models.SearchPageModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.wikipedia.ui.SearchFragment.oldWord;

public class FireBaseController {
    private SearchPageModel searchWordFromDb;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private List<SearchPageModel> value = new ArrayList<>();

    public void read(final HistoryAdapter adapter) {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                searchWordFromDb = dataSnapshot.getValue(SearchPageModel.class);

                if (searchWordFromDb != null) {

                    value.add(0, searchWordFromDb);
                    adapter.updateItems(value);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
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

//    public void delete(final String key) {
//        myRef.removeValue();
//    }

    public void write(SearchPageModel wordForDB) {
        myRef.push().setValue(wordForDB);
    }

}
