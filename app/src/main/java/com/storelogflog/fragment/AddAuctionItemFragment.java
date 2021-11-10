package com.storelogflog.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.model.CardViewModel;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.activity.PaymentActivity;
import com.storelogflog.adapter.AuctionCategoryAdapter;
import com.storelogflog.apiCall.ActiveListApiCall;
import com.storelogflog.apiCall.AuctionCategoryApi;
import com.storelogflog.apiCall.AuctionCheckApiCall;
import com.storelogflog.apiCall.PricingApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.AddAuctionRequestBean;
import com.storelogflog.bean.AuctionCategoryModel;
import com.storelogflog.bean.countryBean.CountryBean;
import com.storelogflog.bean.storageBean.Storage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.storelogflog.apputil.Constants.pound;

public class AddAuctionItemFragment extends BaseFragment implements VolleyApiResponseString {

    Context mContext;
    private RelativeLayout rlSubmit;
    private  CardViewModel.Item item;
    private AppCompatEditText editItemName;
    private AppCompatEditText editDescription;
    private AppCompatEditText editExpectedMinValue;
    private AppCompatTextView txt_auction_category;
    private AppCompatCheckBox cbTermAndCon;
    private AppCompatCheckBox cbAgree;
    private Fragment fragment;
    private Bundle bundle;
    int pondValue=0;
    double amount_auction = 0.0;
    String poundValue2;
    private ProgressBar progressBarDialog;
    ListView auction_category_list;
    String AuctionCategory_ID;
    Storage storage;
    AlertDialog alertDialog;
    List<AuctionCategoryModel.Auction>auctionList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_auction_item, container, false);

        mContext = getActivity();
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rlSubmit=view.findViewById(R.id.rl_submit);
        editItemName=view.findViewById(R.id.edit_item_name);
        editItemName=view.findViewById(R.id.edit_item_name);
        editDescription=view.findViewById(R.id.edit_description);
        editExpectedMinValue=view.findViewById(R.id.edit_expected_min_value);
        cbTermAndCon=view.findViewById(R.id.cb_terms_condition);
        cbAgree=view.findViewById(R.id.cb_agree);
        txt_auction_category = view.findViewById(R.id.txt_auction_category);

        ((HomeActivity)getActivity()).enableViews(true,"Item Valuation");

        if(getArguments()!=null)
        {
            item= ( CardViewModel.Item) getArguments().getSerializable("item");

            editItemName.setText(""+item.getName());
            editDescription.setText(""+item.getDesp());
            editExpectedMinValue.setText(""+item.getValue());

            storage = (Storage) getArguments().getSerializable("storage");


        }


        callPricingApi();

    }

    @Override
    public void initListeners() {

        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utility.isInternetConnected(getActivity()))
                {
                    AuctionCheck();

                }

            }
        });


        cbTermAndCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new CommonMsgFragment();
                bundle=new Bundle();
                bundle.putString("id","5");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(),fragment,true,null);
            }
        });

        txt_auction_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionCategory_dialogue();
            }
        });


    }

    private void AuctionCheck() {
        try {
            JSONObject jsonObjectPayload=new JSONObject();
            jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
            jsonObjectPayload.put("item", item.getID());

            Logger.debug(TAG,jsonObjectPayload.toString());
            String token=Utility.getJwtToken(jsonObjectPayload.toString());
            new AuctionCheckApiCall(getContext(),this,token, Constants.Auction_check);
            showLoading("Loading...");

            Log.e(TAG,"Auction_check======>"+jsonObjectPayload.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void AuctionCategory_dialogue() {
        final Dialog dialog = new Dialog(mContext);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        auction_category_list = dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle = dialog.findViewById(R.id.txt_title);
        progressBarDialog = dialog.findViewById(R.id.dialog_progress_bar);
        txtTitle.setText(mContext.getResources().getString(R.string.select_category));

        callAuctionCategoryApi();

        auction_category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               txt_auction_category.setText(auctionList.get(position).getAuctionCategoryName());

                AuctionCategory_ID = String.valueOf(auctionList.get(position).getAuctionCategoryID());

                Log.e("AuctionCategory_ID",AuctionCategory_ID);

                dialog.dismiss();



            }
        });

        dialog.show();

    }

    private void callAuctionCategoryApi() {
        if (Utility.isInternetConnected(mContext)) {
            try {
            JSONObject jsonObjectPayload=new JSONObject();

            jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
            Logger.debug(TAG,jsonObjectPayload.toString());
            String token=Utility.getJwtToken(jsonObjectPayload.toString());

            Log.e("jsonObjectPayload", String.valueOf(jsonObjectPayload));
            Log.e("token", String.valueOf(token));
           // showLoading("Loading...");
            new AuctionCategoryApi(mContext,this,token, Constants.AuctionCategory);

            progressBarDialog.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }



    void callPricingApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {

                new PricingApiCall(getActivity(),this,null, Constants.PRICING_CODE);
                showLoading("Loading...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }

    void navigatePayment()
    {
        if(Utility.isInternetConnected(getActivity()))
        {

            if (isValidate())
            {
                try {


                    AddAuctionRequestBean addAuctionRequestBean=new AddAuctionRequestBean();
                    addAuctionRequestBean.setStorageId(""+storage.getID());
                    addAuctionRequestBean.setUnitId(""+storage.getUnitID());
                    addAuctionRequestBean.setItemId(""+item.getID());
                    addAuctionRequestBean.setItemName(""+editItemName.getText().toString());
                    addAuctionRequestBean.setItemDescription(""+editDescription.getText().toString());
                    addAuctionRequestBean.setAmount(pondValue);
                    addAuctionRequestBean.setShowing_amount(String.valueOf(poundValue2));
                    addAuctionRequestBean.setExpectedValue(editExpectedMinValue.getText().toString());
                    addAuctionRequestBean.setAuction_category_id(AuctionCategory_ID);

                    startActivity(new Intent(getActivity(), PaymentActivity.class).
                            putExtra("requestData",addAuctionRequestBean).
                            putExtra("storage", storage).
                    putExtra("FROM","AddAuction"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }

    public boolean isValidate() {


        if (TextUtils.isEmpty(AuctionCategory_ID)) {
            showToast(getContext(),"Please select auction category first!");
            return false;
        }else if (editItemName.getText().toString().isEmpty()) {
             showErrorMsg(editItemName, "Item name can't be blank");
            return false;
        }
        else if (editDescription.getText().toString().isEmpty()) {
            return showErrorMsg(editDescription, "Description can't be blank");
        }
        else if (editExpectedMinValue.getText().toString().isEmpty()) {
             showErrorMsg(editExpectedMinValue, "Expected min value can't be blank");
            return false;
        }
        else if (!cbAgree.isChecked()) {

            showToast(getContext(),"Please checked I agree to pay.. checkbox");
            return false;
        }
        else if (!cbTermAndCon.isChecked()) {

            showToast(getContext(),"please checked terms and condition checkbox");
            return false;
        }
        else {
            return true;
        }

    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.ADD_AUCTION_ITEM_CODE:
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
                            String message=getStringFromJsonObj(jsonObject,"Message");
                            if(result==1)
                            {
                               fragment=new LogFragment();
                               Common.loadFragment(getActivity(),fragment,false,null);
                            }
                            else
                            {
                                showToast(getActivity(),message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;

            case Constants.PRICING_CODE:
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
                            if(result==1)
                            {
                                pondValue=getIntFromJsonObj(jsonObject,"AuctionItem");

                                Double value1 = Double.parseDouble(String.valueOf(pondValue));
                                Double value2 = Double.parseDouble("100");
                                DecimalFormat precision = new DecimalFormat("0.00");
                                Double value3 =  value1/value2;

                               // pondValue2 = value3;

                                double x = Double.parseDouble(precision.format(value3));
                              //  pondValue = (int) x;

                               Log.e("pondValue", String.valueOf(pondValue));
                                poundValue2 = precision.format(value3);
                                cbAgree.setText("I agree to pay "+pound + String.valueOf(precision.format(value3))+" to have my item valued");

                                Log.e("cbAgree", poundValue2);
                                Log.e("response", String.valueOf(response));
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;


            case Constants.AuctionCategory:
                progressBarDialog.setVisibility(View.GONE);
                if (response != null) {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,"AuctionCategory"+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            if(result==1) {
                             AuctionCategoryModel auctionCategoryModel = new Gson().fromJson(response, AuctionCategoryModel.class);
                                auctionList = auctionCategoryModel.getAuctions();
                                if (auctionList.size()>0){
                                AuctionCategoryAdapter auctionCategoryAdapter = new AuctionCategoryAdapter(mContext,auctionCategoryModel.getAuctions());
                                auction_category_list.setAdapter(auctionCategoryAdapter);
                            }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;


            case Constants.Auction_check:
                hideLoading();

                if(response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            Log.e(TAG,"Auction_check======>"+response);

                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"Message");
                            if(result==1)
                            {
                             navigatePayment();
                            }
                            else
                            {
                                       CheckStatus(message);
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
            case Constants.ADD_AUCTION_ITEM_CODE:
                hideLoading();
                break;

            case Constants.PRICING_CODE:
                hideLoading();
                break;

            case Constants.AuctionCategory:
                progressBarDialog.setVisibility(View.GONE);

                   break;
        }
    }



    private void CheckStatus(String message) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.auction_check,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        TextView facebook_txt = layout.findViewById(R.id.facebook_txt);
        TextView allow_txt = layout.findViewById(R.id.allow_txt);
        TextView btn_Ok = layout.findViewById(R.id.btn_OK);

        btn_Ok.setText(mContext.getResources().getString(R.string.OK));
        facebook_txt.setText(mContext.getResources().getString(R.string.app_name));
        allow_txt.setText(message);

        alertDialog.show();

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();


            }
        });
    }




}
