package com.storelogflog.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.activity.CommonMsgActivity;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.apiCall.CmsApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.Utility;

import org.json.JSONException;
import org.json.JSONObject;


public class CommonMsgFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    private WebView webviewShortDes;
    String id="";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_common_msg, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        webviewShortDes=view.findViewById(R.id.webview_short_des);
        ((HomeActivity)getActivity()).enableViews(false,"");

        if(getArguments()!=null)
        {
            id=getArguments().getString("id");
            callCmsApi();
        }

    }

    @Override
    public void initListeners() {


    }


    @Override
    public void onClick(View view) {

    }


    void callCmsApi()
    {
        if(Utility.isInternetConnected(getContext()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("id", id);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new CmsApiCall(getContext(),this,token, Constants.CMS_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getContext(),"No Internet Connection");
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


                                ((HomeActivity)getActivity()).enableViews(true,title);

                                webviewShortDes.getSettings().setJavaScriptEnabled(true);
                                webviewShortDes.setBackgroundColor(Color.TRANSPARENT);
                                String htmlData = "<font color='white'>" + shortDescription +longDescription+ "</font>";
                                webviewShortDes.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);

                            }
                            else
                            {
                                showToast(getContext(),"Data not found!");
                            }
                        }
                        else
                        {
                            showToast(getContext(),"Data not found!");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast(getContext(),"Something went wrong");

                    }
                }
                else
                {
                    showToast(getContext(),"Data not found!");
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
}
