package com.example.wikipedia.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SearchFragment searchFragment = new SearchFragment();
                return searchFragment;
            case 1:
                ResultFragment resultFragment = new ResultFragment();
                return resultFragment;
            case 2:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Поиск";
            case 1:
                return "Результат";
            case 2:
                return "История";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}