package com.storelogflog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.activity.LoginActivity;
import com.storelogflog.apiCall.ChecktTokenApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoardFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    private Fragment fragment;
    private Bundle bundle;
    private CardView cardViewStroe;
    private CardView cardViewLog;
    private CardView cardViewFlog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews(view);
        initListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callCheckToken();
    }

    @Override
    public void initViews(View view) {

        cardViewStroe= view.findViewById(R.id.cardView_store);
        cardViewLog= view.findViewById(R.id.cardview_log);
        cardViewFlog= view.findViewById(R.id.cardview_flog);
        ((HomeActivity)getActivity()).enableViews(false,"Home");

    }

    @Override
    public void initListeners() {

        cardViewStroe.setOnClickListener(this);
        cardViewLog.setOnClickListener(this);
        cardViewFlog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cardView_store:

                fragment=new StoreFragment();
                bundle=new Bundle();
                bundle.putString("from","store");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
            case R.id.cardview_log:
                fragment=new StoreFragment();
                bundle=new Bundle();
                bundle.putString("from","log");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(),fragment,true,null);


                break;
            case R.id.cardview_flog:
                fragment=new FlogFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
        }

    }

    public void callCheckToken()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new ChecktTokenApiCall(getActivity(),this,token, Constants.CHECK_TOKEN_CODE);
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

        hideLoading();
        switch (code)
        {
            case Constants.CHECK_TOKEN_CODE:
                if (response != null) {

                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 0) {

                                logout();
                            }

                            //showToast(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

        }

    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        hideLoading();
        switch (code) {
            case Constants.CHECK_TOKEN_CODE:
                hideLoading();
                break;
        }
    }

    void logout()
    {

        //PreferenceManger.getPreferenceManger().clearSession();
        PreferenceManger.getPreferenceManger().setBoolean(PrefKeys.ISLOGIN,false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}
