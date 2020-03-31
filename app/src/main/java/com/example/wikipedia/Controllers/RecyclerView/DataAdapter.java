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

import com.example.wikipedia.Controllers.FireBaseController;
import com.example.wikipedia.Controllers.QueryController;
import com.example.wikipedia.Models.SearchWordModel;
import com.example.wikipedia.R;

import java.util.List;

import static com.example.wikipedia.ui.HistoryFragment.checkIfEmpty;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<SearchWordModel> history;
    private FireBaseController fireBaseController;

    public DataAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void updateItems(List<SearchWordModel> list) {
        history = list;
        checkIfEmpty(history);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        checkIfEmpty(history);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder holder, int position) {

        final SearchWordModel searchWordModel = history.get(position);

        holder.word.setText(searchWordModel.getWord());

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fireBaseController = new FireBaseController();
                fireBaseController.delete(searchWordModel.getKey());// удаляем из БД

            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String searchWordStr = holder.word.getText().toString();

                QueryController queryController = new QueryController();
                queryController.queryApi(searchWordStr);
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


    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView word;
        final ImageButton btn_del;
        final LinearLayout item;

        ViewHolder(View view) {
            super(view);

            word = (TextView) view.findViewById(R.id.word);
            btn_del = (ImageButton) view.findViewById(R.id.btn_del);
            item = (LinearLayout) view.findViewById(R.id.item);
        }
    }
}