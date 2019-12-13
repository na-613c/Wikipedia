package com.example.wikipedia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wikipedia.R;


public class ResultFragment extends Fragment {

    public static TextView textView;
    public static TextView mainText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.result_fragment, container, false);

        textView = (TextView) v.findViewById(R.id.textView);
        mainText = (TextView) v.findViewById(R.id.main_text);

        return v;
    }

    public static void writeInSearchFragment(String title, String content) {
        mainText.setText(title);
        textView.setText(content);
    }
}
