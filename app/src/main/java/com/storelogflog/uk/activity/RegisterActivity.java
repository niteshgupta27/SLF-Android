package com.storelogflog.uk.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.adapter.CountryAdapter;
import com.storelogflog.uk.adapter.RegionAdapter;
import com.storelogflog.uk.adapter.RegionAdapter2;
import com.storelogflog.uk.apiCall.GetAllCountryApiCall;
import com.storelogflog.uk.apiCall.LoginApiCall;
import com.storelogflog.uk.apiCall.RegionApiCall;
import com.storelogflog.uk.apiCall.RegisterApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.bean.countryBean.CountryBean;
import com.storelogflog.uk.bean.login.LoginBean;
import com.storelogflog.uk.bean.regionBean.RegionBean;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class RegisterActivity extends BaseActivity implements VolleyApiResponseString,View.OnClickListener {

    String TAG = this.getClass().getSimpleName();
    private AppCompatEditText editFirstName;
    private AppCompatEditText editLastName;
    private AppCompatEditText editPhoneNumber;
    private AppCompatEditText editEmail;
    private AppCompatEditText editPassword;
    private AppCompatTextView txtRegion;
    private AppCompatTextView txtCountry;
    private ListView lvRegion;
    private ListView lvCountry;
    private ProgressBar progressBarDialog;
    private int countryId;
    private int regionId;
    private RegionBean regionBean;
    private CountryBean countryBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        editFirstName=findViewById(R.id.edit_first_name);
        editLastName=findViewById(R.id.edit_last_name);
        editPhoneNumber=findViewById(R.id.edit_mobile);
        editEmail=findViewById(R.id.edit_email);
        editPassword=findViewById(R.id.edit_password);
        txtRegion=findViewById(R.id.txt_region);
        txtCountry=findViewById(R.id.txt_country);

    }

    @Override
    public void initListeners() {

        findViewById(R.id.txt_register).setOnClickListener(this);
        findViewById(R.id.ll_signin).setOnClickListener(this);
        txtRegion.setOnClickListener(this);
        txtCountry.setOnClickListener(this);
    }


    public void login(String email,String password) {
        if (Utility.isInternetConnected(RegisterActivity.this)) {
            try {

                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("email", email);
                jsonObjectPayload.put("password", password);
                jsonObjectPayload.put("devicetype", Constants.DEVICE_TYPE);
                jsonObjectPayload.put("fcm", ""+PreferenceManger.getPreferenceManger().getString(PrefKeys.FCMTOKEN));
                jsonObjectPayload.put("devicemanufacture", android.os.Build.MANUFACTURER);
                jsonObjectPayload.put("modelname", Build.MODEL);
                jsonObjectPayload.put("modelnumber", Build.MODEL);
                jsonObjectPayload.put("osver", Build.VERSION.RELEASE);
                jsonObjectPayload.put("devicename", android.os.Build.DEVICE);


                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new LoginApiCall(RegisterActivity.this, this, token, Constants.LOGIN_CODE);
                showLoading("Login...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast("No Internet Connection");
        }

    }


    @Override
    public void onClick(View view) {
         switch (view.getId())
         {
             case R.id.txt_register:
                 register();
                 break;

             case R.id.ll_signin:
                 startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                 finish();
                 break;

             case R.id.txt_country:
                 countryDialog();
                 break;

             case R.id.txt_region:
                 regionDialog();
                /* if(!txtCountry.getText().toString().isEmpty())
                 {
                     regionDialog();
                 }
                 else
                 {
                     showToast("Please select first country");
                 }*/

                 break;
         }
    }






    @Override
    public void onAPiResponseSuccess(String response, int code) {


        switch (code)
        {
            case Constants.REGISTER_CODE:
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

                                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                               // finish();

                                Utility.commonMsgDialog(RegisterActivity.this, "You Registered successfuly \n  Please update you profile to continue", false, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        login(editEmail.getText().toString(),editPassword.getText().toString());
                                        if (Utility.dialog!=null)
                                        {
                                            Utility.dialog.dismiss();
                                        }

                                    }
                                });

                            }
                            else
                            {
                                Utility.commonMsgDialog(RegisterActivity.this, "" + message, true, null);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

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
                                RegionAdapter2 adapter = new RegionAdapter2(regionBean.getRegion(),RegisterActivity.this);
                                lvRegion.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ALL_COUNTRY_CODE:
                if(response!=null)
                {
                    progressBarDialog.setVisibility(View.GONE);
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
                                countryBean=new Gson().fromJson(response, CountryBean.class);
                                if(countryBean!=null) {
                                    if (countryBean.getCountries().size() > 0) {

                                        CountryAdapter adapter = new CountryAdapter(countryBean.getCountries(),RegisterActivity.this);
                                        lvCountry.setAdapter(adapter);
                                    }
                                }
                                PreferenceManger.getPreferenceManger().setObject(PrefKeys.COUNTRY_LIST,countryBean);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.LOGIN_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "message");
                            if (result == 1) {

                                LoginBean loginBean= new Gson().fromJson(response, LoginBean.class);
                                PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_INFO,loginBean);

                                PreferenceManger.getPreferenceManger().setString(PrefKeys.EMAIL,loginBean.getEmail());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.APIKEY,loginBean.getApikey());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.SECRET,loginBean.getSecret());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.UserProfile,loginBean.getImage());
                                PreferenceManger.getPreferenceManger().setBoolean(PrefKeys.ISLOGIN,true);

                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class)
                                        .putExtra("From","Login"));
                                finish();

                             /*   Utility.commonMsgDialog(LoginActivity.this, "" + message, false, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        finish();
                                    }
                                });*/

                            } else {

                                Utility.commonMsgDialog(RegisterActivity.this, "" + message, true, null);

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

        switch (code)
        {
            case Constants.REGISTER_CODE:
                Logger.debug(TAG,""+Utility.returnErrorMsg(error,RegisterActivity.this));
                hideLoading();
                showToast(Utility.returnErrorMsg(error,RegisterActivity.this));
                break;
            case Constants.ALL_REGIONS_CODE:
                progressBarDialog.setVisibility(View.GONE);
                break;

            case Constants.LOGIN_CODE:
                Logger.debug(TAG, "" + Utility.returnErrorMsg(error, RegisterActivity.this));
                hideLoading();
                showToast(Utility.returnErrorMsg(error, RegisterActivity.this));
                break;

        }


    }




    public void register()
    {
        if(Utility.isInternetConnected(RegisterActivity.this))
        {
            if (isValidate())
            {
                try {
                    JSONObject jsonObjectPayload=new JSONObject();
                    jsonObjectPayload.put("firstname",editFirstName.getText().toString());
                    jsonObjectPayload.put("lastname",editLastName.getText().toString());
                    jsonObjectPayload.put("email",editEmail.getText().toString());
                    jsonObjectPayload.put("phonenumber",editPhoneNumber.getText().toString());
                    jsonObjectPayload.put("password",editPassword.getText().toString());
                    jsonObjectPayload.put("IP","142.142.25.25");
                    jsonObjectPayload.put("country",1);
                    jsonObjectPayload.put("region",regionId);
                    jsonObjectPayload.put("fcmtoken",""+PreferenceManger.getPreferenceManger().getString(PrefKeys.FCMTOKEN));


                    Logger.debug(TAG,jsonObjectPayload.toString());
                    String token=Utility.getJwtToken(jsonObjectPayload.toString());
                    new RegisterApiCall(RegisterActivity.this,this,token, Constants.REGISTER_CODE);
                    showLoading("Register...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            showToast("No Internet Connection");
        }

    }

    public boolean isValidate()
    {
        if(editFirstName.getText().toString().isEmpty())
        {
            return showErrorMsg(editFirstName,"First name can't be blank");
        }
        else if(editLastName.getText().toString().isEmpty())
        {
            return showErrorMsg(editLastName,"Last name can't be blank");
        }
        else if(editPhoneNumber.getText().toString().isEmpty())
        {
            return showErrorMsg(editPhoneNumber,"Mobile number can't be blank");
        }
        else if(!Validator.isValidMobileNo(editPhoneNumber.getText().toString()))
        {
            return showErrorMsg(editPhoneNumber,"Invalid Mobile Number");
        }
        else if(editEmail.getText().toString().isEmpty())
        {
            return showErrorMsg(editEmail,"Email can't be blank");
        }
        else if(!Validator.isEmailValid(editEmail.getText().toString()))
        {
            return showErrorMsg(editEmail,"Invalid email");
        }
       /* else if(txtCountry.getText().toString().isEmpty())
        {
            showToast("Country can't be blank");
            return false;
        }*/
        else if(txtRegion.getText().toString().isEmpty())
        {
             showToast("Region can't be blank");
             return false;
        }

        else if(editPassword.getText().toString().isEmpty())
        {
            return showErrorMsg(editPassword,"Password can't be blank");
        }
        else if(editPassword.getText().toString().length()<6)
        {
            return showErrorMsg(editPassword,"Password can't be less than 6 digits");
        }
        else
        {
            return true;
        }

    }



    void countryDialog()
    {
        final Dialog dialog = new Dialog(RegisterActivity.this);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        lvCountry=dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
        progressBarDialog=dialog.findViewById(R.id.dialog_progress_bar);
        txtTitle.setText("Select Country");
        txtTitle.setVisibility(View.VISIBLE);

         callAllCountryApi();


        lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    void callAllCountryApi()
    {
        if(Utility.isInternetConnected(RegisterActivity.this))
        {
            progressBarDialog.setVisibility(View.VISIBLE);
            new GetAllCountryApiCall(RegisterActivity.this,this,null, Constants.ALL_COUNTRY_CODE);

        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    void regionDialog()
    {
        final Dialog dialog = new Dialog(RegisterActivity.this);

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

    void callAllRegionApi()
    {
        if(Utility.isInternetConnected(RegisterActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("country","1");
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new RegionApiCall(RegisterActivity.this,this,token, Constants.ALL_REGIONS_CODE);
                progressBarDialog.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


}
