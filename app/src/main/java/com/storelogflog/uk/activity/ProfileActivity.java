package com.storelogflog.uk.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.adapter.CountryAdapter;
import com.storelogflog.uk.adapter.RegionAdapter2;
import com.storelogflog.uk.apiCall.GetUserProfileDataApiCall;
import com.storelogflog.uk.apiCall.RegionApiCall;
import com.storelogflog.uk.apiCall.UpdateAddressApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.apputil.VolleyMultipartRequest;
import com.storelogflog.uk.bean.cityBean.CityBean;
import com.storelogflog.uk.bean.login.LoginBean;
import com.storelogflog.uk.bean.profileBean.Profile;
import com.storelogflog.uk.bean.profileBean.ProfileBean;
import com.storelogflog.uk.bean.regionBean.RegionBean;
import com.storelogflog.uk.bean.countryBean.CountryBean;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, VolleyApiResponseString {

    String TAG = this.getClass().getSimpleName();
    private AppCompatTextView txtEditProfile;
    private AppCompatEditText editName,editEmail,editPhone,editAddress1,editAddress2;
    private AppCompatTextView txtCountry,txtRegion;
    private LinearLayout llCountry;
    private LinearLayout llRegion;
    private LinearLayout llCity;
    private  RegionBean  regionBean;
    private CityBean cityBean;
    private int cityId=0;
    private int regionId=0;
    private int countryId=0;
    public Toolbar toolbar;
    private AppCompatEditText editCity;
    private AppCompatTextView txtChangePhoto;
    private String cityName="";
    private ProgressBar progressBarDialog;
    private ListView lvRegion;
    private ListView lvCountry;
    private  MenuItem menuEditProfile;
    private static int GALLARY_PIC = 1009;
    private static int CAMERA_PIC = 1008;
    private Bitmap bitmap;
    private CircleImageView imgProfile;
    String from="";
    RequestOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        editName=findViewById(R.id.edit_name);
        editEmail=findViewById(R.id.edit_email);
        editPhone=findViewById(R.id.edit_phone);
        editAddress1=findViewById(R.id.edit_address1);
        editAddress2=findViewById(R.id.edit_address2);
        txtEditProfile=findViewById(R.id.txt_edit_profile);
        editCity=findViewById(R.id.edit_city);
        txtCountry=findViewById(R.id.txt_country);
        txtRegion=findViewById(R.id.txt_region);
        llCountry=findViewById(R.id.ll_country);
        llRegion=findViewById(R.id.ll_region);
        llCity=findViewById(R.id.ll_city);
        txtChangePhoto=findViewById(R.id.txt_change_photo);
        imgProfile=findViewById(R.id.profile_image);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Profile");

        options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder);


    }

    void callUpdatePhoto() {
        if (Utility.isInternetConnected(ProfileActivity.this)) {

            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                uploadImageToServer(bitmap, token);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            showToast("No Internet Connection");
        }

    }


    private void uploadImageToServer(final Bitmap bitmap, final String token) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.BASE_URL + Constants.API_UPDATE_PHOTO, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

             //   hideLoading();
                String resultResponse = new String(response.data);

                if (resultResponse != null) {
                    String payload[] = resultResponse.split("\\.");
                    if (payload[1] != null) {
                        resultResponse = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(resultResponse);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                showToast(message);
                                callGetProfileApi2();
         /*                       LoginBean loginBean= new Gson().fromJson(response.toString(), LoginBean.class);
                                PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_INFO,loginBean);

                                PreferenceManger.getPreferenceManger().setString(PrefKeys.EMAIL,loginBean.getEmail());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.APIKEY,loginBean.getApikey());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.SECRET,loginBean.getSecret());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.UserProfile,loginBean.getImage());
*/

                            } else {
                                showToast(message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: " + error.getMessage());
                Log.e(Tag, "error" + error.getMessage());

                hideLoading();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("cAuthKey",login.getAuthkey());
                //Log.e(TAG, "params"+ params);
                return params;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("data", token);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> data = new HashMap<>();
                long image_name = System.currentTimeMillis();
                data.put("image", new VolleyMultipartRequest.DataPart(image_name + ".jpeg", getFileDataFromDrawable(bitmap)));
                return data;

            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 0));
        Volley.newRequestQueue(ProfileActivity.this).add(multipartRequest);


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent()!=null)
        {
            from=getIntent().getStringExtra("From");
            if(from.equals("Login"))
            {
                editTable();

                txtEditProfile.setText("Save Profile");

            }
            else if(from.equals("Home"))
            {
                nonEditTable();
                txtEditProfile.setText("Update Profile");
            }
        }


        callGetProfileApi();
    }

    public void initListeners()
    {

        txtEditProfile.setOnClickListener(this);
        llCountry.setOnClickListener(this);
        llRegion.setOnClickListener(this);
        llCity.setOnClickListener(this);
        txtChangePhoto.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {

        if(from.equals("Login"))
        {
            showToast("Please update profile first");
        }
        else
        {
            onBackPressed();
        }


        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if(from.equals("Login"))
        {
            showToast("Please update profile first");
        }
        else
        {
            super.onBackPressed();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.img_edit:
                editTable();
                break;

            case R.id.txt_edit_profile:
                callUpdateApi();
                break;
            case R.id.ll_country:
                 countryDialog();
                 break;
            case R.id.ll_region:
                regionDialog();
                break;
            case R.id.txt_change_photo:
                requestStorageAndCameraPermissionWithAlertDialog(ProfileActivity.this,GALLARY_PIC,CAMERA_PIC);
                break;


        }
    }


    public void updateUi(Profile profile)
    {

        editName.setText(""+profile.getFirstName()+" "+profile.getLastName());
        editEmail.setText(""+profile.getEmail());

        if(profile.getPhone()!=null)
        {
            editPhone.setText(""+profile.getPhone());
        }
        else
        {
            editPhone.setText("");
        }

        if (profile.getAdd1()!=null)
        {

            editAddress1.setText(""+profile.getAdd1());
        }
        else
        {
            editAddress1.setText("");
        }


        if (profile.getAdd1()!=null)
        {

            editAddress1.setText(""+profile.getAdd1());
        }
        else
        {
            editAddress1.setText("");
        }



        if (profile.getAdd2()!=null)
        {

            editAddress2.setText(""+profile.getAdd2());
        }
        else
        {
            editAddress2.setText("");
        }



        if (profile.getCountry()!=null)
        {

            txtCountry.setText(""+profile.getCountry());
        }
        else
        {
            txtCountry.setText("");
            txtCountry.setHint("");
        }


        if (profile.getReagion()!=null)
        {

            txtRegion.setText(""+profile.getReagion());
        }
        else
        {
            txtRegion.setText("");
        }


        if (profile.getCity()!=null)
        {

            editCity.setText(""+profile.getCity());
        }
        else
        {
            editCity.setText("");
        }

        countryId =profile.getCountryID();
        regionId=profile.getRegionID();

        if(profile.getImage()!=null)
        {
            Utility.loadImage(ProfileActivity.this,profile.getImage(),imgProfile);
        }



        //nonEditTable();

    }

    public boolean isValidate()
    {

        if (editPhone.getText().toString().isEmpty()) {

            return showErrorMsg(editPhone, "Mobile number can't be blank");

        } else if (!Validator.isValidMobileNo(editPhone.getText().toString())) {

            return showErrorMsg(editPhone, "Invalid Mobile Number");
        }
        else if (editAddress1.getText().toString().isEmpty()) {

            return showErrorMsg(editAddress1, "Address1 can't be blank");

        }
/*
        else if (editAddress2.getText().toString().isEmpty()) {

            return showErrorMsg(editAddress2, "Address2 can't be blank");

        }
*/
       /* else if (txtCountry.getText().toString().isEmpty()) {
            showToast("Country can't be blank");
            return false;
        }*/
        else if (editCity.getText().toString().isEmpty()) {

            return showErrorMsg(editCity, "City can't be blank");

        }

        else if (txtRegion.getText().toString().isEmpty()) {
            showToast("Region can't be blank");
            return false;
        }

        else {
            return true;
        }

    }

    public void editTable()
    {

        editAddress1.setEnabled(true);
        editAddress2.setEnabled(true);
        editCity.setEnabled(true);
        editPhone.setEnabled(true);
        llCountry.setEnabled(true);
        txtCountry.setEnabled(true);
        llRegion.setEnabled(true);
        txtRegion.setEnabled(true);

        txtEditProfile.setVisibility(View.VISIBLE);
        txtChangePhoto.setVisibility(View.VISIBLE);

        llRegion.setClickable(true);
        llCountry.setClickable(true);
        txtCountry.setClickable(true);
        txtRegion.setClickable(true);

        if(menuEditProfile!=null)
            menuEditProfile.setVisible(false);



    }

    public void nonEditTable()
    {
        editAddress1.setEnabled(false);
        editAddress2.setEnabled(false);
        editCity.setEnabled(false);
        editPhone.setEnabled(false);
        llCountry.setEnabled(false);
        txtCountry.setEnabled(false);
        llRegion.setEnabled(false);
        txtRegion.setEnabled(false);

        txtEditProfile.setVisibility(View.GONE);
        txtChangePhoto.setVisibility(View.GONE);
        editName.setClickable(false);

        llRegion.setClickable(false);
        llCountry.setClickable(false);
        txtCountry.setClickable(false);
        txtRegion.setClickable(false);

        if(menuEditProfile!=null)
            menuEditProfile.setVisible(true);

    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        hideLoading();

        switch (code)
        {
            case Constants.ALL_COUNTRY_CODE:
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
                                RegionAdapter2 adapter = new RegionAdapter2(regionBean.getRegion(),ProfileActivity.this);
                                lvRegion.setAdapter(adapter);


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ALL_CITIES_CODE:
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
                               // cityDialog();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;


            case Constants.UPDATE_ADDRESS_CODE:
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
                               showToast(message);
                               finish();
                              // callGetProfileApi();
                            }
                            else
                            {
                                showToast(message);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;


            case Constants.GET_PROFILE_CODE:
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
                               ProfileBean profileBean=new Gson().fromJson(response.toString(), ProfileBean.class);
                               if (profileBean.getProfile()!=null) {
                                   updateUi(profileBean.getProfile());
                               }

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

        hideLoading();

        switch (code) {
            case Constants.ALL_COUNTRY_CODE:
                break;
            case Constants.ALL_REGIONS_CODE:
                progressBarDialog.setVisibility(View.GONE);
                break;
            case Constants.ALL_CITIES_CODE:
                break;
            case Constants.GET_PROFILE_CODE:
                break;
        }


    }


    void callAllRegionApi()
    {
        if(Utility.isInternetConnected(ProfileActivity.this))
        {


            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("country","1");
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());

                progressBarDialog.setVisibility(View.VISIBLE);
                new RegionApiCall(ProfileActivity.this,this,token, Constants.ALL_REGIONS_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    void callGetProfileApi()
    {
        if(Utility.isInternetConnected(ProfileActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey",PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new GetUserProfileDataApiCall(ProfileActivity.this,this,token, Constants.GET_PROFILE_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    void callGetProfileApi2()
    {
        if(Utility.isInternetConnected(ProfileActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey",PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
             //   showLoading("Loading...");
                new GetUserProfileDataApiCall(ProfileActivity.this,this,token, Constants.GET_PROFILE_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }

 /*   void callAllCityApi(int id)
    {
        if(Utility.isInternetConnected(ProfileActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("region",""+id);
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new CityApiCall(ProfileActivity.this,this,token, Constants.ALL_CITIES_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }*/


    void callUpdateApi()
    {
        if(Utility.isInternetConnected(ProfileActivity.this))
        {

            if (isValidate())
            {
                try {
                    JSONObject jsonObjectPayload=new JSONObject();
                    jsonObjectPayload.put("address1",editAddress1.getText().toString());
                    jsonObjectPayload.put("address2",editAddress2.getText().toString());
                    jsonObjectPayload.put("region",""+regionId);
                    jsonObjectPayload.put("country","1");
                    jsonObjectPayload.put("city",""+editCity.getText().toString());
                    jsonObjectPayload.put("phonenumber",""+editPhone.getText().toString());
                    jsonObjectPayload.put("apikey",PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                    Logger.debug(TAG,jsonObjectPayload.toString());
                    String token=Utility.getJwtToken(jsonObjectPayload.toString());
                    showLoading("Loading...");
                    new UpdateAddressApiCall(ProfileActivity.this,this,token, Constants.UPDATE_ADDRESS_CODE);

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



    void regionDialog()
    {
        final Dialog dialog = new Dialog(ProfileActivity.this);

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


    void countryDialog()
    {
        final Dialog dialog = new Dialog(ProfileActivity.this);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView listView=dialog.findViewById(R.id.listview);
       // AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
       // txtTitle.setText("Select Country");

        final CountryBean countryBean=PreferenceManger.getPreferenceManger().getObject(PrefKeys.COUNTRY_LIST,CountryBean.class);

        if(countryBean!=null) {
            if (countryBean.getCountries().size() > 0) {

                CountryAdapter adapter = new CountryAdapter(countryBean.getCountries(),ProfileActivity.this);
                listView.setAdapter(adapter);
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String countryName=countryBean.getCountries().get(position).getName();
                txtCountry.setText(countryName);

                 countryId=countryBean.getCountries().get(position).getID();

                //txtRegion.setText("Select Region");
               // txtCity.setText("Select City");

                dialog.dismiss();




            }
        });

        dialog.show();


    }


   /* void regionDialog()
    {
        final Dialog dialog = new Dialog(ProfileActivity.this);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView listView=dialog.findViewById(R.id.listview);
         AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
         txtTitle.setText("Select Region");
        if(regionBean!=null) {
            if (regionBean.getRegion()!=null && regionBean.getRegion().size() > 0) {

                RegionAdapter2 adapter = new RegionAdapter2(regionBean.getRegion(),ProfileActivity.this);
                listView.setAdapter(adapter);
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                txtRegion.setText(regionBean.getRegion().get(position).getName());
                //txtCity.setText("Select City");
                regionId=regionBean.getRegion().get(position).getID();


                dialog.dismiss();

            }
        });

        dialog.show();


    }*/


   /* void cityDialog()
    {
        final Dialog dialog = new Dialog(ProfileActivity.this);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView listView=dialog.findViewById(R.id.listview);
        // AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
        // txtTitle.setText("Select Country");


        if(cityBean!=null && cityBean.getCities().size()>0) {

            CityAdapter adapter = new CityAdapter(cityBean.getCities(),ProfileActivity.this);
            listView.setAdapter(adapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //txtCity.setText(cityBean.getCities().get(position).getName());
                cityId=cityBean.getCities().get(position).getID();


                dialog.dismiss();


            }
        });

        dialog.show();


    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu,menu);

        menuEditProfile = menu.findItem(R.id.action_edit_profile);



        String from=getIntent().getStringExtra("From");
        if(from.equals("Login")) {

            menuEditProfile.setVisible(false);
        }
        else if(from.equals("Home"))
        {
            menuEditProfile.setVisible(true);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.action_edit_profile:
                menuItem.setVisible(false);
                editTable();
                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == GALLARY_PIC) {
                try {
                    Uri selectedFileUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFileUri);

                    Glide.with(ProfileActivity.this)
                            .load(bitmap)
                            .apply(options)
                            .into(imgProfile);

                    callUpdatePhoto();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_PIC) {
                try {

                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");

                    Glide.with(ProfileActivity.this)
                            .load(bitmap)
                            .apply(options)
                            .into(imgProfile);
                    callUpdatePhoto();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }





}


