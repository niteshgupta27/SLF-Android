package com.storelogflog.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.adapter.TutorialAdapter;
import com.storelogflog.apiCall.TutorialListApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.TutorialBean;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoActivity extends BaseActivity  {
    public Toolbar toolbar;
    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_layout);
        Log.e("VVF","cf");
        initViews();

    }

    @Override
    public void initViews() {


        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setTitle("Tutorials");
         videoView =(VideoView)findViewById(R.id.vdVw);
        //Set MediaController  to enable play, pause, forward, etc options.
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Location of Media File
        String videourl = getIntent().getStringExtra("urlvideo");
        Log.e("dv",videourl);
        Uri uri = Uri.parse(videourl);
        //Starting VideView By Setting MediaController and URI
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


    }

    @Override
    public void initListeners() {

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onClick(View view) {

    }
}
