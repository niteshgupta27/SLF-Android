package com.storelogflog.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.storelogflog.R;
import com.storelogflog.adapter.RegionAdapter;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.apputil.VolleyMultipartRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContactUsActivity extends BaseActivity {

    public Toolbar toolbar;
    private AppCompatTextView txtReason;
    private RelativeLayout rlReason;
    private AppCompatEditText editSubject;
    private AppCompatEditText editMsg;
    private LinearLayout llAddAttachMent;
    private AppCompatTextView txtSubmit;
    private AppCompatTextView txtAttachment;
    private String reason = "";
    private Bitmap bitmap = null;
    private static int GALLARY_PIC = 1009;
    private static int CAMERA_PIC = 1008;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        txtReason = findViewById(R.id.txt_reason);
        rlReason = findViewById(R.id.rl_region);
        editSubject = findViewById(R.id.edit_subject);
        editMsg = findViewById(R.id.edit_msg);
        llAddAttachMent = findViewById(R.id.ll_add_attachment);
        txtSubmit = findViewById(R.id.txt_submit);
        txtAttachment = findViewById(R.id.txt_attachment);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("Contact Us");


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void initListeners() {

        rlReason.setOnClickListener(this);
        txtReason.setOnClickListener(this);
        llAddAttachMent.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_region:
                regionDialog();
                break;
            case R.id.txt_reason:
                regionDialog();
                break;
            case R.id.ll_add_attachment:
                requestStorageAndCameraPermissionWithAlertDialog(ContactUsActivity.this,GALLARY_PIC,CAMERA_PIC);
                break;
            case R.id.txt_submit:
                callContactUsApi();
                break;

        }
    }

    void callContactUsApi() {
        if (Utility.isInternetConnected(ContactUsActivity.this)) {

            if (isValidate()) {
                try {
                    JSONObject jsonObjectPayload = new JSONObject();
                    jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                    jsonObjectPayload.put("reason", "" + txtReason.getText().toString());
                    jsonObjectPayload.put("subject", "" + editSubject.getText().toString());
                    jsonObjectPayload.put("message", "" + editMsg.getText().toString());

                    Logger.debug(Tag, jsonObjectPayload.toString());

                    String token = Utility.getJwtToken(jsonObjectPayload.toString());
                    uploadImageToServer(bitmap, token);
                    showLoading("Loading...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            showToast("No Internet Connection");
        }

    }


    public boolean isValidate() {

        if (txtReason.getText().toString().isEmpty()) {
            showToast("Please select reason");
            return false;
        } else if (editSubject.getText().toString().isEmpty()) {
            showToast("Please select subject");
            return false;
        } else if (editMsg.getText().toString().isEmpty()) {
            showToast("Please select message");
            return false;
        }
//        else if (bitmap == null) {
//            showToast("Please attach photo");
//            return false;
//        }
        else {

            return true;
        }


    }


    void regionDialog() {
        final Dialog dialog = new Dialog(ContactUsActivity.this);

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView listView = dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle = dialog.findViewById(R.id.txt_title);
        txtTitle.setText("Select your Reason");



        final ArrayList<String> reagonList = new ArrayList();
        String s1 ="Need Help or assistance";
        String s2 ="Have a complaint";
        String s3 ="Bug";
        String s4 ="Other";
        reagonList.add(s1);
        reagonList.add(s2);
        reagonList.add(s3);
        reagonList.add(s4);

       /* for (int i = 0; i < 4; i++) {
            reagonList.add("Need Help or assistance");
            reagonList.add("Have a complaint");
            reagonList.add("Bug");
            reagonList.add("Other");
        }*/

        RegionAdapter adapter = new RegionAdapter(reagonList, ContactUsActivity.this);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                txtReason.setText(reagonList.get(position));
                reason = reagonList.get(position);
                dialog.dismiss();

            }
        });

        dialog.show();


    }


    private void uploadImageToServer(final Bitmap bitmap, final String token) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.BASE_URL + Constants.API_CONTACT_US, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                hideLoading();
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
                                finish();

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
        Volley.newRequestQueue(ContactUsActivity.this).add(multipartRequest);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            if (requestCode == GALLARY_PIC) {
                try {
                    Uri selectedFileUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFileUri);
                    Uri tempUri = getImageUri(ContactUsActivity.this, bitmap);
                    txtAttachment.setText("Edit attachment");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_PIC) {
                try {


                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");

                    txtAttachment.setText("Edit attachment");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }


}
