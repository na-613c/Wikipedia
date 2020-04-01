package com.example.wikipedia.Controllers.RecyclerView;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikipedia.Controllers.ProxyController;
import com.example.wikipedia.Models.ResultsModel;
import com.example.wikipedia.Models.SearchPageModel;
import com.example.wikipedia.R;

import java.util.List;

import static com.example.wikipedia.ui.ResultFragment.checkIfEmpty;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ResultsModel> resultsModels;


    public ResultsAdapter(Context context,List<ResultsModel> resultsModels) {
        inflater = LayoutInflater.from(context);
        this.resultsModels = resultsModels;
    }

//    public void updateItems(List<ResultsModel> list) {
//        resultsModels = list;
//        checkIfEmpty(resultsModels);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_result, parent, false);
        checkIfEmpty(resultsModels);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ResultsAdapter.ViewHolder holder, int position) {

        final ResultsModel resultsModel = resultsModels.get(position);

        holder.title.setText(resultsModel.getTitle());
        holder.body.setText(resultsModel.getBody());


        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProxyController proxyController = new ProxyController();
                SearchPageModel searchPageModel = new SearchPageModel();

                searchPageModel.setTitle(resultsModel.getTitle());
                searchPageModel.setId(resultsModel.getPageid());

                proxyController.preparation(searchPageModel,"page");
            }
        });

    }


    @Override
    public int getItemCount() {
        try {
            Log.d("__",""+resultsModels.size());
            checkIfEmpty(resultsModels);
            return resultsModels.size();

        } catch (Exception e) {
            checkIfEmpty(resultsModels);
            return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView title, body;
        final LinearLayout item;

        ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
            item = (LinearLayout) view.findViewById(R.id.item);
        }
    }
}