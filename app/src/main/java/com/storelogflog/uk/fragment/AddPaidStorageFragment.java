package com.storelogflog.uk.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;


import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.CommonMsgActivity;
import com.storelogflog.uk.activity.HomeActivity;

import com.storelogflog.uk.activity.PaymentActivity;

import com.storelogflog.uk.adapter.CityAdapter;
import com.storelogflog.uk.adapter.CountryAdapter;
import com.storelogflog.uk.adapter.RegionAdapter2;

import com.storelogflog.uk.apiCall.CityApiCall;
import com.storelogflog.uk.apiCall.PricingApiCall;
import com.storelogflog.uk.apiCall.RegionApiCall;

import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.AddStorageRequestBean;
import com.storelogflog.uk.bean.cityBean.CityBean;
import com.storelogflog.uk.bean.countryBean.CountryBean;
import com.storelogflog.uk.bean.regionBean.RegionBean;
import org.json.JSONException;
import org.json.JSONObject;
import static com.storelogflog.uk.apputil.Constants.pound;


public class AddPaidStorageFragment extends BaseFragment implements View.OnClickListener , VolleyApiResponseString {

    private RelativeLayout rlSubmit;
    private AppCompatEditText editStorageSpaceName;
    private AppCompatEditText editAddress1;
    private AppCompatEditText editAddress2;
    private AppCompatEditText editZip;
    private AppCompatTextView txtCountry;
    private AppCompatTextView txtRegion;
    private AppCompatEditText txtCity;
    private AppCompatCheckBox cbAgree;
    private AppCompatCheckBox cbTermsAndCon;
    private int countryId;
    private int regionId;
    private int cityId;
    private  RegionBean  regionBean;
    private CityBean cityBean;
    private ListView lvRegion;
    private ListView lvCity;
    private ProgressBar progressBarDialog;
    private Fragment fragment;
    private Bundle bundle;
    int pondValue=0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_paid_storage_space, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rlSubmit=view.findViewById(R.id.rl_submit);
        cbTermsAndCon=view.findViewById(R.id.cb_terms_and_conditions);
        editStorageSpaceName=view.findViewById(R.id.edit_storage_space_name);
        editAddress1=view.findViewById(R.id.edit_address1);
        editAddress2=view.findViewById(R.id.edit_address2);
        txtCountry=view.findViewById(R.id.txt_country);
        txtRegion=view.findViewById(R.id.txt_region);
        txtCity=view.findViewById(R.id.txt_city);
        editZip=view.findViewById(R.id.edit_zip);
        cbAgree=view.findViewById(R.id.cb_agree);

        HomeActivity.toolbar.setVisibility(View.VISIBLE);

        ((HomeActivity)getActivity()).enableViews(true,"Add Storage");

