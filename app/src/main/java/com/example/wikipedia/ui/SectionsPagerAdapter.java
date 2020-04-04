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
            case 1:
                return new ResultFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new FavoritesFragment();
            default:
                return new SearchFragment();
        }

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
            case 3:
                return "Лучшее";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}