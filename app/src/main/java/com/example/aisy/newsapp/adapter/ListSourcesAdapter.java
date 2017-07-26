package com.example.aisy.newsapp.adapter;

/**
 * Created by aisy on 25/07/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aisy.newsapp.ArticlesActivity;
import com.example.aisy.newsapp.MainActivity;
import com.example.aisy.newsapp.R;
import com.example.aisy.newsapp.Model.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListSourcesAdapter extends RecyclerView.Adapter<ListSourcesAdapter.ViewHolder> {

    private Context context;
    private List<Source.ListSource> listSources;

    public ListSourcesAdapter(Context context, List<Source.ListSource> listSources){
        this.context = context;
        this.listSources = listSources;
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgv_logo;
        private TextView txtv_name;
        private TextView txtv_id;

        public ViewHolder(View itemView){
            super(itemView);

            imgv_logo   = (ImageView) itemView.findViewById(R.id.imgv_logo);
            txtv_name   = (TextView) itemView.findViewById(R.id.txtv_name);
            txtv_id     = (TextView) itemView.findViewById(R.id.txtv_id);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_sources, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        final Source.ListSource listSource = listSources.get(position);
        holder.txtv_name.setText(listSource.getName());
        holder.txtv_id.setText(listSource.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getContext().startActivity(new Intent(getContext(),ArticlesActivity.class)
                        .putExtra("source",listSource.getId()));
            }
        });
    }

    @Override
    public int getItemCount(){
        return listSources.size();
    }

    public void setListSources(List<Source.ListSource> listSources){
        this.listSources = listSources;
        notifyDataSetChanged();
    }

}
