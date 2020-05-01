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

import com.example.wikipedia.Controllers.ProxyController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;
import com.google.android.material.textfield.TextInputLayout;

public class SearchFragment extends Fragment {

    public static String oldWord = "";
    @SuppressLint("StaticFieldLeak")
    private static TextInputLayout mTextInputLayout;
    private EditText mEditText;
    public static final String TITLE = "ПОИСК";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search_fragment, container, false);

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


        return v;
    }

    private void startQuery() {
        String searchStr = mEditText.getText().toString().trim();

        SearchPageModel searchPageModel = new SearchPageModel();
        searchPageModel.setTitle(searchStr);

        ProxyController proxyController = new ProxyController();
        proxyController.preparation(searchPageModel, "search");

    }

    public static void showError(String message) {
        mTextInputLayout.setError(message);
    }

    public static void hideError() {
        mTextInputLayout.setError("");
    }

}
