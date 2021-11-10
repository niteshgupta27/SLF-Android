package com.storelogflog.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.adapter.ChatAdapter;
import com.storelogflog.adapter.ItemListAdapter;
import com.storelogflog.adapter.StorageListAdapter;
import com.storelogflog.apiCall.AllMsgApiCall;
import com.storelogflog.apiCall.SenMsgApiCall;
import com.storelogflog.apiCall.ViewItemApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.itemListBean.ItemListBean;
import com.storelogflog.bean.messageBean.MessageBean;
import com.storelogflog.bean.storageBean.Storage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class ChatingFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    private Fragment fragment;
    private Bundle bundle;
    private RecyclerView rvchat;
    private ChatAdapter adapter;
    private Storage storage;
    private AppCompatEditText editMsg;
    private AppCompatImageView imgSendButton;
    private ProgressBar progressBar;
    Handler handler;
    Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chating, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rvchat=view.findViewById(R.id.rv_chat);
        editMsg=view.findViewById(R.id.edit_msg);
        imgSendButton=view.findViewById(R.id.img_send_button);
        progressBar=view.findViewById(R.id.progress_bar);
        ((HomeActivity)getActivity()).enableViews(true,"Chat");

        rvchat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        if(getArguments()!=null)
        {
            storage= (Storage) getArguments().getSerializable("storage");
            if (storage!=null)
            {
                callAllMessageApi(storage);
            }

        }


        imgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(storage!=null)
                {
                    if(editMsg.getText().toString().isEmpty())
                    {
                        showToast(getActivity(),"Please type something");
                    }
                    else
                    {
                        callSendMessageApi(storage,editMsg.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
                        imgSendButton.setVisibility(View.GONE);
                    }
                }
                else
                {
                    showToast(getActivity(),"Something went wrong");
                }


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

     /*  handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (storage!=null)
                {
                    try {

                        callAllMessageApi(storage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                handler.postDelayed(this, 5000);
            }
        },5000);*/



    }


    @Override
    public void onStop() {
        super.onStop();
      //  stopTest();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void startTest() {
        handler.postDelayed(runnable,0); //wait 0 ms and run
    }

    public void stopTest() {

        if (handler !=null)
        handler.removeCallbacks(null);
    }

    @Override
    public void initListeners() {


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {


        }
    }

    void callAllMessageApi(Storage storage)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("unit",""+storage.getUnitID());
                jsonObjectPayload.put("storage",""+storage.getID());
                jsonObjectPayload.put("parentID",""+storage.getChatParentID());


                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new AllMsgApiCall(getContext(),this,token, Constants.ALL_MSG_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
           // showToast(getActivity(),"No Internet Connection");
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }



    void callSendMessageApi(Storage storage,String msg)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("unit",""+storage.getUnitID());
                jsonObjectPayload.put("storage",""+storage.getID());
                jsonObjectPayload.put("message",""+msg);
                jsonObjectPayload.put("parentID",""+storage.getChatParentID());


                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new SenMsgApiCall(getContext(),this,token, Constants.SEND_MSG_CODE);
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

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.ALL_MSG_CODE:
                hideLoading();
                if(response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {
                                MessageBean messageBean=new Gson().fromJson(response.toString(), MessageBean.class);
                                if (messageBean!=null && messageBean.getMessages() !=null && messageBean.getMessages().size()>0)
                                {
                                    adapter = new ChatAdapter(getActivity(),messageBean.getMessages());
                                    rvchat.setAdapter(adapter);
                                }
                            }
                            else
                            {

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast(getContext(),"Something went wrong!");
                        }
                    }
                }


                break;

            case Constants.SEND_MSG_CODE:
                hideLoading();

                progressBar.setVisibility(View.GONE);
                imgSendButton.setVisibility(View.VISIBLE);
                if (response!=null)
                {
                    progressBar.setVisibility(View.GONE);
                    imgSendButton.setVisibility(View.VISIBLE);
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {
                               callAllMessageApi(storage);
                               editMsg.setText("");
                            }
                            else
                            {

                            }


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

        switch (code)
        {
            case Constants.ALL_MSG_CODE:
                hideLoading();
                break;
            case Constants.SEND_MSG_CODE:
                progressBar.setVisibility(View.GONE);
                imgSendButton.setVisibility(View.VISIBLE);
                break;
        }
    }


}
