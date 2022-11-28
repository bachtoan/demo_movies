package com.example.demo_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo_movies.Adapter.CastAdapter;
import com.example.demo_movies.Adapter.PartAdapter;
import com.example.demo_movies.Model.CastModel;
import com.example.demo_movies.Model.PartModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private List<CastModel> castModels;
    private List<PartModel> partModels;
    private CastAdapter castAdapter;
    private PartAdapter partAdapter;

    private RecyclerView part_recycle_view, cast_recycle_view;

    private ImageView thumb,cover;
    private TextView title,desc;
    private FloatingActionButton actionButton;
    private String title_movies;
    private String des_movies;
    private String thumb_movies;
    private String link_movies;
    private String cover_movies;
    private String cast_movies;
    private String trailer_movies;

    public static String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        thumb = findViewById(R.id.thumb);
        cover =findViewById(R.id.cover);
        title = findViewById(R.id.title_details);
        desc = findViewById(R.id.tv_desc);
        actionButton = findViewById(R.id.floatingActionButton2);
        part_recycle_view = findViewById(R.id.recyclerView_parts);
        cast_recycle_view = findViewById(R.id.recyclerView_casts);


        title_movies = getIntent().getStringExtra("title");
        des_movies = getIntent().getStringExtra("desc");
        thumb_movies= getIntent().getStringExtra("thumb");
        link_movies= getIntent().getStringExtra("link");
        cover_movies= getIntent().getStringExtra("cover");
        cast_movies= getIntent().getStringExtra("cast");
        trailer_movies= getIntent().getStringExtra("t_link");

//        Log.d("bbbb", "onCreate: "+trailer_movies);
//        Log.d("bbbb", "onCreate: "+des_movies);
//        Log.d("bbbb", "onCreate: "+title_movies);
//        Log.d("bbbb", "onCreate: "+thumb_movies);
//        Log.d("bbbb", "onCreate: "+link_movies);
//        Log.d("bbbb", "onCreate: "+cover_movies);
//        Log.d("bbbb", "onCreate: "+cast_movies);

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title_movies);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(cover_movies).into(thumb);
        Glide.with(this).load(thumb_movies).into(cover);

        thumb.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        cover.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));

        title.setText(title_movies);
        desc.setText(des_movies);



        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference();

                // truy vấn đến link => trailer_movie chính là chuỗi ftlink
                myRef.child("link").child(trailer_movies).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String vidUrl = snapshot.getValue(String.class);
                        Intent intent = new Intent(DetailsActivity.this,PlayActivity.class);
                        intent.putExtra("vid",vidUrl);
                        Log.d("bbbb", "onCreate: "+ vidUrl);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailsActivity.this, "Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
        loadPart();
        loadCast();
        
    }

    private void loadPart() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference partRef = database.getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        part_recycle_view.setLayoutManager(layoutManager);

        partModels = new ArrayList<>();
        partAdapter = new PartAdapter(partModels);
        part_recycle_view.setAdapter(partAdapter);
        Log.d("bbbb", "loadPart: da load ");

        partRef.child("link").child(link_movies).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot content:snapshot.getChildren()){
                    PartModel partModel = content.getValue(PartModel.class);
                    partModels.add(partModel);
                }
                partAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCast() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference castRef = database.getReference();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        cast_recycle_view.setLayoutManager(layoutManager);

        castModels = new ArrayList<>();
        castAdapter = new CastAdapter(castModels);
        cast_recycle_view.setAdapter(castAdapter);

        castRef.child("cast").child(cast_movies).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot content:snapshot.getChildren()){
                    CastModel castModel = content.getValue(CastModel.class);
                    castModels.add(castModel);
//                    Log.d("bbbb", "onDataChange: " +castModel.getCname());
//                    Log.d("bbbb", "onDataChange: " +castModel.getCurl());
                }
                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}