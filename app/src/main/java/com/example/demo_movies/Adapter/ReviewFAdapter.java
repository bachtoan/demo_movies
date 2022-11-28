package com.example.demo_movies.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_movies.DetailsActivity;
import com.example.demo_movies.Model.FearturedModel;
import com.example.demo_movies.Model.ReviewFModel;
import com.example.demo_movies.R;

import java.util.List;

public class ReviewFAdapter extends RecyclerView.Adapter<ReviewFAdapter.MyViewHolder> {
    private List<ReviewFModel> dataModels;

    public ReviewFAdapter(List<ReviewFModel> dataModels) {
        this.dataModels = dataModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(dataModels.get(position).getRtitle());
        Glide.with(holder.itemView.getContext()).load(dataModels.get(position).getRcover()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext(), DetailsActivity.class);

                sendDataToDetailsActivity.putExtra("title",dataModels.get(position).getRtitle());
                sendDataToDetailsActivity.putExtra("link",dataModels.get(position).getRlink());
                sendDataToDetailsActivity.putExtra("cover",dataModels.get(position).getRcover());
                sendDataToDetailsActivity.putExtra("thumb",dataModels.get(position).getRthumb());
                sendDataToDetailsActivity.putExtra("desc",dataModels.get(position).getRdes());
                sendDataToDetailsActivity.putExtra("cast",dataModels.get(position).getRcast());
                sendDataToDetailsActivity.putExtra("t_link",dataModels.get(position).getRtlink());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity)holder.itemView.getContext(),holder.imageView,
                                "imageMain");

                holder.itemView.getContext().startActivity(sendDataToDetailsActivity,optionsCompat.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.moviecard_imageView);
            textView = itemView.findViewById(R.id.movie_title);
        }
    }
}
