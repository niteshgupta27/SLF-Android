package com.storelogflog.uk.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.adapter.CityAdapter;
import com.storelogflog.uk.adapter.RegionAdapter2;
import com.storelogflog.uk.apiCall.AddStorageApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseJson;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.AddStorageRequestBean;
import com.storelogflog.uk.bean.cityBean.CityBean;
import com.storelogflog.uk.bean.regionBean.RegionBean;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.facebook.FacebookSdk.getApplicationContext;


public class PaymentFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    public static RelativeLayout rlMakePayment;
    private AddStorageRequestBean addStorageRequestBean;
    private String paymentIntentClientSecret=null;
    private Stripe stripe;
    private  CardInputWidget cardInputWidget;
    public static ProgressBar progressBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rlMakePayment=view.findViewById(R.id.rl_make_payment);
        cardInputWidget=view.findViewById(R.id.cardInputWidget);
        progressBar=view.findViewById(R.id.progress_bar);
        ((HomeActivity)getActivity()).enableViews(true,"Payment");

        if(getArguments()!=null)
        {
             addStorageRequestBean= (AddStorageRequestBean) getArguments().getSerializable("requestData");

             if(addStorageRequestBean!=null)
             {
               new PaymentTask().execute();

             }
        }





    }

    @Override
    public void initListeners() {

      //  rlMakePayment.setOnClickListener(this);
}



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

        }
    }

    private void startCheckout(final String paymentIntentClientSecret) {
        // ...

        // Hook up the pay button to the card widget and stripe instance

        rlMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

                if (params != null) {

                    Toast.makeText(getActivity(),"dfgg",Toast.LENGTH_SHORT).show();
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                //    final Context context = getApplicationContext();
                    final Context context = getActivity();
                    stripe = new Stripe(
                            context,
                            PaymentConfiguration.getInstance(context).getPublishableKey()
                    );
                    stripe.confirmPayment((HomeActivity)onBackHandler, confirmParams);

                    rlMakePayment.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    showToast(getActivity(),"Please filled card information");
                }
            }
        });


    }


    void callAddStorage()
    {
        if(Utility.isInternetConnected(getActivity()))
        {

            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("name",addStorageRequestBean.getStorageName());
                jsonObjectPayload.put("descriptionshort","test");
                jsonObjectPayload.put("descriptionlong","test");
                jsonObjectPayload.put("address1",addStorageRequestBean.getAddress1());
                jsonObjectPayload.put("address2",addStorageRequestBean.getAddress2());
                jsonObjectPayload.put("country",""+addStorageRequestBean.getCountry());
                jsonObjectPayload.put("region",""+addStorageRequestBean.getRegion());
                jsonObjectPayload.put("city",""+addStorageRequestBean.getCity());
                jsonObjectPayload.put("zip",""+addStorageRequestBean.getZip());
              //  jsonObjectPayload.put("amount","1200");
           //     jsonObjectPayload.put("txnid","DESE3343");
               // jsonObjectPayload.put("date","2020-10-03");
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new AddStorageApiCall(getActivity(),this,token, Constants.ADD_STORAGE_CODE);

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
                            if(result==1)
                            {
                                String message2="Thank you for the request. Once your unit id is validated and approved, you can start logging the item.";

                                Fragment fragment=new ThankYouFragment();
                                Bundle bundle=new Bundle();
                                bundle.putString("message",message);
                                bundle.putString("from",Constants.FROM_PAYMENT_SCREEN);
                                fragment.setArguments(bundle);
                                Common.loadFragment(getActivity(),fragment,false,null);
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
            case Constants.ADD_STORAGE_CODE:
                hideLoading();
                break;
        }
    }


    private class PaymentTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String[] params2) {


            com.stripe.Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
            PaymentIntentCreateParams paymentIntentCreateParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(1099L)
                            .setCurrency("usd")
                            .build();
            try {
                PaymentIntent intent = PaymentIntent.create(paymentIntentCreateParams);
                paymentIntentClientSecret = intent.getClientSecret();

                Log.e("paymentInt",""+paymentIntentClientSecret);



            } catch (StripeException e) {
                e.printStackTrace();
            }


            return paymentIntentClientSecret;
        }

        @Override
        protected void onPostExecute(String message) {
            //process message

            if(message!=null)
            {
                startCheckout(message);
            }
            else
            {
                showToast(getActivity(),"Something went wrong");
            }

        }
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        if (data!=null)
        {
            stripe.onPaymentResult(requestCode, data, new PaymentResultCallback((HomeActivity) onBackHandler));
            showToast(getContext(),"data found");
        }
        else
        {
            showToast(getContext(),"data not found");
        }

    }



    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<HomeActivity> activityRef;

        PaymentResultCallback(@NonNull HomeActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {

            progressBar.setVisibility(View.GONE);
            rlMakePayment.setVisibility(View.VISIBLE);

            final HomeActivity activity = activityRef.get();
            if (activity == null) {

                Log.e("activty_null","on_success");

                return;
            }

            com.stripe.android.model.PaymentIntent paymentIntent = result.getIntent();
            com.stripe.android.model.PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == com.stripe.android.model.PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
               /* activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );*/

                progressBar.setVisibility(View.GONE);
                rlMakePayment.setVisibility(View.VISIBLE);

                Log.e("success=","Payment completed");
                Log.e("success=","Payment completed"+gson.toJson(paymentIntent));
            } else if (status == com.stripe.android.model.PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
               /* activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );*/
                progressBar.setVisibility(View.GONE);
                rlMakePayment.setVisibility(View.VISIBLE);

                Log.e("error=","Payment failed");
            }
        }

        @Override
        public void onError(@NonNull Exception e) {

            progressBar.setVisibility(View.GONE);
            rlMakePayment.setVisibility(View.VISIBLE);
            final HomeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method

            Log.e("error=",""+e.toString());




            // activity.displayAlert("Error", e.toString(), false);
        }
    }

}
