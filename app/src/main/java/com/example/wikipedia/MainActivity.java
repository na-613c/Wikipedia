package com.example.wikipedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.wikipedia.Controllers.DataBaseController;
import com.example.wikipedia.Controllers.FireBaseController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.ui.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static SearchPageModel searchPageModel;
    public static DataBaseController dataBaseController;
    public static FireBaseController fireBaseController;
    public static List<SearchPageModel> history;
    public static ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        searchPageModel = new SearchPageModel();
        dataBaseController = new DataBaseController();
        fireBaseController = new FireBaseController();

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        history = new ArrayList<>();


        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);//количество страниц, которые должны быть сохранены по обе стороны от текущей страницы
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}