package com.example.wikipedia.ui;

import android.annotation.SuppressLint;
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

import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;
import com.example.wikipedia.Request.WikipediaQuery;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.wikipedia.MainActivity.searchWord;

public class SearchFragment extends Fragment {

    private Button button;
    public static String oldWord = "";
    private String searchStr;

    private static TextInputLayout mTextInputLayout;
    private EditText mEditText;


    private FireBase fireBase;


    @SuppressLint("ShowToast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.search_fragment, container, false);

        mTextInputLayout = (TextInputLayout) v.findViewById(R.id.textInputLayout);
        mEditText = (EditText) v.findViewById(R.id.editTextName);
        button = (Button) v.findViewById(R.id.button);

        mEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
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
        searchStr = mEditText.getText().toString().trim().toLowerCase();

        String[] words = searchStr.split(" ");//разделяем на массив из слов
        searchStr = "";
        for (String word : words) {
            String first = word.substring(0, 1).toUpperCase();
            String all = word.substring(1);
            searchStr += first + all + " ";
        }

        searchWord.setWord(searchStr);

        WikipediaQuery wikipediaQuery = new WikipediaQuery();
        wikipediaQuery.queryApi(searchStr);

        if (!searchStr.equals(oldWord) & !(searchStr.equals(""))) {
            fireBase = new FireBase();
            fireBase.write(searchWord);
        }
    }

    public static void showError(String message) {
        mTextInputLayout.setError(message);
    }

    public static void hideError() {
        mTextInputLayout.setError("");
    }

}
