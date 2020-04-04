package com.example.wikipedia.Controllers.RecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.ProxyController;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;

import static com.example.wikipedia.MainActivity.history;
import static com.example.wikipedia.ui.HistoryFragment.checkIfEmpty;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private LayoutInflater inflater;

    public HistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        checkIfEmpty();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, int position) {

        SearchPageModel searchPageModel = history.get(position);

        holder.word.setText(searchPageModel.getTitle());

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
            return history.size();
        } catch (Exception e) {
            return 0;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView word;
        final LinearLayout item;

        ViewHolder(View view) {
            super(view);

            word = (TextView) view.findViewById(R.id.word);
            item = (LinearLayout) view.findViewById(R.id.item);
        }
    }
}