        callPricingApi();



    }

    @Override
    public void initListeners() {

        txtCountry.setOnClickListener(this);
        txtRegion.setOnClickListener(this);
        rlSubmit.setOnClickListener(this);


        cbTermsAndCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new CommonMsgFragment();
                bundle=new Bundle();
                bundle.putString("id","2");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(),fragment,true,null);
            }
        });


        cbAgree.setText("I agree to pay storage space free "+Constants.pound+"0/year");
    }




    public boolean isValidate()
    {
        if(editStorageSpaceName.getText().toString().isEmpty())
        {
            return showErrorMsg(editStorageSpaceName,"Storage name can't be blank");
        }
        else if(editAddress1.getText().toString().isEmpty())
        {
            return showErrorMsg(editAddress1,"Address1 can't be blank");
        }
        else if(editAddress2.getText().toString().isEmpty())
        {
            return showErrorMsg(editAddress2,"Address2 can't be blank");
        }

      /*  else if(txtCountry.getText().toString().isEmpty())
        {
            showToast(getActivity(),"Please select country");
            return false;
        }*/

        else if(txtCity.getText().toString().isEmpty())
        {
            return showErrorMsg(txtCity,"Please select city");

        }
        else if(txtRegion.getText().toString().isEmpty())
        {
            showToast(getActivity(),"Please select region");
            return false;
        }

        else if(editZip.getText().toString().isEmpty())
        {
            return showErrorMsg(editZip,"Postcode can't be blank");
        }
        else if(!cbAgree.isChecked())
        {
            showToast(getActivity(),"Please Checked checxbox");
            return false;
        }
        else if(!cbTermsAndCon.isChecked())
        {
            showToast(getActivity(),"Please Checked checxbox");
            return false;
        }
        else
        {
            return true;
        }

    }





    void countryDialog()
    {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView listView=dialog.findViewById(R.id.listview);
         AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
         txtTitle.setText("Select Country");
         txtTitle.setVisibility(View.VISIBLE);

        final CountryBean countryBean= PreferenceManger.getPreferenceManger().getObject(PrefKeys.COUNTRY_LIST,CountryBean.class);

        if(countryBean!=null) {
            if (countryBean.getCountries().size() > 0) {

                CountryAdapter adapter = new CountryAdapter(countryBean.getCountries(),getActivity());
                listView.setAdapter(adapter);
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String countryName=countryBean.getCountries().get(position).getName();
                txtCountry.setText(countryName);

                countryId=countryBean.getCountries().get(position).getID();

                txtRegion.setText("");
                txtRegion.setHint("Region");


                dialog.dismiss();
            }
        });

        dialog.show();


    }

    void regionDialog()
    {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        lvRegion=dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
        progressBarDialog=dialog.findViewById(R.id.dialog_progress_bar);
        txtTitle.setText("Select Region");

        callAllRegionApi();

        lvRegion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                txtRegion.setText(regionBean.getRegion().get(position).getName());


                regionId=regionBean.getRegion().get(position).getID();
                dialog.dismiss();

            }
        });

        dialog.show();


    }




    void cityDialog()
    {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        lvCity=dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
        progressBarDialog=dialog.findViewById(R.id.dialog_progress_bar);
        txtTitle.setText("Select City");

        callAllCityApi();


        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                txtCity.setText(cityBean.getCities().get(position).getName());
                cityId=cityBean.getCities().get(position).getID();


                dialog.dismiss();


            }
        });

        dialog.show();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.txt_country:
                 countryDialog();
                 break;
            case R.id.txt_region:

                regionDialog();
              /*  if(!txtCountry.getText().toString().isEmpty())
                {
                    regionDialog();
                }
                else
                {
                    showToast(getActivity(),"Please select first country");
                }*/
                break;
            case R.id.txt_city:
               /* if(txtCountry.getText().toString().isEmpty())
                {
                    showToast(getActivity(),"Please select first country");
                }
                else if(txtRegion.getText().toString().isEmpty())
                {
                    showToast(getActivity(),"Please select first region");
                }
                else
                {
                    cityDialog();
                }*/

                break;
            case R.id.rl_submit:
                navigatePaymentScreen();
                break;
        }
    }

    void navigatePaymentScreen()
    {
        if(Utility.isInternetConnected(getActivity()))
        {

            if(isValidate())
            {
                try {

                    AddStorageRequestBean addStorageRequestBean=new AddStorageRequestBean();
                    addStorageRequestBean.setStorageName(editStorageSpaceName.getText().toString());
                    addStorageRequestBean.setAddress1(editAddress1.getText().toString());
                    addStorageRequestBean.setAddress2(editAddress2.getText().toString());
                    addStorageRequestBean.setCountry("1");
                    addStorageRequestBean.setRegion(""+regionId);
                    addStorageRequestBean.setCity(""+txtCity.getText().toString());
                    addStorageRequestBean.setZip(""+editZip.getText().toString());
                    addStorageRequestBean.setPond(200);

                  /*  fragment=new PaymentFragment();
                    bundle=new Bundle();
                    bundle.putSerializable("requestData",addStorageRequestBean);
                    fragment.setArguments(bundle);
                    Common.loadFragment(getActivity(),fragment,false,null);*/

                  startActivity(new Intent(getActivity(), PaymentActivity.class).
                          putExtra("requestData",addStorageRequestBean).
                          putExtra("FROM","AddStorage"));



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


    void callAllRegionApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("country","1");
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new RegionApiCall(getActivity(),this,token, Constants.ALL_REGIONS_CODE);
                progressBarDialog.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
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



    void callAllCityApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("region",""+regionId);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());

                progressBarDialog.setVisibility(View.VISIBLE);
                new CityApiCall(getActivity(),this,token, Constants.ALL_CITIES_CODE);

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
            case Constants.ALL_REGIONS_CODE:
                progressBarDialog.setVisibility(View.GONE);
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
                                regionBean=new Gson().fromJson(response, RegionBean.class);
                                RegionAdapter2 adapter = new RegionAdapter2(regionBean.getRegion(),getActivity());
                                lvRegion.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ALL_CITIES_CODE:
            progressBarDialog.setVisibility(View.GONE);
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
                            cityBean=new Gson().fromJson(response, CityBean.class);

                            if(cityBean!=null && cityBean.getCities().size()>0) {

                                CityAdapter adapter = new CityAdapter(cityBean.getCities(),getActivity());
                                lvCity.setAdapter(adapter);
                            }

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

                                pondValue=getIntFromJsonObj(jsonObject,"NewStorage");
                                cbAgree.setText("I agree to pay storage space free "+pound+pondValue+"/year");

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
            case Constants.ALL_REGIONS_CODE:
                 progressBarDialog.setVisibility(View.GONE);
                 break;
            case Constants.ALL_CITIES_CODE:
                progressBarDialog.setVisibility(View.GONE);
                break;
            case Constants.PRICING_CODE:
                hideLoading();
                break;

        }
    }
}
