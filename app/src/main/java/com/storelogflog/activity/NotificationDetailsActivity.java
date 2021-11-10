package com.storelogflog.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.storelogflog.R;
import com.storelogflog.bean.notificationBean.Notification;

public class NotificationDetailsActivity extends BaseActivity {

    public Toolbar toolbar;
    private AppCompatTextView txtTitle;
    private AppCompatTextView txtDescription;
    private AppCompatTextView txtDateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        txtTitle = findViewById(R.id.txt_title);
        txtDateTime = findViewById(R.id.txt_date_time);
        txtDescription = findViewById(R.id.txt_description);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent()!=null)
        {
            Notification notification= (Notification) getIntent().getSerializableExtra("notification");

            toolbar.setTitle(""+notification.getTitle());
            txtTitle.setText(""+notification.getTitle());
            txtDescription.setText(""+notification.getDesp());
            txtDateTime.setText(""+notification.getDate());

        }



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void initListeners() {


    }


    @Override
    public void onClick(View view) {

    }
}
