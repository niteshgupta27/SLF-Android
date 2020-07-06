package com.storelogflog.uk.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.StorageLeadApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;

import org.json.JSONException;
import org.json.JSONObject;


public class StorageLeadFragment extends BaseFragment implements VolleyApiResponseString, TextWatcher {

    private RelativeLayout rlSubmit;
    private AppCompatEditText editStorageName;
    private AppCompatEditText editContactPerson;
    private AppCompatEditText editContactNumber;
    private AppCompatEditText editContactAddress;
    private AppCompatEditText editStorageCity;
    private AppCompatEditText editMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lead_storage, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rlSubmit = view.findViewById(R.id.rl_submit);
        editStorageName = view.findViewById(R.id.edit_storage_name);
        editContactPerson = view.findViewById(R.id.edit_contact_person);
        editContactNumber = view.findViewById(R.id.edit_contact_number);
        editContactAddress = view.findViewById(R.id.edit_address);
        editStorageCity = view.findViewById(R.id.edit_city);
        editMessage = view.findViewById(R.id.edit_message);
        hideShow();

        ((HomeActivity) getActivity()).enableViews(true, "Contact Us");


    }

    @Override
    public void initListeners() {

        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getStorageLeadApi();
            }
        });

        editStorageName.addTextChangedListener(this);
        editContactPerson.addTextChangedListener(this);
        editContactNumber.addTextChangedListener(this);
        editContactAddress.addTextChangedListener(this);
        editStorageCity.addTextChangedListener(this);
        editMessage.addTextChangedListener(this);


    }

    public void hideShow() {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgBack.setVisibility(View.VISIBLE);
        HomeActivity.toolbar.setVisibility(View.VISIBLE);


        HomeActivity.imgSearch.setVisibility(View.GONE);
        HomeActivity.imgMenu.setVisibility(View.GONE);


    }

    public void getStorageLeadApi() {
        if (Utility.isInternetConnected(getActivity())) {

            if (isValidate()) {

                try {
                    JSONObject jsonObjectPayload = new JSONObject();
                    jsonObjectPayload.put("name", editStorageName.getText().toString());
                    jsonObjectPayload.put("email", editContactPerson.getText().toString());
                    jsonObjectPayload.put("phone", editContactNumber.getText().toString());
                    jsonObjectPayload.put("address", editContactAddress.getText().toString());
                    jsonObjectPayload.put("city", editStorageCity.getText().toString());
                    jsonObjectPayload.put("message", editMessage.getText().toString());
                    jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));

                    Logger.debug(TAG, jsonObjectPayload.toString());
                    String token = Utility.getJwtToken(jsonObjectPayload.toString());
                    new StorageLeadApiCall(getActivity(), this, token, Constants.STORAGE_LEAD_CODE);
                    showLoading("Loading...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showToast(getContext(), "No Internet Connection");
        }

    }

    public boolean isValidate() {
        Changebackground();
        if (editStorageName.getText().toString().isEmpty()) {
            return showErrorMsg(editStorageName, "Business name can't be blank");
        } /*else if (editContactPerson.getText().toString().isEmpty()) {

            return showErrorMsg(editContactPerson, "Contact person can't be blank");
        }*/ else if (editContactNumber.getText().toString().isEmpty()) {


            return showErrorMsg(editContactNumber, "Contact number can't be blank");
        } else if (!Validator.isValidMobileNo(editContactNumber.getText().toString())) {

            return showErrorMsg(editContactNumber, "Invalid Mobile Number");
        }/* else if (editContactAddress.getText().toString().isEmpty()) {

            return showErrorMsg(editContactAddress, "Address can't be blank");
        } */else if (editStorageCity.getText().toString().isEmpty()) {

            return showErrorMsg(editStorageCity, "Storage City can't be blank");
        } else {
            return true;
        }

    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        if (Constants.STORAGE_LEAD_CODE == code) {
            hideLoading();
            if (response != null) {
                String payload[] = response.split("\\.");
                if (payload[1] != null) {
                    response = Utility.decoded(payload[1]);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Logger.debug(TAG, "" + jsonObject.toString());
                        int result = getIntFromJsonObj(jsonObject, "result");
                        String message = getStringFromJsonObj(jsonObject, "Message");
                        if (result == 1) {
                            Fragment fragment = new ThankYouFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("message", message);
                            bundle.putString("from", Constants.FROM_STORAGE_LEAD);
                            fragment.setArguments(bundle);
                            Common.loadFragment(getActivity(), fragment, false, null);
                        } else {
                            showToast(getActivity(), message);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

    }


    private void Changebackground() {


        if (editStorageName.getText().toString().trim().isEmpty()) {
            editStorageName.setBackgroundResource(R.drawable.green_border_light);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);

        } /*else if (editContactPerson.getText().toString().trim().isEmpty()) {
            editContactPerson.setBackgroundResource(R.drawable.green_border_light);
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);

        }*/ else if (editContactNumber.getText().toString().trim().isEmpty()) {
            editContactNumber.setBackgroundResource(R.drawable.green_border_light);
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);

        } /*else if (editContactAddress.getText().toString().trim().isEmpty()) {
            editContactAddress.setBackgroundResource(R.drawable.green_border_light);
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);

        } */else if (editStorageCity.getText().toString().trim().isEmpty()) {
            editStorageCity.setBackgroundResource(R.drawable.green_border_light);
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);

        } /*else if (editMessage.getText().toString().trim().isEmpty()) {
            editMessage.setBackgroundResource(R.drawable.green_border_light);
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);

        }*/ else {
            editStorageName.setBackgroundResource(R.drawable.background_edit_square);
            editContactPerson.setBackgroundResource(R.drawable.background_edit_square);
            editContactNumber.setBackgroundResource(R.drawable.background_edit_square);
            editContactAddress.setBackgroundResource(R.drawable.background_edit_square);
            editStorageCity.setBackgroundResource(R.drawable.background_edit_square);
            editMessage.setBackgroundResource(R.drawable.background_edit_square);
        }
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
}
