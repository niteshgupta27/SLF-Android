package com.storelogflog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.apiCall.GetUserProfileDataApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.profileBean.Profile;
import com.storelogflog.bean.profileBean.ProfileBean;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.callBackInterFace.DrawerLocker;
import com.storelogflog.fragment.DashBoardFragment;
import com.storelogflog.fragment.FlogFragment;
import com.storelogflog.fragment.HomeFragment;
import com.storelogflog.fragment.LogFragment;
import com.storelogflog.fragment.PhotoFragment;
import com.storelogflog.fragment.SettingFragment;
import com.storelogflog.fragment.StorageListFragment;
import com.storelogflog.fragment.StoreFragment;
import com.storelogflog.fragment.ThankYouFragment;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DrawerLocker, VolleyApiResponseString {

    public static AppCompatImageView imgMenu;
    public static AppCompatImageView imgBack;
    public static AppCompatImageView imgSearch;
    public static AppCompatTextView txtToolBarTitle;
    public static Toolbar toolbar;
    public static HomeActivity homeActivity;
    public AppCompatTextView txtUserName;
    public AppCompatTextView txtEmail;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private LinearLayout llHeader;
    private Fragment fragment;
    private Bundle bundle;
    private CircleImageView imgProfile;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeActivity = this;
       super.onCreate(savedInstanceState);
        //     FirebaseCrashlytics.getInstance().setCustomKey("str_key", "hello");

        if (getIntent() != null) {
            String from = getIntent().getStringExtra("From");
            if (from.equals("Login")) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class)
                        .putExtra("From", "Login"));
            }
        }


        setContentView(R.layout.activity_navigation_drawer);
        initViews();

        initListeners();


        if (getIntent() != null) {
            String from = getIntent().getStringExtra("From");

            if (from.equals(Constants.FROM_SPLASH_SCREEN) || from.equals("LoginToDashBoard")) {
                Common.loadFragment(HomeActivity.this, new DashBoardFragment(), false, Common.DASHBOARD_FRAGMENT);

            } else {
                Common.loadFragment(HomeActivity.this, new HomeFragment(), false, Common.HOME_FRAGMENT);
            }

            if (from.equals(Constants.FROM_PAYMENT_SCREEN)) {

                Fragment fragment = new ThankYouFragment();
                Bundle bundle = new Bundle();
                bundle.putString("message", getIntent().getStringExtra("message"));
                bundle.putString("from", Constants.FROM_Add_STORAGE);
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, false, null);


            } else if (from.equals("PaymentRenew")) {
                Fragment fragment = new ThankYouFragment();
                Bundle bundle = new Bundle();
                bundle.putString("message", getIntent().getStringExtra("message"));
                bundle.putString("from", "PaymentRenew");
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, false, null);

            } else if (from.equals("AddAuction")) {
                Fragment fragment = new ThankYouFragment();
                Bundle bundle = new Bundle();
                bundle.putString("message", "Thank you, item submitted for valuation successfully.");
                bundle.putString("from", "AddAuction");
                bundle.putSerializable("storage", (Storage) getIntent().getSerializableExtra("storage"));
                fragment.setArguments(bundle);

                Common.loadFragment(HomeActivity.this, fragment, false, null);
            } else if (from.equals("AddedItems")) {

                Fragment fragment = new PhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", (Storage) getIntent().getSerializableExtra("storage"));
              
                bundle.putString("sotrageId", getIntent().getStringExtra("sotrageId"));
                bundle.putString("itemId", getIntent().getStringExtra("itemId"));
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, false, null);
            } else if (from.equals(Constants.FROM_Add_STORAGE)) {
                Fragment fragment = new ThankYouFragment();
                Bundle bundle = new Bundle();
                bundle.putString("message", getIntent().getStringExtra("message"));
                bundle.putString("from", Constants.FROM_PAYMENT_SCREEN);
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, false, null);

            } else if (from.equals("StorageRenew")) {
                fragment = new StoreFragment();
                bundle = new Bundle();
                bundle.putString("from", "store");
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, true, null);

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        callGetProfileApi();


    }

    public void initViews() {

        imgMenu = findViewById(R.id.img_menu);
        imgBack = findViewById(R.id.img_back);
        imgSearch = findViewById(R.id.img_search);
        toolbar = findViewById(R.id.toolbar);
        txtToolBarTitle = findViewById(R.id.txt_toolbar_title);
        drawerLayout = findViewById(R.id.drawer_layout);


        final NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        llHeader = headerView.findViewById(R.id.ll_header);

        txtUserName = headerView.findViewById(R.id.txt_user_name);
        txtEmail = headerView.findViewById(R.id.txt_email);
        imgProfile = headerView.findViewById(R.id.img_profile);

        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



    }

    public void enableViews(boolean enable, String title) {

        toolbar.setTitle(title);
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (enable) {
            //You may not want to open the drawer on swipe from the left in this case
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            //You must regain the power of swipe for the drawer.
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            actionBarDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }


    public void initListeners() {

        imgMenu.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        llHeader.setOnClickListener(this);
    }


    public void updateUi(Profile profile) {
        txtUserName.setText("" + profile.getFirstName()+""+profile.getLastName());
        txtEmail.setText("" + profile.getEmail());

        Log.e("profileImage",profile.getImage());

        PreferenceManger.getPreferenceManger().setString(PrefKeys.USERFIRSTNAME,profile.getFirstName());
        PreferenceManger.getPreferenceManger().setString(PrefKeys.USERLASTNAME,profile.getLastName());

        if (profile.getImage() != null) {
            Utility.loadImage(HomeActivity.this,profile.getImage(), imgProfile);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.dashBoardFragment:
                Common.loadFragment(HomeActivity.this, new DashBoardFragment(), true, null);
                break;
            case R.id.storeFragment:
                fragment = new StoreFragment();
                bundle = new Bundle();
                bundle.putString("from", "store");
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, true, null);


                break;
            case R.id.logFragment:
                fragment = new StoreFragment();
                bundle = new Bundle();
                bundle.putString("from", "log");
                fragment.setArguments(bundle);
                Common.loadFragment(HomeActivity.this, fragment, true, null);
                break;
            case R.id.flogFragment:
                fragment = new FlogFragment();
                Common.loadFragment(HomeActivity.this, fragment, true, null);
                break;
            case R.id.storageListFragment:
                fragment = new StorageListFragment();
                Common.loadFragment(HomeActivity.this, fragment, true, null);
                break;
            case R.id.action_subscription:
                startActivity(new Intent(HomeActivity.this, SubscriptionActivity.class));
                break;
            case R.id.notificationActivity:
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                break;
            case R.id.settingFragment:
                fragment = new SettingFragment();
                Common.loadFragment(HomeActivity.this, fragment, true, null);
                break;
            case R.id.nav_aboutus:
                startActivity(new Intent(HomeActivity.this, CommonMsgActivity.class)
                        .putExtra("id", "1"));
                break;
            case R.id.contactUsFragment:
                startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                break;
            case R.id.nav_logout:
                logout();
                break;

        }

        drawerLayout.closeDrawers();
        return false;

    }

    void logout() {

        //PreferenceManger.getPreferenceManger().clearSession();
        PreferenceManger.getPreferenceManger().setBoolean(PrefKeys.ISLOGIN, false);
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_profile:
                // drawerLayout.openDrawer(Gravity.LEFT);
                drawerLayout.closeDrawers();
                break;
            case R.id.ll_header:
                drawerLayout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class)
                        .putExtra("From", "Home"));
                break;
        }
    }

    @Override
    public void setDrawerLocked(boolean shouldLock) {

        if (shouldLock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void callGetProfileApi() {
        if (Utility.isInternetConnected(HomeActivity.this)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new GetUserProfileDataApiCall(HomeActivity.this, this, token, Constants.GET_PROFILE_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast("No Internet Connection");
        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        hideLoading();

        switch (code) {
            case Constants.GET_PROFILE_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            Log.e("Profile_response", jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {
                                ProfileBean profileBean = new Gson().fromJson(response.toString(), ProfileBean.class);
                                if (profileBean != null && profileBean.getProfile() != null) {
                                    updateUi(profileBean.getProfile());

                                    Log.e("Image", profileBean.getProfile().getImage());
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

            case Constants.GET_PROFILE_CODE:
                break;
        }
    }

    public void EnableView(boolean enable) {
        if (enable) {
            onBackPressed();

        }
    }
}
