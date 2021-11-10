package com.storelogflog.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.adapter.ActiveListAdapter;
import com.storelogflog.adapter.ViewOfferAdapter;
import com.storelogflog.apiCall.AcceptOfferListApiCall;
import com.storelogflog.apiCall.ActiveListApiCall;
import com.storelogflog.apiCall.ViewOfferListApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.activeListBean.ActiveListBean;
import com.storelogflog.bean.itemListBean.Auction;
import com.storelogflog.bean.offerListBean.OfferListBean;
import com.storelogflog.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;


public class ViewOffersFragment extends BaseFragment implements VolleyApiResponseString {

    private RelativeLayout rlAcceptOffers;
    private AppCompatTextView txtAcceptOffer;
    private RecyclerView rvViewOffers;
    private ViewOfferAdapter adapter;
    private String auctionId;
    private AppCompatTextView txtErrorMsg;
    private String from="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_offers, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rlAcceptOffers=view.findViewById(R.id.rl_accept_offer);
       // txtAcceptOffer=view.findViewById(R.id.txt_accept_offer);
        rvViewOffers=view.findViewById(R.id.rv_view_offers);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);

        if(getArguments()!=null)
        {
            auctionId=getArguments().getString("auctionId");
            from=getArguments().getString("From");
            String auctionName=getArguments().getString("auctionName");
            ((HomeActivity)getActivity()).enableViews(true,""+auctionName);


                    if(from.equals("Active"))
                    {
                        rlAcceptOffers.setVisibility(View.VISIBLE);
                    }
                    else if(from.equals("Archive"))
                    {
                        rlAcceptOffers.setVisibility(View.GONE);
/*
                        adapter = new ViewOfferAdapter(getActivity(),null,from);
                        rvViewOffers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        rvViewOffers.setAdapter(adapter);*/
                    }


         callViewOfferListApi();
        }

    }

    @Override
    public void initListeners() {

        rlAcceptOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter!=null)
                {
                    Logger.debug(TAG,""+adapter.getSelectedOffer());

                    if(adapter.getSelectedOffer()!=-1)
                    {
                        callAcceptOfferApi(adapter.getSelectedOffer());
                    }
                    else
                    {
                        showToast(getActivity(),"Please select offer first");
                    }
                }
                else
                {
                    showToast(getActivity(),"Please select offer first");
                }


            }
        });


    }

    void callViewOfferListApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("auction",""+auctionId);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new ViewOfferListApiCall(getContext(),this,token, Constants.VIEW_OFFER_LIST_CODE);
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


    void callAcceptOfferApi(int id)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("id",""+id);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new AcceptOfferListApiCall(getContext(),this,token, Constants.ACCEPT_OFFER_CODE);
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



    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.VIEW_OFFER_LIST_CODE:
                hideLoading();
                if (response!=null)
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
                            OfferListBean offerListBean=new Gson().fromJson(response.toString(), OfferListBean.class);
                            if (offerListBean!=null && offerListBean.getOffers()!=null && offerListBean.getOffers().size()>0)
                            {
                                adapter = new ViewOfferAdapter(getActivity(),offerListBean.getOffers(),from);
                                rvViewOffers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                rvViewOffers.setAdapter(adapter);

                                txtErrorMsg.setVisibility(View.GONE);
                                rvViewOffers.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                txtErrorMsg.setVisibility(View.VISIBLE);
                                rvViewOffers.setVisibility(View.GONE);
                            }


                        }
                        else
                        {
                            txtErrorMsg.setVisibility(View.VISIBLE);
                            rvViewOffers.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        txtErrorMsg.setVisibility(View.VISIBLE);
                        rvViewOffers.setVisibility(View.GONE);
                    }
                }
                    else
                    {
                        txtErrorMsg.setVisibility(View.VISIBLE);
                        rvViewOffers.setVisibility(View.GONE);
                    }
                }
                else
                {
                    txtErrorMsg.setVisibility(View.VISIBLE);
                    rvViewOffers.setVisibility(View.GONE);
                }

                break;


            case Constants.ACCEPT_OFFER_CODE:
                hideLoading();

                if (response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"Message");
                            if(result==1)
                            {

                                showToast(getContext(),message);
                            }
                            else
                            {
                                showToast(getContext(),message);
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
            case Constants.VIEW_OFFER_LIST_CODE:
                hideLoading();
                txtErrorMsg.setVisibility(View.VISIBLE);
                rvViewOffers.setVisibility(View.GONE);
                txtErrorMsg.setText(""+Utility.returnErrorMsg(error,getActivity()));
                break;
            case Constants.ACCEPT_OFFER_CODE:
                hideLoading();
                break;
        }
    }

}
