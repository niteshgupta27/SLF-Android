package com.storelogflog.uk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.apiCall.ForgotPasswordApiCall;
import com.storelogflog.uk.apiCall.RegisterApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;

import org.json.JSONException;
import org.json.JSONObject;


public class ForgotActivity extends BaseActivity implements View.OnClickListener, VolleyApiResponseString {

    String TAG = this.getClass().getSimpleName();
    private AppCompatEditText editEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        initListeners();

    }

    @Override
    public void initViews() {
        findViewById(R.id.ll_submit).setOnClickListener(this);

        editEmail=findViewById(R.id.edit_email);


        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Forgot Password");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    public void initListeners() {


    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_submit:
                forgotPassword();
                break;
            case R.id.img_back:
                onBackPressed();
                break;


        }
    }



  void forgotPassword()
  {

      if(Utility.isInternetConnected(ForgotActivity.this))
      {
          if(editEmail.getText().toString().isEmpty())
          {
              showErrorMsg(editEmail,"Email can't be blank");
          }
          else if(!Validator.isEmailValid(editEmail.getText().toString()))
          {
              showErrorMsg(editEmail,"Invalid email");
          }
          else
          {
              try {
                  JSONObject jsonObjectPayload=new JSONObject();
                  jsonObjectPayload.put("email",editEmail.getText().toString());
                  Logger.debug(TAG,jsonObjectPayload.toString());
                  String token=Utility.getJwtToken(jsonObjectPayload.toString());
                  new ForgotPasswordApiCall(ForgotActivity.this,this,token, Constants.FORGOT_PASSWORD_CODE);
                  showLoading("Please wait...");

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

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        if(Constants.FORGOT_PASSWORD_CODE==code)
        {
            hideLoading();
            if(response!=null)
            {

                String payload[]=response.split("\\.");
                response=Utility.decoded( payload[1]);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Logger.debug(TAG,""+jsonObject.toString());
                    int result=getIntFromJsonObj(jsonObject,"result");
                    String message=getStringFromJsonObj(jsonObject,"message");
                    if(result==1)
                    {
                        startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                        finish();
                    }

                    showToast(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }



    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        Logger.debug(TAG,""+Utility.returnErrorMsg(error,ForgotActivity.this));
        hideLoading();
    }
}
