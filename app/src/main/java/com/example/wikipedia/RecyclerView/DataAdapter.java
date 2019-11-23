package com.example.wikipedia.RecyclerView;

/****************************************
 *      created by Shavlovskii Ivan     *
 *               23.11.2019             *
 ***************************************/

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Data.SearchWord;
import com.example.wikipedia.Firebase.FireBase;
import com.example.wikipedia.R;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<SearchWord> history;
    private FireBase fireBase;

    public DataAdapter(Context context, List<SearchWord> history) {
        this.history = history;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateItems() {
        notifyDataSetChanged();
    }

    public void deleteItems(List<SearchWord> listAfterDelete){
        history = listAfterDelete;
        updateItems();
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, int position) {

        final SearchWord searchWord = history.get(position);

        holder.word.setText(searchWord.getWord());

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fireBase = new FireBase();
                fireBase.delete(searchWord.getKey());// удаляем из БД

            }
        });

    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView word;
        final ImageButton btn_del;

        ViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.word);
            btn_del = (ImageButton) view.findViewById(R.id.btn_del);
        }
    }
}