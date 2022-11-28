package com.example.demo_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.demo_movies.Adapter.FeaturedAdapter;
import com.example.demo_movies.Adapter.ReviewFAdapter;
import com.example.demo_movies.Adapter.SliderAdapter;
import com.example.demo_movies.Model.DataModel;
import com.example.demo_movies.Model.FearturedModel;
import com.example.demo_movies.Model.ReviewFModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference();
    private List<DataModel> dataModels;
    private List<FearturedModel> fearturedModels;
    private List<ReviewFModel> reviewFModels;

    private SliderAdapter sliderAdapter;
    private RecyclerView featuredRecyclerView;
    private RecyclerView reviewFRecyclerView;

    private FeaturedAdapter featuredAdapter;
    private ReviewFAdapter reviewFAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movies from Toan");

        FirebaseApp.initializeApp(this);

        SliderView view = findViewById(R.id.sliderView);

        sliderAdapter = new SliderAdapter(MainActivity.this);
        view.setSliderAdapter(sliderAdapter);
        view.setIndicatorAnimation(IndicatorAnimationType.WORM);
        view.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        view.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        view.setScrollTimeInSec(6);
        view.setAutoCycle(true);
        renewItems(view);

        //load data from firebase
        
        loadFireBaseForSlider();
        loadFeaturedData();
        loadReviewFData();
    }


    private void loadData() {
        loadFeaturedData();

    }

    private void loadReviewFData() {
        DatabaseReference Fref = database.getReference();
        reviewFRecyclerView = findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        reviewFRecyclerView.setLayoutManager(layoutManager);

        reviewFModels = new ArrayList<>();
        reviewFAdapter = new ReviewFAdapter(reviewFModels);
        reviewFRecyclerView.setAdapter(reviewFAdapter);

        Fref.child("review").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentDataSnapshot: snapshot.getChildren()){
                    ReviewFModel dataModel = contentDataSnapshot.getValue(ReviewFModel.class);
                    reviewFModels.add(dataModel);
                }
                reviewFAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadFeaturedData() {
        DatabaseReference Fref = database.getReference();
        featuredRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        featuredRecyclerView.setLayoutManager(layoutManager);

        fearturedModels = new ArrayList<>();
        featuredAdapter = new FeaturedAdapter(fearturedModels);
        featuredRecyclerView.setAdapter(featuredAdapter);

        Fref.child("featured").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentDataSnapshot: snapshot.getChildren()){
                    FearturedModel dataModel = contentDataSnapshot.getValue(FearturedModel.class);
                    fearturedModels.add(dataModel);
                }
                featuredAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadFireBaseForSlider() {
        myRef.child("trailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot contentSlider : dataSnapshot.getChildren()){
                    DataModel sliderItem = contentSlider.getValue(DataModel.class);
                    Log.d("zzzz", "onDataChange: "+sliderItem.getTurl());
                    dataModels.add(sliderItem);
                }
                sliderAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void renewItems(View view) {
        dataModels = new ArrayList<>();
        DataModel dataItems = new DataModel();
        dataModels.add(dataItems);
        sliderAdapter.renewItems(dataModels);
        sliderAdapter.deleteItems(0);
    }
}