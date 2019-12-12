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

import com.example.wikipedia.Domain.Launcher;
import com.example.wikipedia.Domain.SearchWord;
import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;
import com.example.wikipedia.Request.AddSearchWord;
import com.google.android.material.textfield.TextInputLayout;

public class SearchFragment extends Fragment {

    private Button button;

    private static TextInputLayout mTextInputLayout;
    private EditText mEditText;

    private SearchWord searchWord;
    private FireBase fireBase;


    @SuppressLint("ShowToast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.search_fragment, container, false);

        button = (Button) v.findViewById(R.id.button);

        mTextInputLayout = (TextInputLayout) v.findViewById(R.id.textInputLayout);
        mEditText = (EditText) v.findViewById(R.id.editTextName);


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
        /************* Launcher *************/

        String searchStr = mEditText.getText().toString().trim();


        AddSearchWord addSearchWord = new AddSearchWord();
        addSearchWord.addWord(searchStr);

        searchWord = Launcher.searchWord;
        // Write a message to the database
        fireBase = new FireBase();
        fireBase.write(searchWord);

    }

    public static void showError() {
        mTextInputLayout.setError("Проверьте интернет соеденение");

    }

    public static void hideError() {
        mTextInputLayout.setError("");
    }

}
