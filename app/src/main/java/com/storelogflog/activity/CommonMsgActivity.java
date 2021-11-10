package com.storelogflog.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.apiCall.CmsApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.Utility;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonMsgActivity extends BaseActivity implements VolleyApiResponseString {

    public Toolbar toolbar;
    private WebView webviewShortDes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
        initListeners();
    }


    @Override
    public void initViews() {
        webviewShortDes=findViewById(R.id.webview_short_des);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent()!=null)
        {
            String id=getIntent().getStringExtra("id");
            if (id != null)
            {
                callCmsApi(id);
            }

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


    void callCmsApi(String id)
    {
        if(Utility.isInternetConnected(CommonMsgActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("id", id);
                Logger.debug(Tag,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new CmsApiCall(CommonMsgActivity.this,this,token, Constants.CMS_CODE);
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
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.CMS_CODE:
                hideLoading();
                if (response!=null)
                {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Logger.debug(Tag,""+jsonObject.toString());
                        int result=getIntFromJsonObj(jsonObject,"result");
                        if(result==1)
                        {
                            JSONObject jsonData=jsonObject.getJSONObject("Data");

                            if(jsonData!=null)
                            {
                            String shortDescription=jsonData.getString("ShortDesp");
                            String longDescription=jsonData.getString("LongDesp");
                            String title=jsonData.getString("Title");


                            toolbar.setTitle(title);

                            webviewShortDes.getSettings().setJavaScriptEnabled(true);
                            webviewShortDes.setBackgroundColor(Color.TRANSPARENT);
                            String htmlData = "<font color='white'>" + shortDescription +longDescription+ "</font>";
                            webviewShortDes.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);

                        }
                            else
                            {
                                showToast("Data not found!");
                            }
                        }
                        else
                        {
                            showToast("Data not found!");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("Something went wrong");

                    }
                }
                else
                {
                    showToast("Data not found!");
                }
                break;


        }


    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code)
        {
            case Constants.CMS_CODE:
                hideLoading();
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
