package com.example.demo_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private String VIDEO_URL;
    private String VIDEO_TITLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);

        VIDEO_URL = getIntent().getStringExtra("vid");
        VIDEO_TITLE = getIntent().getStringExtra("title");

        Toolbar toolbar = findViewById(R.id.playactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(VIDEO_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        playerView = findViewById(R.id.video_player);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(getApplicationContext()).build();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);

        DataSource.Factory dataSouFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,getString(R.string.app_name)));
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSouFactory).createMediaSource(
                Uri.parse(VIDEO_URL)
        );

        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleExoPlayer.release();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}