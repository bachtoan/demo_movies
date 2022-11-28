package com.example.demo_movies.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_movies.Model.PartModel;
import com.example.demo_movies.R;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.MyViewHolder> {
    private List<PartModel> models;

    public PartAdapter(List<PartModel> models) {
        this.models = models;
        Log.d("nnnn", "PartAdapter: da khoi tao ");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.part_name.setText(models.get(position).getPart());
        Glide.with(holder.itemView).load(models.get(position).getUrl()).into(holder.part_image);
        Log.d("nnnn", "onBindViewHolder: "+models.get(position).getPart());
        Log.d("nnnn", "onBindViewHolder: "+models.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView part_image;
        TextView part_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            part_image = itemView.findViewById(R.id.part_image);
            part_name = itemView.findViewById(R.id.part_name);
        }
    }
}
