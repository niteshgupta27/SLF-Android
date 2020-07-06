package com.storelogflog.uk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storelogflog.uk.R;
import com.storelogflog.uk.apiCall.AddAuctionItemApiCall;
import com.storelogflog.uk.apiCall.AddItemApiCall;
import com.storelogflog.uk.apiCall.AddStorageApiCall;
import com.storelogflog.uk.apiCall.AddpaymentStorageApiCall;
import com.storelogflog.uk.apiCall.RenewSubcriptionApiCall;
import com.storelogflog.uk.apiCall.StripeTokenApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.AddAuctionRequestBean;
import com.storelogflog.uk.bean.AddItemBean;
import com.storelogflog.uk.bean.AddStorageRequestBean;
import com.storelogflog.uk.bean.paymentBean.PaymentResponse;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.bean.subscriptionBean.Subscripiton;
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
import java.util.Calendar;
import java.util.Locale;


public class PaymentActivity extends BaseActivity implements VolleyApiResponseString {
    public static RelativeLayout rlMakePayment;
    public static ProgressBar progressBar;
    public Toolbar toolbar;
    public String from;
    String TAG = this.getClass().getSimpleName();
    Storage storage1;
    long amount = 0;
    private AddStorageRequestBean addStorageRequestBean;
    private AddAuctionRequestBean addAuctionRequestBean;
    private Subscripiton subscripiton;
    private AddItemBean addItemBean;
    private String paymentIntentClientSecret = null;
    private Stripe stripe;
    private CardInputWidget cardInputWidget;
    private TextView txt_amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        rlMakePayment = findViewById(R.id.rl_make_payment);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        progressBar = findViewById(R.id.progress_bar);
        txt_amount = findViewById(R.id.txt_amount);
        cardInputWidget.setPostalCodeEnabled(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent() != null) {
            from = getIntent().getStringExtra("FROM");
            if (from.equals("AddStorage")) {
                addStorageRequestBean = (AddStorageRequestBean) getIntent().getSerializableExtra("requestData");
                if (addStorageRequestBean != null) {
                    amount = addStorageRequestBean.getPond();
                    new PaymentTask().execute();
                    // callStripeToken(addStorageRequestBean.getPond()+"");
                    txt_amount.setText("Pay To buy a storage space for £" + addStorageRequestBean.getPond() + "/year");
                } else {
                    showToast("Something went wrong!");
                }


                toolbar.setTitle("Add Storage");

            } else if (from.equals("Subscription")) {
                subscripiton = (Subscripiton) getIntent().getSerializableExtra("subscription");
                if (subscripiton != null) {
                    String amountStr = getIntent().getStringExtra("amount");
                    amount = Long.parseLong(amountStr);
                    //callStripeToken(amountStr);
                    new PaymentTask().execute();
                } else {
                    showToast("Something went wrong!");
                }

                toolbar.setTitle("Renew");
            } else if (from.equals("AddAuction")) {
                addAuctionRequestBean = (AddAuctionRequestBean) getIntent().getSerializableExtra("requestData");
                if (addAuctionRequestBean != null) {

                    amount = Long.parseLong(addAuctionRequestBean.getAmout());
                    //callStripeToken(addAuctionRequestBean.getAmout());
                    new PaymentTask().execute();

                } else {
                    showToast("Something went wrong!");
                }

                toolbar.setTitle("Add Auction");
            } else if (from.equals("AddedItems")) {
                addItemBean = (AddItemBean) getIntent().getSerializableExtra("requestData");
                if (addItemBean != null) {
                    amount = addItemBean.getAmount();
                    Log.e("SelectedGridShapeID2", String.valueOf(addItemBean.getShape_id()));
                    new PaymentTask().execute();
                    storage1 = (Storage) getIntent().getSerializableExtra("storage");


                    txt_amount.setText("  I agree to pay £" + addItemBean.getAmount() + "to have my item valued");
                } else {
                    showToast("Something went wrong!");
                }


                toolbar.setTitle("Add Item");

            }


        }
    }

    @Override
    public void initListeners() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onClick(View view) {

    }

    private void startCheckout(final String paymentIntentClientSecret) {
        // ...

        // Hook up the pay button to the card widget and stripe instance

        rlMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();


                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    final Context context = getApplicationContext();
                    stripe = new Stripe(
                            context,
                            PaymentConfiguration.getInstance(context).getPublishableKey()
                    );
                    stripe.confirmPayment(PaymentActivity.this, confirmParams);


                    rlMakePayment.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    showToast("Please filled card information");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment

        if (resultCode == RESULT_OK) {
            if (data != null) {
                stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
            } else {
                showToast("Data not found!");
            }
        }

    }

    void callAddStorage(PaymentResponse paymentResponse, PaymentActivity activity) {
        if (Utility.isInternetConnected(PaymentActivity.this)) {

            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("name", addStorageRequestBean.getStorageName());
                jsonObjectPayload.put("descriptionshort", "test");
                jsonObjectPayload.put("descriptionlong", "test");
                jsonObjectPayload.put("address1", addStorageRequestBean.getAddress1());
                jsonObjectPayload.put("address2", addStorageRequestBean.getAddress2());
                jsonObjectPayload.put("country", "" + addStorageRequestBean.getCountry());
                jsonObjectPayload.put("region", "" + addStorageRequestBean.getRegion());
                jsonObjectPayload.put("city", "" + addStorageRequestBean.getCity());
                jsonObjectPayload.put("zip", "" + addStorageRequestBean.getZip());
                jsonObjectPayload.put("amount", "" + paymentResponse.getAmount());
                jsonObjectPayload.put("txnid", "" + paymentResponse.getId());
                jsonObjectPayload.put("date", "" + getDate(paymentResponse.getCreated()));
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("storage_shaps", addStorageRequestBean.getStorage_shaps());
                jsonObjectPayload.put("storage_type", addStorageRequestBean.getStorage_type());
                jsonObjectPayload.put("storage_doors", addStorageRequestBean.getStorage_doors());
                jsonObjectPayload.put("door_type ", addStorageRequestBean.getDoor_type());
                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new AddStorageApiCall(PaymentActivity.this, this, token, Constants.ADD_STORAGE_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast("No Internet Connection");
        }
    }

    void callPaymentStorage(PaymentResponse paymentResponse) {

        if (Utility.isInternetConnected(PaymentActivity.this)) {


            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("storage_id", "" + addItemBean.getStorage_id());
                jsonObjectPayload.put("amount", addItemBean.getAmount());
                jsonObjectPayload.put("txnid", "" + paymentResponse.getId());
                jsonObjectPayload.put("date", "" + getDate(paymentResponse.getCreated()));
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));

                Logger.debug(TAG, jsonObjectPayload.toString());
                Log.e("AddItemAPI", jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                 showLoading("Loading...");
                new AddpaymentStorageApiCall(PaymentActivity.this, this, token, Constants.Payment_Storage);

            } catch (Exception e) {
                Log.e("Error===>", e.toString());
            }


        } else {
            showToast("No Internet Connection");
        }
    }

    void callAddItem() {

        if (Utility.isInternetConnected(PaymentActivity.this)) {


            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("storage", "" + addItemBean.getStorage_id());
                jsonObjectPayload.put("unit", addItemBean.getUnit_id());
                jsonObjectPayload.put("name", addItemBean.getName());
                jsonObjectPayload.put("description", addItemBean.getDescription());
                jsonObjectPayload.put("length", addItemBean.getLength());
                jsonObjectPayload.put("width", "" + addItemBean.getWidth());
                jsonObjectPayload.put("height", "" + addItemBean.getHeight());
                jsonObjectPayload.put("qty", addItemBean.getQty());
                jsonObjectPayload.put("value", "" + addItemBean.getValue());
                jsonObjectPayload.put("unittype", addItemBean.getUnittype());
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("category", addItemBean.getCategory());
                jsonObjectPayload.put("shape_id", addItemBean.getShape_id());
                jsonObjectPayload.put("rack_id", addItemBean.getRack_id());
                jsonObjectPayload.put("currency",addItemBean.getCurrency());

                Logger.debug(TAG, jsonObjectPayload.toString());
                Log.e("AddItemAPI", jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                //   showLoading("Loading...");
                new AddItemApiCall(PaymentActivity.this, this, token, Constants.ADD_ITEM_CODE);

            } catch (Exception e) {
                Log.e("Error===>", e.toString());
            }
        } else {
            showToast("No Internet Connection");
        }
    }

    void callRenewSubcription(PaymentResponse paymentResponse) {
        if (Utility.isInternetConnected(PaymentActivity.this)) {

            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("storage", "" + subscripiton.getStorageID());
                jsonObjectPayload.put("amount", "" + paymentResponse.getAmount());
                jsonObjectPayload.put("txnid", "" + paymentResponse.getId());
                jsonObjectPayload.put("date", "" + getDate(paymentResponse.getCreated()));
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new RenewSubcriptionApiCall(PaymentActivity.this, this, token, Constants.RENEW_SUBSCRIPTION_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast("No Internet Connection");
        }
    }

    void callAddAuctionItemApi(PaymentResponse paymentResponse) {
        if (Utility.isInternetConnected(PaymentActivity.this)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("storage", "" + addAuctionRequestBean.getStorageId());
                jsonObjectPayload.put("unit", "" + addAuctionRequestBean.getUnitId());
                jsonObjectPayload.put("item", "" + addAuctionRequestBean.getItemId());
                jsonObjectPayload.put("title", "" + addAuctionRequestBean.getItemName());
                jsonObjectPayload.put("desp", "" + addAuctionRequestBean.getItemDescription());
                jsonObjectPayload.put("expectedvalue", "" + addAuctionRequestBean.getExpectedValue());
                jsonObjectPayload.put("amount", "" + paymentResponse.getAmount());
                jsonObjectPayload.put("txnid", "" + paymentResponse.getId());


                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new AddAuctionItemApiCall(PaymentActivity.this, this, token, Constants.ADD_AUCTION_ITEM_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast("No Internet Connection");
        }
    }

    void callStripeToken(String amount) {
        if (Utility.isInternetConnected(PaymentActivity.this)) {

            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("amount", "" + amount);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(Tag, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new StripeTokenApiCall(PaymentActivity.this, this, token, Constants.STRIPE_TOKEN_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast("No Internet Connection");
        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code) {
            case Constants.ADD_STORAGE_CODE:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                //   String message2="Thank you for the request. Once your unit id is validated and approved, you can start logging the item.";


                                startActivity(new Intent(PaymentActivity.this, HomeActivity.class).
                                        putExtra("message", message).
                                        putExtra("From", Constants.FROM_PAYMENT_SCREEN));
                                finish();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;


            case Constants.RENEW_SUBSCRIPTION_CODE:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                startActivity(new Intent(PaymentActivity.this, HomeActivity.class).
                                        putExtra("message", message).
                                        putExtra("From", "PaymentRenew"));
                                finish();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.STRIPE_TOKEN_CODE:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                startCheckout(getStringFromJsonObj(jsonObject, "Secrate"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ADD_AUCTION_ITEM_CODE:
                hideLoading();

                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                showToast(message);

                                if (result == 1) {
                                    startActivity(new Intent(PaymentActivity.this, HomeActivity.class).
                                            putExtra("message", message).
                                            putExtra("From", "AddAuction"));
                                    finish();
                                }
                                //fragment=new LogFragment();
                                // Common.loadFragment(getActivity(),fragment,false,null);
                            } else {
                                showToast(message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
            case Constants.Payment_Storage:

                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(Tag, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                callAddItem();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.ADD_ITEM_CODE:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("AddItemFragment", jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {

                                showToast(message);
                                startActivity(new Intent(PaymentActivity.this, HomeActivity.class).
                                        putExtra("From", "AddedItems").
                                        putExtra("storage", storage1).
                                        putExtra("sotrageId", String.valueOf(storage1.getID())).
                                        putExtra("itemId", String.valueOf(getIntFromJsonObj(jsonObject, "ItemId"))));
                                finish();

                            } else {
                                showToast(message);
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
            case Constants.ADD_STORAGE_CODE:
                hideLoading();
                break;

            case Constants.RENEW_SUBSCRIPTION_CODE:
                hideLoading();
                break;

            case Constants.STRIPE_TOKEN_CODE:
                hideLoading();
                break;

            case Constants.ADD_AUCTION_ITEM_CODE:
                hideLoading();
                break;


            case Constants.Payment_Storage:
                hideLoading();
                break;


            case Constants.ADD_ITEM_CODE:
                hideLoading();
                break;


        }
    }

    private String getDate(long time) {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<PaymentActivity> activityRef;

        PaymentResultCallback(@NonNull PaymentActivity activity) {
            activityRef = new WeakReference<>(activity);

            progressBar.setVisibility(View.GONE);
            rlMakePayment.setVisibility(View.VISIBLE);

        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            com.stripe.android.model.PaymentIntent paymentIntent = result.getIntent();
            com.stripe.android.model.PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == com.stripe.android.model.PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                if (paymentIntent != null) {
                    String response = gson.toJson(paymentIntent);
                    try {
                        if (response != null) {
                            JSONObject jsonObject = new JSONObject(response);


                            PaymentResponse paymentResponse = new Gson().fromJson(jsonObject.toString(), PaymentResponse.class);

                            if (paymentResponse != null) {
                                if (activity.from.equals("AddStorage")) {
                                    activity.callAddStorage(paymentResponse, activity);
                                } else if (activity.from.equals("Subscription")) {
                                    activity.callRenewSubcription(paymentResponse);
                                } else if (activity.from.equals("AddAuction")) {
                                    activity.callAddAuctionItemApi(paymentResponse);
                                } else if (activity.from.equals("AddedItems")) {
                                    activity.callPaymentStorage(paymentResponse);
                                }


                            } else {
                                activity.showToast("Data not found");
                            }

                        } else {
                            activity.showToast("Data not found");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        activity.showToast("Something went wrong");
                    }
                }
                Toast.makeText(activity, "Payment completed", Toast.LENGTH_SHORT).show();

                Log.e("success=", "Payment completed" + gson.toJson(paymentIntent));


            } else if (status == com.stripe.android.model.PaymentIntent.Status.RequiresPaymentMethod) {


                Toast.makeText(activity, "Payment failed", Toast.LENGTH_SHORT).show();
                Log.e("error=", "Payment failed");
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method

            Log.e("error=", "" + e.toString());

            Toast.makeText(activity, "" + e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private class PaymentTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String[] params2) {


            com.stripe.Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
            PaymentIntentCreateParams paymentIntentCreateParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(200L)
                            .setCurrency("GBP")
                            .build();
            try {
                PaymentIntent intent = PaymentIntent.create(paymentIntentCreateParams);
                paymentIntentClientSecret = intent.getClientSecret();

                Log.e("paymentInt", "" + paymentIntentClientSecret);

            } catch (StripeException e) {
                e.printStackTrace();
            }

            return paymentIntentClientSecret;
        }

        @Override
        protected void onPostExecute(String message) {
            //process message

            if (message != null) {
                startCheckout(message);
            } else {
                showToast("Something went wrong");
            }

        }
    }


}
