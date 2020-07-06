package com.storelogflog.uk.StorageSelection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.SelectedCellModel;
import com.storelogflog.uk.activity.BaseActivity;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.activity.LoginActivity;
import com.storelogflog.uk.activity.PaymentActivity;
import com.storelogflog.uk.activity.RegisterActivity;
import com.storelogflog.uk.adapter.CountryAdapter;
import com.storelogflog.uk.adapter.RegionAdapter2;
import com.storelogflog.uk.apiCall.AddStorageApiCall;
import com.storelogflog.uk.apiCall.GetAllCountryApiCall;
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
import com.storelogflog.uk.bean.countryBean.CountryBean;
import com.storelogflog.uk.bean.regionBean.RegionBean;
import com.storelogflog.uk.fragment.CommonMsgFragment;
import com.storelogflog.uk.fragment.ThankYouFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.storelogflog.uk.apputil.Constants.pound;

public class AddStorageActivity extends BaseActivity implements VolleyApiResponseString, View.OnClickListener, TextWatcher {
    String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private LinearLayout toolbar_back;
    private ListView lvcountry;
    private ProgressBar progressBarDialog;
    private int countryId;
    private CountryBean countryBean;
    private AppCompatEditText staorage_spcae_name_ext, short_desxription_ext, address1_ext, address2_ext,
            enter_city_ext, postcode_ext;
    private AppCompatTextView txt_country;
    TextView price_txt;
    private CheckBox term_check;
    private RelativeLayout submit_relative, policy_linear;
    int pondValue = 0;
    private AddStorageRequestBean addStorageRequestBean;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storage);

        mContext = this;

        initViews();
        initListeners();


    }

    @Override
    public void initViews() {
        staorage_spcae_name_ext = findViewById(R.id.staorage_spcae_name_ext);
        short_desxription_ext = findViewById(R.id.short_desxription_ext);
        address1_ext = findViewById(R.id.address1_ext);
        address2_ext = findViewById(R.id.address2_ext);
        enter_city_ext = findViewById(R.id.enter_city_ext);
        postcode_ext = findViewById(R.id.postcode_ext);
        txt_country = findViewById(R.id.txt_country);
        term_check = findViewById(R.id.term_check);
        submit_relative = findViewById(R.id.submit_relative);
        toolbar_back = findViewById(R.id.toolbar_back);
        policy_linear = findViewById(R.id.policy_linear);
        price_txt = findViewById(R.id.price_txt);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(PrefKeys.GridCell, "");
        Type type = new TypeToken<List<SelectedCellModel>>() {
        }.getType();
        List<SelectedCellModel> arrayList = gson.fromJson(json, type);
    }

    @Override
    public void initListeners() {
        txt_country.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        policy_linear.setOnClickListener(this);
        submit_relative.setOnClickListener(this);



        staorage_spcae_name_ext.addTextChangedListener(this);
        short_desxription_ext.addTextChangedListener(this);
        address1_ext.addTextChangedListener(this);
        address2_ext.addTextChangedListener(this);
        enter_city_ext.addTextChangedListener(this);
        postcode_ext.addTextChangedListener(this);


        callPricingApi();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_country:
                CountryDialog();
                break;

            case R.id.toolbar_back:
                onBackPressed();
                break;

            case R.id.policy_linear:

                Intent intent = new Intent(mContext, TermsAndConditionActivity.class);
                intent.putExtra("id", "2");
                startActivity(intent);
                break;

            case R.id.submit_relative:
                navigatePaymentScreen();
                break;
        }
    }


    public boolean isValidate() {
        Changebackground();
        if (staorage_spcae_name_ext.getText().toString().isEmpty()) {
            return showErrorMsg(staorage_spcae_name_ext, "Storage name can't be blank");
        } /*else if (address1_ext.getText().toString().isEmpty()) {
            return showErrorMsg(address1_ext, "Address1 can't be blank");
        } else if (address2_ext.getText().toString().isEmpty()) {
            return showErrorMsg(address2_ext, "Address2 can't be blank");
        }*/
        else if (txt_country.getText().toString().isEmpty()) {
            showToast("Please select country");
            return false;
        }
        else if (enter_city_ext.getText().toString().isEmpty()) {
            return showErrorMsg(enter_city_ext, "Please select city");

        }  /*else if (postcode_ext.getText().toString().isEmpty()) {
            return showErrorMsg(postcode_ext, "Postcode can't be blank");
        }*/ else if (!term_check.isChecked()) {
            showToast("Please accept terms & condition's");
            return false;
        } else {
            return true;
        }

    }

    void navigatePaymentScreen() {
        if (Utility.isInternetConnected(mContext)) {

            if (isValidate()) {
                try {

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                    Gson gson = new Gson();
                    String json = sharedPrefs.getString(PrefKeys.GridCell, "");
                    Type type = new TypeToken<List<SelectedCellModel>>() {
                    }.getType();
                    List<SelectedCellModel> arrayList = gson.fromJson(json, type);
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).isChecked()) {
                            if (TextUtils.isEmpty(value)) {
                                value = "1";

                            } else {
                                value = value + "," + "1";
                            }
                        } else {
                            if (TextUtils.isEmpty(value)) {
                                value = "0";

                            } else {
                                value = value + "," + "0";
                            }
                        }
                    }

                    AddStorageRequestBean addStorageRequestBean = new AddStorageRequestBean();
                    addStorageRequestBean.setStorageName(staorage_spcae_name_ext.getText().toString());
                    addStorageRequestBean.setAddress1(address1_ext.getText().toString());
                    addStorageRequestBean.setAddress2(address2_ext.getText().toString());
                    addStorageRequestBean.setCountry("" + countryId);
                    addStorageRequestBean.setRegion("0");
                    addStorageRequestBean.setCity("" + enter_city_ext.getText().toString());
                    addStorageRequestBean.setZip("" + postcode_ext.getText().toString());
                    addStorageRequestBean.setPond(pondValue);
                    addStorageRequestBean.setStorage_shaps(value);

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.garage))) {
                        addStorageRequestBean.setStorage_type("2");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.shed))) {
                        addStorageRequestBean.setStorage_type("3");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                        addStorageRequestBean.setStorage_type("4");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.other))) {
                        addStorageRequestBean.setStorage_type("5");

                    }


                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        addStorageRequestBean.setDoor_type("1");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        addStorageRequestBean.setDoor_type("2");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        addStorageRequestBean.setDoor_type("3");

                    } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {

                        addStorageRequestBean.setDoor_type("4");

                    }if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                        addStorageRequestBean.setDoor_type("5");

                    }
                    addStorageRequestBean.setStorage_doors(PreferenceManger.getPreferenceManger().getString(PrefKeys.Storage_doors));


                    callAddStorage(addStorageRequestBean);
                  /*  startActivity(new Intent(mContext, PaymentActivity.class).
                            putExtra("requestData", addStorageRequestBean).
                            putExtra("FROM", "AddStorage"));*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            showToast("No Internet Connection");
        }
    }


    void callAddStorage(AddStorageRequestBean addStorageRequestBean1) {

        if(Utility.isInternetConnected(mContext))
        {
            addStorageRequestBean = addStorageRequestBean1;
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("name", this.addStorageRequestBean.getStorageName());
                jsonObjectPayload.put("descriptionshort",short_desxription_ext.getText().toString());
                jsonObjectPayload.put("descriptionlong","test");
                jsonObjectPayload.put("address1", this.addStorageRequestBean.getAddress1());
                jsonObjectPayload.put("address2", this.addStorageRequestBean.getAddress2());
                jsonObjectPayload.put("country",""+ this.addStorageRequestBean.getCountry());
                jsonObjectPayload.put("region",""+ this.addStorageRequestBean.getRegion());
                jsonObjectPayload.put("city",""+ this.addStorageRequestBean.getCity());
                jsonObjectPayload.put("zip",""+ this.addStorageRequestBean.getZip());
                jsonObjectPayload.put("storage_type",""+ this.addStorageRequestBean.getStorage_type());
                jsonObjectPayload.put("storage_shaps",""+ this.addStorageRequestBean.getStorage_shaps());
                jsonObjectPayload.put("door_type",""+ this.addStorageRequestBean.getDoor_type());
                jsonObjectPayload.put("storage_doors",""+ this.addStorageRequestBean.getStorage_doors());


                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));




                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());

                Log.e("jsonObjectPayload", String.valueOf(jsonObjectPayload));
                Log.e("token", String.valueOf(token));
                showLoading("Loading...");
              new AddStorageApiCall(this,this,token, Constants.ADD_STORAGE_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    void CountryDialog() {
        final Dialog dialog = new Dialog(mContext);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        lvcountry = dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle = dialog.findViewById(R.id.txt_title);
        progressBarDialog = dialog.findViewById(R.id.dialog_progress_bar);
        txtTitle.setText(mContext.getResources().getString(R.string.select_country));

        callAllCountryApi();

        lvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                txt_country.setText(countryBean.getCountries().get(position).getName());

                countryId = countryBean.getCountries().get(position).getID();
                Changebackground();
                dialog.dismiss();

            }
        });

        dialog.show();


    }

    void callAllCountryApi() {
        if (Utility.isInternetConnected(mContext)) {

                new GetAllCountryApiCall(AddStorageActivity.this,this,null, Constants.ALL_COUNTRY_CODE);

                progressBarDialog.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    void callPricingApi() {
        if (Utility.isInternetConnected(mContext)) {
            try {
                new PricingApiCall(mContext, this, null, Constants.PRICING_CODE);
                showLoading("Loading...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {
        switch (code) {
            case Constants.ALL_COUNTRY_CODE:
                progressBarDialog.setVisibility(View.GONE);
                if (response != null) {
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
                                 countryBean = new Gson().fromJson(response, CountryBean.class);
                                CountryAdapter adapter = new CountryAdapter(countryBean.getCountries(), mContext);
                                lvcountry.setAdapter(adapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;


            case Constants.PRICING_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                pondValue = getIntFromJsonObj(jsonObject, "NewStorage");
                                Log.e("pondValue", String.valueOf(pondValue));
                               price_txt.setText("I agree to pay storage space free "+pound+pondValue+"/year");

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ADD_STORAGE_CODE:
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
                            Log.e("ADD_STORAGE_RESPONSE",jsonObject.toString());
                           if(result==1)
                            {
                                String message2="Thank you for the request. Once your unit id is validated and approved, you can start logging the item.";

                                Intent intent = new Intent(mContext, HomeActivity.class);
                                intent.putExtra("From",Constants.FROM_PAYMENT_SCREEN);
                                intent.putExtra("message",message);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mContext.startActivity(intent);
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
        switch (code) {
            case Constants.ALL_COUNTRY_CODE:
                progressBarDialog.setVisibility(View.GONE);
                hideLoading();
                break;
            case Constants.PRICING_CODE:
                hideLoading();
                break;
            case Constants.ADD_STORAGE_CODE:
              hideLoading();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Changebackground();
    }

    @Override
    public void afterTextChanged(Editable s) {
        Changebackground();
    }


    private void Changebackground() {


        if (staorage_spcae_name_ext.getText().toString().trim().isEmpty()) {
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.green_border_light);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        } /*else if (short_desxription_ext.getText().toString().trim().isEmpty()) {
            short_desxription_ext.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        } else if (address1_ext.getText().toString().trim().isEmpty()) {
            address1_ext.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        } else if (address2_ext.getText().toString().trim().isEmpty()) {
            address2_ext.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        }*/else if (txt_country.getText().toString().trim().isEmpty()) {
            txt_country.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        } else if (enter_city_ext.getText().toString().trim().isEmpty()) {
            enter_city_ext.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);

        }/* else if (postcode_ext.getText().toString().trim().isEmpty()) {
            postcode_ext.setBackgroundResource(R.drawable.green_border_light);
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);

        }*/ else {
            staorage_spcae_name_ext.setBackgroundResource(R.drawable.background_edit_square);
            short_desxription_ext.setBackgroundResource(R.drawable.background_edit_square);
            address1_ext.setBackgroundResource(R.drawable.background_edit_square);
            address2_ext.setBackgroundResource(R.drawable.background_edit_square);
            txt_country.setBackgroundResource(R.drawable.background_edit_square);
            enter_city_ext.setBackgroundResource(R.drawable.background_edit_square);
            postcode_ext.setBackgroundResource(R.drawable.background_edit_square);


        }
    }
}
