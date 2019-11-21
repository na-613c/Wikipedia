package com.example.wikipedia.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.R;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> history;

    public DataAdapter(Context context, List<String> history) {
        this.history = history;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateItems() {
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {

        String searchWord = history.get(position);

        holder.word.setText(searchWord);


    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView word;

        ViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.word);
        }
    }
}