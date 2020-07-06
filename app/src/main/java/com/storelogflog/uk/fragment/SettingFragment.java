package com.storelogflog.uk.fragment;

import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;



import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.GetSettingApiCall;
import com.storelogflog.uk.apiCall.SettingApiCall;
import com.storelogflog.uk.apiCall.StorageClaimApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;


public class SettingFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    private SwitchCompat switchReceiveEmail;
    private SwitchCompat switchReceiveNotification;

    String notification="N";
    String email="N";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        switchReceiveEmail=view.findViewById(R.id.switch_receive_email);
        switchReceiveNotification=view.findViewById(R.id.switch_receive_notification);


        ((HomeActivity)getActivity()).enableViews(false,"Settings");
         callGetSettingApi();

    }

    @Override
    public void initListeners() {


        switchReceiveEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    callSettingApi("Y",notification);
                    email="Y";
                }
                else
                {
                    callSettingApi("N",notification);
                    email="N";
                }
            }
        });


        switchReceiveNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    callSettingApi(email,"Y");
                    notification="Y";
                }
                else
                {
                    callSettingApi("N",notification);
                    notification="N";
                }
            }
        });


        HomeActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackClick();
            }
        });

    }

    public void hideShow()
    {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgMenu.setVisibility(View.GONE);
        HomeActivity.imgSearch.setVisibility(View.GONE);
        HomeActivity.imgBack.setVisibility(View.VISIBLE);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        hideLoading();
        if(Constants.SETTINGS_CODE==code)
        {
            if(response!=null)
            {
                String payload[]=response.split("\\.");
                if (payload[1]!=null)
                {
                    response= Utility.decoded( payload[1]);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Logger.debug(TAG,""+jsonObject.toString());
                        int result=getIntFromJsonObj(jsonObject,"result");
                        String message=getStringFromJsonObj(jsonObject,"message");
                        if(result==1)
                        {

                            showToast(getActivity(),"" + message);
                          //  Utility.commonMsgDialog(getContext(), "" + message, true, null);
                        }
                        else
                        {
                            showToast(getActivity(),"" + message);
                          //  Utility.commonMsgDialog(getContext(), "" + message, true, null);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(Constants.GET_SETTINGS_CODE==code)
        {
            if(response!=null)
            {
                String payload[]=response.split("\\.");
                if (payload[1]!=null)
                {
                    response= Utility.decoded( payload[1]);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Logger.debug(TAG,""+jsonObject.toString());
                        int result=getIntFromJsonObj(jsonObject,"result");
                        String message=getStringFromJsonObj(jsonObject,"message");
                        if(result==1)
                        {
                            String notification=getStringFromJsonObj(jsonObject,"Notification");
                            String email=getStringFromJsonObj(jsonObject,"Email");

                            if(notification.equals("Y"))
                            {
                                switchReceiveNotification.setChecked(true);
                            }
                            else if(notification.equals("N"))
                            {
                                switchReceiveNotification.setChecked(false);
                            }

                            if(email.equals("Y"))
                            {
                                switchReceiveEmail.setChecked(true);
                            }
                            else if(email.equals("N"))
                            {
                                switchReceiveEmail.setChecked(false);
                            }

                        }
                        else
                        {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        hideLoading();
        if(Constants.SETTINGS_CODE==code)
        {
            Logger.debug(TAG,""+Utility.returnErrorMsg(error,getContext()));
            showToast(getActivity(),Utility.returnErrorMsg(error,getContext()));
        }
        else if(Constants.GET_SETTINGS_CODE==code)
        {
            Logger.debug(TAG,""+Utility.returnErrorMsg(error,getContext()));
            showToast(getActivity(),Utility.returnErrorMsg(error,getContext()));
        }
    }


    void callGetSettingApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new GetSettingApiCall(getContext(),this,token, Constants.GET_SETTINGS_CODE);
              //  showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }

    void callSettingApi(String email,String notification)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("notification",notification);
                jsonObjectPayload.put("email",email);

                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new SettingApiCall(getContext(),this,token, Constants.SETTINGS_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }



}
