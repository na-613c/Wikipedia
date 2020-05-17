package com.example.wikipedia.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wikipedia.Controllers.ProxyController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.wikipedia.MainActivity.addPageList;
import static com.example.wikipedia.MainActivity.myContext;

public class SearchFragment extends Fragment {

    public static String oldWord = "";
    @SuppressLint("StaticFieldLeak")
    private static TextInputLayout mTextInputLayout;
    private EditText mEditText;
    public static final String TITLE = "ПОИСК";
    public static Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search_fragment, container, false);

        context = getActivity();
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
        showToast();
        SearchPageModel searchPageModel = new SearchPageModel();
        searchPageModel.setTitle(searchStr);

        ProxyController proxyController = new ProxyController();
        proxyController.preparation(searchPageModel, "search");

    }

    public static void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private static void showToast() {
        Toast toast = Toast.makeText(myContext,
                "Поиск начался...",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showError(String message) {
        mTextInputLayout.setError(message);
    }

    public static void hideError() {
        mTextInputLayout.setError("");
    }

}
