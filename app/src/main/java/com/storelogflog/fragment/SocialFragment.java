package com.storelogflog.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.apiCall.CmsApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.Utility;

import org.json.JSONException;
import org.json.JSONObject;


public class SocialFragment extends BaseFragment {

    private WebView webView;
    String url="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_msg, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        webView=view.findViewById(R.id.webview_short_des);
        ((HomeActivity)getActivity()).enableViews(false,"");


        WebSettings setting =webView.getSettings();
        setting.setJavaScriptEnabled(true);

        if(getArguments()!=null)
        {
            url=getArguments().getString("url");
            webView.loadUrl(url);

        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState!=null)
        {

        }
    }

    @Override
    public void initListeners() {


    }




}
