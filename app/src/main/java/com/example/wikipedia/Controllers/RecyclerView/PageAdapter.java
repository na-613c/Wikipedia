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

import com.example.wikipedia.Controllers.DataBaseController;
import com.example.wikipedia.MainActivity;
import com.example.wikipedia.Models.ResultsModel;
import com.example.wikipedia.R;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.wikipedia.ui.ResultFragment.checkIfEmpty;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ResultsModel> resultsModels;


    public PageAdapter(Context context, List<ResultsModel> resultsModels) {
        inflater = LayoutInflater.from(context);
        this.resultsModels = resultsModels;
    }

    @NonNull
    @Override
    public PageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_page, parent, false);
        checkIfEmpty(resultsModels);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PageAdapter.ViewHolder holder, int position) {

        final ResultsModel resultsModel = resultsModels.get(position);

        if (position != 0) {
            holder.btn.setVisibility(INVISIBLE);
            holder.title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        } else {
            holder.btn.setVisibility(VISIBLE);
            holder.title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3));

        }

        holder.title.setText(resultsModel.getTitle().toUpperCase());
        holder.body.setText(resultsModel.getBody());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DataBaseController.startDatabase(inflater.getContext());
                DataBaseController.addInDatabase(resultsModel.getPageid(), resultsModel.getTitle());
                MainActivity.viewPager.setCurrentItem(3);
            }
        });


    }


    @Override
    public int getItemCount() {
        try {
            checkIfEmpty(resultsModels);
            return resultsModels.size();
        } catch (Exception e) {
            checkIfEmpty(resultsModels);
            return 0;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title, body;
        final ImageButton btn;
        final LinearLayout linerLayout;

        ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
            btn = (ImageButton) view.findViewById(R.id.add);
            linerLayout = (LinearLayout) view.findViewById(R.id.liner_layout);
        }
    }
}
