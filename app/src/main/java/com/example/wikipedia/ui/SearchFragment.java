package com.example.wikipedia.ui;

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
import com.example.wikipedia.R;
import com.google.android.material.textfield.TextInputLayout;

public class SearchFragment extends Fragment {

    private Button button;
    public static String oldWord = "";
    private String searchStr;

    private static TextInputLayout mTextInputLayout;
    private EditText mEditText;




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
        searchStr = mEditText.getText().toString().trim();

        ProxyController proxyController = new ProxyController();
        proxyController.preparation(searchStr);
        
    }

    public static void showError(String message) {
        mTextInputLayout.setError(message);
    }

    public static void hideError() {
        mTextInputLayout.setError("");
    }

}
