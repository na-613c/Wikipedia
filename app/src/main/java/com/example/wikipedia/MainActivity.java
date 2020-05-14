package com.example.wikipedia;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;

public class MainActivity extends AppCompatActivity {

    public static SearchPageModel searchPageModel;
    public static DataBaseController dataBaseController;
    public static FireBaseController fireBaseController;
    public static List<SearchPageModel> history;
    public static ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private NavigationTabStrip mNavigationTabStrip;
    public static List<Integer> pageList = new ArrayList<Integer>();
    public static Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf");
        addPageList(0);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        myContext = this;
        searchPageModel = new SearchPageModel();
        dataBaseController = new DataBaseController();
        fireBaseController = new FireBaseController();
        history = new ArrayList<>();

        mNavigationTabStrip = findViewById(R.id.nts_center);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);//количество страниц, которые должны быть сохранены по обе стороны от текущей страницы

        viewPager.setAdapter(sectionsPagerAdapter);
        mNavigationTabStrip.setViewPager(viewPager);
        mNavigationTabStrip.setTitles(SearchFragment.TITLE, ResultFragment.TITLE, HistoryFragment.TITLE, FavoritesFragment.TITLE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                addPageList(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void Exit() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {

        if (pageList.size() < 2) {
            Log.d("__IF__", "size() ==" + 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);
            builder.setTitle("Выйти?");  // заголовок
            builder.setMessage("Вы действительно хотите выйти?"); // сообщение

            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Exit();
                }
            });
            builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(true);


            AlertDialog alert = builder.create();
//            alert.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(DialogInterface arg0) {
//                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(BLACK);
//                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(BLACK);
//                }
//            });

            alert.show();

        } else {
            viewPager.setCurrentItem(pageList.get(1));
            pageList.remove(0);
            pageList.remove(0);
        }
    }

    public static void addPageList(Integer page) {
        pageList.add(0, page);
    }
}