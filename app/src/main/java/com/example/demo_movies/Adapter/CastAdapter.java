package com.example.demo_movies.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_movies.Model.CastModel;
import com.example.demo_movies.R;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<CastModel> castModels;

    public CastAdapter(List<CastModel> castModels) {
        this.castModels = castModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cast_name.setText(castModels.get(position).getCname());
        Glide.with(holder.itemView).load(castModels.get(position).getCurl()).into(holder.cast_image);
    }

    @Override
    public int getItemCount() {
        return castModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView cast_image;
        TextView cast_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_image = itemView.findViewById(R.id.img_cast);
            cast_name = itemView.findViewById(R.id.name_cast);
        }
    }
}
