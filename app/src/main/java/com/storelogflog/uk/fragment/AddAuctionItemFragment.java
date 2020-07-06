package com.storelogflog.uk.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.CardViewModel;
import com.storelogflog.uk.activity.CommonMsgActivity;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.activity.PaymentActivity;
import com.storelogflog.uk.apiCall.AddAuctionItemApiCall;
import com.storelogflog.uk.apiCall.PricingApiCall;
import com.storelogflog.uk.apiCall.ViewItemApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.bean.AddAuctionRequestBean;
import com.storelogflog.uk.bean.AddStorageRequestBean;
import com.storelogflog.uk.bean.itemListBean.Item;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;

import static com.storelogflog.uk.apputil.Constants.pound;

public class AddAuctionItemFragment extends BaseFragment implements VolleyApiResponseString {

    private RelativeLayout rlSubmit;
    private  CardViewModel.Item item;
    private AppCompatEditText editItemName;
    private AppCompatEditText editDescription;
    private AppCompatEditText editExpectedMinValue;
    private AppCompatCheckBox cbTermAndCon;
    private AppCompatCheckBox cbAgree;
    private Fragment fragment;
    private Bundle bundle;
    int pondValue=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_auction_item, container, false);
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


        ((HomeActivity)getActivity()).enableViews(true,"Add Auction Item");

        if(getArguments()!=null)
        {
            item= ( CardViewModel.Item) getArguments().getSerializable("item");

            editItemName.setText(""+item.getName());
            editDescription.setText(""+item.getDesp());
            editExpectedMinValue.setText(""+item.getValue());
        }


        callPricingApi();

    }

    @Override
    public void initListeners() {

        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigatePayment();

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
                Storage storage=PreferenceManger.getPreferenceManger().getObject(PrefKeys.USER_STORAGE_BEAN, Storage.class);
                try {

                    AddAuctionRequestBean addAuctionRequestBean=new AddAuctionRequestBean();
                    addAuctionRequestBean.setStorageId(""+storage.getID());
                    addAuctionRequestBean.setUnitId(""+storage.getUnitID());
                    addAuctionRequestBean.setItemId(""+item.getID());
                    addAuctionRequestBean.setItemName(""+editItemName.getText().toString());
                    addAuctionRequestBean.setItemDescription(""+editDescription.getText().toString());
                    addAuctionRequestBean.setAmout(""+pondValue);
                    addAuctionRequestBean.setExpectedValue(editExpectedMinValue.getText().toString());

                    Log.e("pondValue=",""+pondValue);

                    startActivity(new Intent(getActivity(), PaymentActivity.class).
                            putExtra("requestData",addAuctionRequestBean).
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

        if (editItemName.getText().toString().isEmpty()) {
            return showErrorMsg(editItemName, "Item name can't be blank");
        }
        else if (editDescription.getText().toString().isEmpty()) {
            return showErrorMsg(editDescription, "Description can't be blank");
        }
        else if (editExpectedMinValue.getText().toString().isEmpty()) {
            return showErrorMsg(editExpectedMinValue, "Expected min value can't be blank");
        }
        else if (!cbAgree.isChecked()) {

            showToast(getContext(),"please checked I agree to pay.. checkbox");
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
                                cbAgree.setText("I agree to pay Auction valuation fee "+pound+pondValue+"");
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
        }
    }







}
