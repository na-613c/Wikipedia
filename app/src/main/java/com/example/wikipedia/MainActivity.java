package com.example.wikipedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.wikipedia.Controllers.DataBaseController;
import com.example.wikipedia.Controllers.FireBaseController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.ui.FavoritesFragment;
import com.example.wikipedia.ui.HistoryFragment;
import com.example.wikipedia.ui.ResultFragment;
import com.example.wikipedia.ui.SearchFragment;
import com.example.wikipedia.ui.SectionsPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
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
    private NavigationTabStrip mNavigationTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);

        searchPageModel = new SearchPageModel();
        dataBaseController = new DataBaseController();
        fireBaseController = new FireBaseController();


        history = new ArrayList<>();

        mNavigationTabStrip = findViewById(R.id.nts_center);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);//количество страниц, которые должны быть сохранены по обе стороны от текущей страницы

        viewPager.setAdapter(sectionsPagerAdapter);
        mNavigationTabStrip.setViewPager(viewPager);
        mNavigationTabStrip.setTitles(SearchFragment.TITLE, ResultFragment.TITLE, HistoryFragment.TITLE, FavoritesFragment.TITLE);

    }
}