package com.storelogflog.uk.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;



import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.CommonMsgActivity;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.activity.LoginActivity;
import com.storelogflog.uk.activity.RegisterActivity;
import com.storelogflog.uk.adapter.RegionAdapter;
import com.storelogflog.uk.adapter.UnitSpinnerAdapter;
import com.storelogflog.uk.apiCall.RegisterApiCall;
import com.storelogflog.uk.apiCall.StorageInquiryApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.bean.RegionBean;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ContactStorageFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {


    private AppCompatEditText editName;
    private AppCompatEditText editEmail;
    private AppCompatEditText editPhone;
    private AppCompatTextView txtUnit;
    private RelativeLayout rlUnit;
    private AppCompatEditText editComment;
    private AppCompatTextView txtSubmit;
    private AppCompatCheckBox cbAuthorise;
    private AppCompatCheckBox cbTermsAndCondition;

    private String storageName;
    private String storageId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_storage, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        txtSubmit=view.findViewById(R.id.txt_submit);
        editName=view.findViewById(R.id.edit_name);
        editEmail=view.findViewById(R.id.edit_email);
        editPhone=view.findViewById(R.id.edit_phone);
        txtUnit=view.findViewById(R.id.txt_unit);
        rlUnit=view.findViewById(R.id.rl_unit);
        editComment=view.findViewById(R.id.edit_comment);
        cbAuthorise=view.findViewById(R.id.cb_authorise);
        cbTermsAndCondition=view.findViewById(R.id.cb_terms_condition);

        hideShow();

        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        if (getArguments()!=null)
        {
            if (getArguments() != null) {

                storageId= getArguments().getString("storageId");
                storageName= getArguments().getString("storageName");
                HomeActivity.txtToolBarTitle.setText("Contact "+storageName+" Storage");
                cbAuthorise.setText("I authorise "+storageName+" Storage to contact me");

            }
        }


    }

    @Override
    public void initListeners() {

        txtSubmit.setOnClickListener(this);
        txtUnit.setOnClickListener(this);

        HomeActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick();
            }
        });

        cbTermsAndCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                startActivity(new Intent(getContext(), CommonMsgActivity.class)
                        .putExtra("id","2"));
            }
        });


    }

    public void hideShow()
    {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgBack.setVisibility(View.VISIBLE);
        HomeActivity.toolbar.setVisibility(View.VISIBLE);
        HomeActivity.imgSearch.setVisibility(View.GONE);
        HomeActivity.imgMenu.setVisibility(View.GONE);

    }





    void unitDialog()
    {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_sppiner_popup);
        dialog.setCancelable(true);
        ListView lvUnit=dialog.findViewById(R.id.listview);
        AppCompatTextView txtTitle=dialog.findViewById(R.id.txt_title);
        txtTitle.setText("Select Unit");


        final ArrayList<String> unitList=new ArrayList<>();

        unitList.add("Number of units you looking for");
        unitList.add("1");
        unitList.add("2");
        unitList.add("3");
        unitList.add("4");
        unitList.add("5");
        unitList.add("6");
        unitList.add("7");
        unitList.add("8");
        unitList.add("9");
        unitList.add("10");

        if(unitList!=null) {
            if (unitList.size() > 0) {
                UnitSpinnerAdapter adapter = new UnitSpinnerAdapter(unitList, getActivity());
                lvUnit.setAdapter(adapter);
            }
        }


        lvUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String unit=unitList.get(position);
                txtUnit.setText(unit);
                dialog.dismiss();


            }
        });

        dialog.show();


    }


    public boolean isValidate()
    {
        if(editName.getText().toString().isEmpty())
        {
            return showErrorMsg(editName,"Name can't be blank");
        }
        else if(editEmail.getText().toString().isEmpty())
        {
            return showErrorMsg(editEmail,"Email can't be blank");
        }
        else if(!Validator.isEmailValid(editEmail.getText().toString()))
        {
            return showErrorMsg(editEmail,"Invalid email");
        }
        else if(editPhone.getText().toString().isEmpty())
        {
            return showErrorMsg(editPhone,"Mobile number can't be blank");
        }
        else if(!Validator.isValidMobileNo(editPhone.getText().toString()))
        {
            return showErrorMsg(editPhone,"Invalid Mobile Number");
        }
        else if(txtUnit.getText().toString().isEmpty() || txtUnit.getText().toString().equals("Number of units you looking for"))
        {
            showToast(getActivity(),"Unit can't be blank");
            return false;
        }
        else if(!cbAuthorise.isChecked())
        {
            showToast(getActivity(),"please checked authorise storage");
            return false;
        }
        else if(!cbTermsAndCondition.isChecked())
        {
            showToast(getActivity(),"please checked terms and condition");
            return false;
        }
        else
        {
            return true;
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.txt_submit:
                callStorageInquiry();
                break;
            case R.id.txt_unit:
                unitDialog();
                break;

        }
    }



    void callStorageInquiry()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            if (isValidate())
            {
                try {
                    JSONObject jsonObjectPayload=new JSONObject();
                    jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                    jsonObjectPayload.put("name",editName.getText().toString());
                    jsonObjectPayload.put("email",editEmail.getText().toString());
                    jsonObjectPayload.put("phone",editPhone.getText().toString());
                    jsonObjectPayload.put("unit",txtUnit.getText().toString());
                    jsonObjectPayload.put("message",editComment.getText().toString());
                    jsonObjectPayload.put("storageid",storageId);
                    Logger.debug(TAG,jsonObjectPayload.toString());
                    String token=Utility.getJwtToken(jsonObjectPayload.toString());
                    new StorageInquiryApiCall(getContext(),this,token, Constants.STORAGE_INQUIRY_CODE);
                    showLoading("Loading...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        if(Constants.STORAGE_INQUIRY_CODE==code)
        {
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
                            Fragment fragment=new ThankYouFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("message",message);
                            bundle.putString("from",Constants.FROM_CONTACT_STORAGE);
                            fragment.setArguments(bundle);
                            Common.loadFragment(getActivity(),fragment,false,null);
                        }
                        else
                        {
                            Utility.commonMsgDialog(getContext(), "" + message, true, null);
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

        if(Constants.STORAGE_INQUIRY_CODE==code)
        {
            Logger.debug(TAG,""+Utility.returnErrorMsg(error,getContext()));
            hideLoading();
            showToast(getActivity(),Utility.returnErrorMsg(error,getContext()));
        }
    }
}




