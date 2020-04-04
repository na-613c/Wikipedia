package com.example.wikipedia.Controllers.RecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.ProxyController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;

import java.util.List;

import static com.example.wikipedia.Controllers.DataBaseController.deleteFromDatabase;
import static com.example.wikipedia.ui.FavoritesFragment.checkIfEmpty;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<SearchPageModel> searchPageModels;


    public FavoritesAdapter(Context context, List<SearchPageModel> searchPageModels) {
        inflater = LayoutInflater.from(context);
        this.searchPageModels = searchPageModels;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_favoriters, parent, false);
        checkIfEmpty(searchPageModels);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoritesAdapter.ViewHolder holder, int position) {

        final SearchPageModel searchPageModel = searchPageModels.get(position);//SearchPageModel

        holder.title.setText(searchPageModel.getTitle());

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteFromDatabase(searchPageModel.getId());
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProxyController proxyController = new ProxyController();
                proxyController.preparation(searchPageModel, "page");
            }
        });

    }


    @Override
    public int getItemCount() {
        try {
            checkIfEmpty(searchPageModels);
            return searchPageModels.size();
        } catch (Exception e) {
            checkIfEmpty(searchPageModels);
            return 0;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageButton btn_del;
        final LinearLayout item;

        ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            item = (LinearLayout) view.findViewById(R.id.item);
            btn_del = (ImageButton) view.findViewById(R.id.btn_del);
        }
    }
}