package com.example.wikipedia.ui;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               20.11.2019             *
 ***************************************/

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wikipedia.Data.Launcher;
import com.example.wikipedia.Data.SearchWord;
import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;
import com.example.wikipedia.Request.AddSearchWord;
import com.example.wikipedia.Request.WikipediaQuery;

public class SearchFragment extends Fragment {

    private Button button;
    private EditText editText;
    private SearchWord searchWord;


    WikipediaQuery wikipediaQuery;

    private FireBase fireBase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.search_fragment, container, false);

        button = (Button) v.findViewById(R.id.button);
        editText = (EditText) v.findViewById(R.id.edit_text);


        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    startQuery();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startQuery();
            }
        });

        return v;
    }

    private void startQuery() {
        /************* Launcher *************/

        String searchStr = editText.getText().toString().trim();


        AddSearchWord addSearchWord = new AddSearchWord();
        addSearchWord.addWord(searchStr);

        searchWord = Launcher.searchWord;
        // Write a message to the database
        fireBase = new FireBase();
        fireBase.write(searchWord);

    }




}
