package com.example.wikipedia.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    public static final int count = 4;

    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
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
    public int getCount() {
        return count;
    }
}