package com.storelogflog.uk.activity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.adapter.NotificationsAdapter;
import com.storelogflog.uk.apiCall.NotiifcationListApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.notificationBean.NotificationBean;


import org.json.JSONException;
import org.json.JSONObject;

public class NotificationActivity extends BaseActivity implements VolleyApiResponseString {

    private RecyclerView rvNotification;
    private NotificationsAdapter adapter;
    public Toolbar toolbar;
    private AppCompatTextView txtUserMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        rvNotification=findViewById(R.id.rv_notification);
        txtUserMsg=findViewById(R.id.txt_user_msg);
        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setTitle("Notifications");

        callNotiifcationListApi();

    }

    @Override
    public void initListeners() {



    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    void callNotiifcationListApi()
    {
        if(Utility.isInternetConnected(NotificationActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new NotiifcationListApiCall(NotificationActivity.this,this,token, Constants.NOTIFICATION_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.NOTIFICATION_CODE:
                hideLoading();

                if(response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(Tag,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {

                                NotificationBean notificationBean=new Gson().fromJson(response.toString(), NotificationBean.class);

                                if(notificationBean!=null && notificationBean.getNotifications()!=null && notificationBean.getNotifications().size()>0)
                                {
                                    rvNotification.setVisibility(View.VISIBLE);
                                    txtUserMsg.setVisibility(View.GONE);

                                    adapter = new NotificationsAdapter(NotificationActivity.this,notificationBean.getNotifications());
                                    rvNotification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));
                                    rvNotification.setAdapter(adapter);

                                }
                                else
                                {

                                    rvNotification.setVisibility(View.GONE);
                                    txtUserMsg.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                rvNotification.setVisibility(View.GONE);
                                txtUserMsg.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            rvNotification.setVisibility(View.GONE);
                            txtUserMsg.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    rvNotification.setVisibility(View.GONE);
                    txtUserMsg.setVisibility(View.VISIBLE);
                }

                break;


        }

    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code)
        {
            case Constants.NOTIFICATION_CODE:
                hideLoading();
                rvNotification.setVisibility(View.GONE);
                txtUserMsg.setVisibility(View.VISIBLE);
                txtUserMsg.setText(""+Utility.returnErrorMsg(error,NotificationActivity.this));
                break;
        }
    }
}
