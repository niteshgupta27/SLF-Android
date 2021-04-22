package com.storelogflog.uk.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.apiCall.GetAllCountryApiCall;
import com.storelogflog.uk.apiCall.LoginApiCall;
import com.storelogflog.uk.apiCall.SetPasswordApiCall;
import com.storelogflog.uk.apiCall.SocialLoginApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.bean.countryBean.CountryBean;
import com.storelogflog.uk.bean.login.LoginBean;
import com.storelogflog.uk.dialog.SetPassword;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends BaseActivity implements View.OnClickListener, VolleyApiResponseString, GoogleApiClient.OnConnectionFailedListener {

    String TAG = this.getClass().getSimpleName();
    private AppCompatEditText editEmail;
    private AppCompatEditText editPassword;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private final int RC_SIGN_IN=9001;
    private String email2="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();

        printKeyHash();
        byte[] sha1KeyHash = {(byte) 0x9A, (byte) 0xB0, (byte) 0x98, (byte) 0x16, (byte) 0x76, (byte) 0x23, (byte) 0xB5, (byte) 0x9A, (byte) 0x55, (byte) 0x83, (byte) 0x00, (byte) 0xFF, (byte) 0x76, (byte) 0x0E, (byte) 0xB6, (byte) 0xB9, (byte) 0xC6, (byte) 0xFE, (byte) 0xA3, (byte) 0xC7};
        Log.e("keyHashForFacebookLogin", Base64.encodeToString(sha1KeyHash, Base64.NO_WRAP));
    }

    @Override
    public void initViews() {

        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);

        LoginManager.getInstance().logOut();
        callbackManager = CallbackManager.Factory.create();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut();
        PreferenceManger.getPreferenceManger().setBoolean(PrefKeys.ISLOGIN,false);

    }


    public void initListeners() {

        findViewById(R.id.txt_submit).setOnClickListener(this);
        findViewById(R.id.login_with_email).setOnClickListener(this);
        findViewById(R.id.txt_forgot_password).setOnClickListener(this);
        findViewById(R.id.txt_terms_and_condition).setOnClickListener(this);
        findViewById(R.id.login_with_fb).setOnClickListener(this);
        findViewById(R.id.rl_lgoin_with_google).setOnClickListener(this);
        findViewById(R.id.txt_privacy_policy).setOnClickListener(this);
    }


    void callAllCountryApi()
    {
        if(Utility.isInternetConnected(LoginActivity.this))
        {
            new GetAllCountryApiCall(LoginActivity.this,this,null, Constants.ALL_COUNTRY_CODE);

        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_submit:
                login();
                break;
            case R.id.txt_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                break;

            case R.id.login_with_email:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;

            case R.id.txt_terms_and_condition:
                startActivity(new Intent(LoginActivity.this, CommonMsgActivity.class)
                .putExtra("id","2"));
                break;

            case R.id.txt_privacy_policy:
                startActivity(new Intent(LoginActivity.this, CommonMsgActivity.class)
                        .putExtra("id","3"));
                break;


            case R.id.login_with_fb:

                if(Utility.isInternetConnected(LoginActivity.this))
                {
                    loginFB();
                }
                else
                {
                    showToast("No Internet Connection");
                }

                break;


            case R.id.rl_lgoin_with_google:

                if(Utility.isInternetConnected(LoginActivity.this))
                {
                    signIn();
                }
                else
                {
                    showToast("No Internet Connection");
                }

                break;
        }
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {

        if(account!=null)
        {
            if(!account.getDisplayName().equals("") && account.getDisplayName()!=null)
            {
                email2=account.getEmail();

                if(account.getDisplayName()!=null)
                {
                    if (account.getDisplayName().contains(" "))
                    {
                        String name[]=account.getDisplayName().split(" ");
                        socialLoginApi(name[0],name[1],account.getEmail(),account.getId(),false);
                    }
                    else
                    {
                        socialLoginApi(account.getDisplayName(),"",account.getEmail(),account.getId(),false);

                    }
                }



            }
        }
    }


    public void login() {
        if (Utility.isInternetConnected(LoginActivity.this)) {
            if (isValidate()) {
                try {

                    JSONObject jsonObjectPayload = new JSONObject();
                    jsonObjectPayload.put("email", editEmail.getText().toString());
                    jsonObjectPayload.put("password", editPassword.getText().toString());
                    jsonObjectPayload.put("devicetype", Constants.DEVICE_TYPE);
                    jsonObjectPayload.put("fcm", ""+PreferenceManger.getPreferenceManger().getString(PrefKeys.FCMTOKEN));
                    jsonObjectPayload.put("devicemanufacture", android.os.Build.MANUFACTURER);
                    jsonObjectPayload.put("modelname", Build.MODEL);
                    jsonObjectPayload.put("modelnumber", Build.MODEL);
                    jsonObjectPayload.put("osver", Build.VERSION.RELEASE);
                    jsonObjectPayload.put("devicename", android.os.Build.DEVICE);


                    Logger.debug(TAG, jsonObjectPayload.toString());

                    String token = Utility.getJwtToken(jsonObjectPayload.toString());
                    new LoginApiCall(LoginActivity.this, this, token, Constants.LOGIN_CODE);
                    showLoading("Login...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showToast("No Internet Connection");
        }

    }

    public void setPassword(String email,String password) {
        if (Utility.isInternetConnected(LoginActivity.this)) {
            try {

                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("email", email);
                jsonObjectPayload.put("password",password);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));

                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new SetPasswordApiCall(LoginActivity.this, this, token, Constants.SET_PASSWORD_CODE);
                showLoading("Login...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast("No Internet Connection");
        }

    }


    public boolean isValidate() {
        if (editEmail.getText().toString().isEmpty()) {
            return showErrorMsg(editEmail, "Email can't be blank");
        } else if (!Validator.isEmailValid(editEmail.getText().toString())) {
            return showErrorMsg(editEmail, "Invalid email");
        } else if (editPassword.getText().toString().isEmpty()) {
            return showErrorMsg(editPassword, "Password can't be blank");
        } else if (editPassword.getText().toString().length() < 6) {
            return showErrorMsg(editPassword, "Password can't be less than 6 digits");
        } else {
            return true;
        }

    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.LOGIN_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response",response);
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


                                if (loginBean.getIsProfileUpdated()==0)
                                {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                            .putExtra("From","Login"));
                                    finish();
                                }
                                else if (loginBean.getIsProfileUpdated()==1)
                                {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                            .putExtra("From","LoginToDashBoard"));
                                    finish();
                                }



                             /*   Utility.commonMsgDialog(LoginActivity.this, "" + message, false, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        finish();
                                    }
                                });*/

                            } else {

                                Utility.commonMsgDialog(LoginActivity.this, "" + message, true, null);

                            }

                            //showToast(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.SOCIAL_LOGIN_CODE:
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
                                PreferenceManger.getPreferenceManger().setBoolean(PrefKeys.ISLOGIN,true);


                                int popupShow = getIntFromJsonObj(jsonObject, "PopupShow");

                                if (popupShow==1)
                                {
                                    new SetPassword(LoginActivity.this,email2) {
                                        @Override
                                        public void onClickSubmit(String email, String password) {

                                            setPassword(email,password);

                                        }
                                    };

                                }
                                else
                                {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                            .putExtra("From","LoginToDashBoard"));
                                    finish();
                                }



                            } else {

                                Utility.commonMsgDialog(LoginActivity.this, "" + message, true, null);

                            }

                            //showToast(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

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
                            if(result==1)
                            {
                                CountryBean countryBean=new Gson().fromJson(response, CountryBean.class);
                                PreferenceManger.getPreferenceManger().setObject(PrefKeys.COUNTRY_LIST,countryBean);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.SET_PASSWORD_CODE:
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

                                 startActivity(new Intent(LoginActivity.this, ProfileActivity.class)
                                .putExtra("From","Login"));
                                 finish();

                            } else {

                                showToast(message);
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
            case Constants.LOGIN_CODE:
                Logger.debug(TAG, "" + Utility.returnErrorMsg(error, LoginActivity.this));
                hideLoading();
                showToast(Utility.returnErrorMsg(error, LoginActivity.this));
                break;

            case Constants.SOCIAL_LOGIN_CODE:
                Logger.debug(TAG, "" + Utility.returnErrorMsg(error, LoginActivity.this));
                hideLoading();
                showToast(Utility.returnErrorMsg(error, LoginActivity.this));

            case Constants.SET_PASSWORD_CODE:
                Logger.debug(TAG, "" + Utility.returnErrorMsg(error, LoginActivity.this));
                hideLoading();
                showToast(Utility.returnErrorMsg(error, LoginActivity.this));


        }


    }



    void loginFB()
    {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        getFbUserProfile(loginResult.getAccessToken());
                        Logger.debug("result","Login facbook....");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Logger.debug("cancel","Login facbook....");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code

                        Logger.error("error","Login facbook...."+exception.getMessage());
                    }
                });
    }



    void getFbUserProfile(AccessToken accessToken) {
        GraphRequest request=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String   email="";
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");

                    if(object.has("email"))
                    {
                        email = object.getString("email");
                        email2 = object.getString("email");
                    }

                    String   id = object.getString("id");
                    String   picture = object.getString("picture");


                    Logger.debug("firstName = ", " " + firstName);
                    Logger.debug("last_name = ", " " + lastName);
                    Logger.debug("email = ", " " + email);
                    Logger.debug("id = ", " " + id);
                    Logger.debug("picture = ", " " + picture);

                    socialLoginApi(firstName,lastName,email,id,true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void socialLoginApi(String firstName,String lastName,String email,String id,boolean isFB)
    {
        try {

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            Log.e("ip",""+ip);

            JSONObject jsonObjectPayload = new JSONObject();
            jsonObjectPayload.put("firstname", firstName);
            jsonObjectPayload.put("lastname", lastName);
            jsonObjectPayload.put("email", email);
           // jsonObjectPayload.put("password", "123456");
            jsonObjectPayload.put("devicetype", Constants.DEVICE_TYPE);
            jsonObjectPayload.put("fcm", ""+PreferenceManger.getPreferenceManger().getString(PrefKeys.FCMTOKEN));
            jsonObjectPayload.put("IP", "104.15.54.58");
            jsonObjectPayload.put("devicemanufacture", android.os.Build.MANUFACTURER);
            jsonObjectPayload.put("modelname", Build.MODEL);
            jsonObjectPayload.put("modelnumber", Build.MODEL);
            jsonObjectPayload.put("osver", Build.VERSION.RELEASE);
            jsonObjectPayload.put("devicename", android.os.Build.DEVICE);
            jsonObjectPayload.put("apple_id","");


            if(isFB)
            {
                jsonObjectPayload.put("fbid",id);
                jsonObjectPayload.put("googleid","");
            }
            else
            {
                jsonObjectPayload.put("fbid","");
                jsonObjectPayload.put("googleid",id);
            }


            Logger.debug(TAG, jsonObjectPayload.toString());
            String token = Utility.getJwtToken(jsonObjectPayload.toString());
            new SocialLoginApiCall(LoginActivity.this, this, token, Constants.SOCIAL_LOGIN_CODE);
            showLoading("Login...");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
          //  GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleResult(result);

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }


    @Override
    protected void onResume() {
        super.onResume();
        callAllCountryApi();
    }

    private void printKeyHash() {
        /* Add code to print out the key hash */
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES); //replace com.demo with your package name
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("DeveloperKeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT));
                MessageDigest mda = MessageDigest.getInstance("SHA-1");
                mda.update(signature.toByteArray());
                Log.e("releseKeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:", e.toString());
        }
    }

    public void hashFromSHA1(String sha1) {
        String[] arr = sha1.split(":");
        byte[] byteArr = new  byte[arr.length];

        for (int i = 0; i< arr.length; i++) {
            byteArr[i] = Integer.decode("0x" + arr[i]).byteValue();
        }

        Log.e("hash : ", Base64.encodeToString(byteArr, Base64.NO_WRAP));
    }
}
