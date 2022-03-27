package com.storelogflog.activity;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TutorialActivity extends BaseActivity implements VolleyApiResponseString {

    private RecyclerView rvNotification;
    private TutorialAdapter adapter;
    public Toolbar toolbar;
    private AppCompatTextView txtUserMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        rvNotification=findViewById(R.id.rv_tutorial);
        txtUserMsg=findViewById(R.id.txt_user_msg);
        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setTitle("Tutorials");

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
        if(Utility.isInternetConnected(TutorialActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new TutorialListApiCall(TutorialActivity.this,this,token, Constants.Tutorial_List);
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
            case Constants.Tutorial_List:
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

                                TutorialBean tutorialBean=new Gson().fromJson(response.toString(), TutorialBean.class);

                                if(tutorialBean!=null && tutorialBean.gettutorial()!=null && tutorialBean.gettutorial().size()>0)
                                {
                                    rvNotification.setVisibility(View.VISIBLE);
                                    txtUserMsg.setVisibility(View.GONE);

                                    adapter = new TutorialAdapter(TutorialActivity.this,tutorialBean.gettutorial());
                                    rvNotification.setLayoutManager(new LinearLayoutManager(TutorialActivity.this, LinearLayoutManager.VERTICAL, false));
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
                txtUserMsg.setText(""+Utility.returnErrorMsg(error,TutorialActivity.this));
                break;
        }
    }
}