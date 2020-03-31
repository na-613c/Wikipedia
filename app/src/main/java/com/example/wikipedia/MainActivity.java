package com.example.wikipedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.wikipedia.Models.InformationModel;
import com.example.wikipedia.Models.SearchWordModel;
import com.example.wikipedia.ui.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static SearchWordModel searchWordModel;
    public static InformationModel informationModel;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        searchWordModel = new SearchWordModel();
        informationModel = new InformationModel();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);

        viewPager.setOffscreenPageLimit(2);//количество страниц, которые должны быть сохранены по обе стороны от текущей страницы

        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}