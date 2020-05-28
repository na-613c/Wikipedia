package com.example.wikipedia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.ui.RecyclerView.FavoritesAdapter;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.wikipedia.Controllers.DataBaseController.favoritesList;
import static com.example.wikipedia.Controllers.DataBaseController.readFromDatabase;


public class FavoritesFragment extends Fragment {
    private static FavoritesAdapter favoritesAdapter;
    private static RecyclerView recyclerView;
    private static TextView emptyResult;
    public static final String TITLE = "ИЗБРАННОЕ";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.favorites_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        emptyResult = (TextView) v.findViewById(R.id.emptyResults);

        favoritesAdapter = new FavoritesAdapter(inflater.getContext(), favoritesList);//создаем адаптер

        setAdapterFavoritesRV();
        readFromDatabase();

        return v;
    }

    public static void setAdapterFavoritesRV() {
        recyclerView.setAdapter(favoritesAdapter);//устанавливаем для списка адаптер
    }


    public static void checkIfEmpty(List<SearchPageModel> results) {
        if (results.size() == 0) {
            recyclerView.setVisibility(INVISIBLE);
            emptyResult.setVisibility(VISIBLE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            emptyResult.setVisibility(INVISIBLE);
        }
    }
}
