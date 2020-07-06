package com.storelogflog.uk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.adapter.NotificationsAdapter;
import com.storelogflog.uk.adapter.SubscriptionAdapter;
import com.storelogflog.uk.apiCall.NotiifcationListApiCall;
import com.storelogflog.uk.apiCall.PricingApiCall;
import com.storelogflog.uk.apiCall.SubcriptionListApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.notificationBean.NotificationBean;
import com.storelogflog.uk.bean.subscriptionBean.Subscripiton;
import com.storelogflog.uk.bean.subscriptionBean.SubscriptionBean;
import com.storelogflog.uk.callBackInterFace.RenewSubscription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.storelogflog.uk.apputil.Constants.pound;

public class SubscriptionActivity extends BaseActivity implements VolleyApiResponseString, SearchView.OnQueryTextListener {

    private RecyclerView rvSubcription;
    private AppCompatTextView txtErrorMsg;
    private Toolbar toolbar;
    private SubscriptionAdapter adapter;
    private List<Subscripiton>subscripitonList;
    private long pondValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        rvSubcription=findViewById(R.id.rv_subscription);
        txtErrorMsg=findViewById(R.id.txt_user_msg);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("Subcription");
        callSubscriptionListApi();
        callPricingApi();
    }

    @Override
    public void initListeners() {

    }



    @Override
    public void onClick(View view) {

    }


    void callSubscriptionListApi()
    {
        if(Utility.isInternetConnected(SubscriptionActivity.this))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new SubcriptionListApiCall(SubscriptionActivity.this,this,token, Constants.SUBSCRIPTION_LIST_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.SUBSCRIPTION_LIST_CODE:
                hideLoading();

                if(response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(Tag,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {

                                SubscriptionBean subscriptionBean=new Gson().fromJson(response.toString(), SubscriptionBean.class);

                                if(subscriptionBean!=null && subscriptionBean.getSubscripiton()!=null && subscriptionBean.getSubscripiton().size()>0)
                                {
                                    rvSubcription.setVisibility(View.VISIBLE);
                                    txtErrorMsg.setVisibility(View.GONE);

                                    subscripitonList=subscriptionBean.getSubscripiton();

                                    adapter = new SubscriptionAdapter(SubscriptionActivity.this, subscriptionBean.getSubscripiton(), new RenewSubscription() {
                                        @Override
                                        public void renewClick(Subscripiton subscripiton) {

                                            startActivity(new Intent(SubscriptionActivity.this, PaymentActivity.class)
                                                    .putExtra("subscription",subscripiton)
                                                    .putExtra("FROM","Subscription")
                                                    .putExtra("amount",""+pondValue));
                                        }
                                    });

                                    rvSubcription.setLayoutManager(new LinearLayoutManager(SubscriptionActivity.this, LinearLayoutManager.VERTICAL, false));
                                    rvSubcription.setAdapter(adapter);

                                }
                                else
                                {

                                    rvSubcription.setVisibility(View.GONE);
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                rvSubcription.setVisibility(View.GONE);
                                txtErrorMsg.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            rvSubcription.setVisibility(View.GONE);
                            txtErrorMsg.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    rvSubcription.setVisibility(View.GONE);
                    txtErrorMsg.setVisibility(View.VISIBLE);
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
                            Logger.debug(Tag,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            if(result==1)
                            {
                                pondValue=getIntFromJsonObj(jsonObject,"RenewCost");
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
            case Constants.SUBSCRIPTION_LIST_CODE:
                hideLoading();
                rvSubcription.setVisibility(View.GONE);
                txtErrorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText(""+Utility.returnErrorMsg(error,SubscriptionActivity.this));
                break;

            case Constants.PRICING_CODE:
                hideLoading();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.common_menu,menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {

        }

        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        if(subscripitonList!=null && subscripitonList.size()>0)
        {
            adapter.getFilter().filter(query);

        }
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(subscripitonList!=null && subscripitonList.size()>0)
        {
            adapter.getFilter().filter(newText);

        }

        return false;
    }

    void callPricingApi()
    {
        if(Utility.isInternetConnected(SubscriptionActivity.this))
        {
            try {

                new PricingApiCall(SubscriptionActivity.this,this,null, Constants.PRICING_CODE);
                showLoading("Loading...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast("No Internet Connection");
        }
    }


}
