package com.storelogflog.StorageSelection.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.activity.BaseActivity;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.apiCall.CmsApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static java.security.AccessController.getContext;

public class TermsAndConditionActivity extends BaseActivity implements VolleyApiResponseString {
    String TAG = this.getClass().getSimpleName();
    private WebView webviewShortDes;
    Context mContext;
    String id;
    AppCompatTextView txt_toolbar_title;
   LinearLayout toolbar_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        mContext = this;

        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        webviewShortDes=findViewById(R.id.webview_short_des);
        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        toolbar_back = findViewById(R.id.toolbar_back);
        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
            callCmsApi();
        }
    }

    @Override
    public void initListeners() {
        toolbar_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_back:
               onBackPressed();
                break;

        }
    }



    void callCmsApi() {
        if (Utility.isInternetConnected(mContext)) {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("id", id);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new CmsApiCall(this,this,token, Constants.CMS_CODE);
                showLoading("Loading...");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast("No Internet Connection");
        }
    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code) {
            case Constants.CMS_CODE:
                hideLoading();
                if (response!=null)
                {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Logger.debug(TAG,""+jsonObject.toString());
                        int result=getIntFromJsonObj(jsonObject,"result");
                        String message=getStringFromJsonObj(jsonObject,"message");

                        if(result==1)
                        {
                            JSONObject jsonData=jsonObject.getJSONObject("Data");

                            if(jsonData!=null)
                            {
                                String shortDescription=jsonData.getString("ShortDesp");
                                String longDescription=jsonData.getString("LongDesp");
                                String title=jsonData.getString("Title");
                                txt_toolbar_title.setText(title);

                              //  ((HomeActivity)mContext).enableViews(true,title);

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

        switch (code) {
            case Constants.CMS_CODE:
                hideLoading();
                break;
        }
    }
    public String convertStandardJSONString(String data_json){
        data_json = data_json.replace("\\", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
