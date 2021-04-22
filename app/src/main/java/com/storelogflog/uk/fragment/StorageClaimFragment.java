package com.storelogflog.uk.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.StorageClaimApiCall;
import com.storelogflog.uk.apiCall.StorageDetailsApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.Validator;
import com.storelogflog.uk.bean.searchStorageBean.SearchStorage;
import com.storelogflog.uk.bean.storageDetailsBean.Photo;
import com.storelogflog.uk.bean.storageDetailsBean.StorageDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class StorageClaimFragment extends BaseFragment implements VolleyApiResponseString {

    Context mContext;
    int[] colorsActive;
    int[] colorsInactive;
    CheckBox tenant_check, term_check;
    RelativeLayout policy_linear, tenant_linear;
    private RelativeLayout rlSubmit;
    private AppCompatCheckBox cbTermsAndConditions;
    private AppCompatEditText editStorageUnit;
    private AppCompatEditText editStorageName;
    private AppCompatEditText editBillingEmail;
    private AppCompatTextView txtAddressLine1;
    private AppCompatTextView txtAddressLine2;
    private SearchStorage storage;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private AppCompatImageView imgContactUs;
    private CustomPagerAdapter customPagerAdapter;
    private int[] layouts;
    private TextView[] dots;
    private Fragment fragment;
    private Bundle bundle;
    private AppCompatImageView imgCall;
    private AppCompatImageView imgPlaceHolder;
    private FrameLayout flBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storage_claim, container, false);

        mContext = getActivity();
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {

        viewPager = view.findViewById(R.id.view_pager);
        dotsLayout = view.findViewById(R.id.ll_dots);
        imgContactUs = view.findViewById(R.id.img_contact_us);
        imgCall = view.findViewById(R.id.img_call);

        rlSubmit = view.findViewById(R.id.rl_submit);
        cbTermsAndConditions = view.findViewById(R.id.cb_terms_and_conditions);
        editStorageUnit = view.findViewById(R.id.edit_storage_unit);
        editStorageName = view.findViewById(R.id.edit_storage_name);
        editBillingEmail = view.findViewById(R.id.edit_billing_email);
        txtAddressLine1 = view.findViewById(R.id.txt_address_line1);
        txtAddressLine2 = view.findViewById(R.id.txt_address_line2);
        imgPlaceHolder = view.findViewById(R.id.img_place_holder);
        flBanner = view.findViewById(R.id.fl_banner);
        tenant_check = view.findViewById(R.id.tenant_check);
        term_check = view.findViewById(R.id.term_check);
        policy_linear = view.findViewById(R.id.policy_linear);
        tenant_linear = view.findViewById(R.id.tenant_linear);


        if (getArguments() != null) {
            storage = (SearchStorage) getArguments().getSerializable("storage");

            if (storage != null) {
                // updateUi(storage);
                callStorageDetails(storage.getID());
            }
        }


    }

    void updateUi(StorageDetailsBean storageDetailsBean) {
        ((HomeActivity) getActivity()).enableViews(true, storageDetailsBean.getStorage().getName());

        txtAddressLine1.setText("" + storageDetailsBean.getStorage().getAddress1());
        txtAddressLine2.setText("" + storageDetailsBean.getStorage().getAddress2());


        if (storageDetailsBean.getPhotos() != null && storageDetailsBean.getPhotos().size() > 0) {
            flBanner.setVisibility(View.VISIBLE);
            imgPlaceHolder.setVisibility(View.GONE);

            layouts = new int[storageDetailsBean.getPhotos().size()];
            dots = new TextView[storageDetailsBean.getPhotos().size()];
            colorsActive = new int[storageDetailsBean.getPhotos().size()];
            colorsInactive = new int[storageDetailsBean.getPhotos().size()];


            for (int i = 0; i < storageDetailsBean.getPhotos().size(); i++) {
                colorsActive[i] = getResources().getColor(R.color.dot_active);
                colorsInactive[i] = getResources().getColor(R.color.dot_inactive);
                layouts[i] = R.layout.item_banner_image;
            }


            customPagerAdapter = new CustomPagerAdapter(storageDetailsBean.getPhotos());
            viewPager.setAdapter(customPagerAdapter);
            addBottomDots(0);


        } else {
            flBanner.setVisibility(View.GONE);
            imgPlaceHolder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListeners() {

        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callStorageClaimApi();
            }
        });


        policy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new CommonMsgFragment();
                bundle = new Bundle();
                bundle.putString("id", "2");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
            }
        });


        imgContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new ContactStorageFragment();
                bundle = new Bundle();
                bundle.putString("storageId", "" + storage.getID());
                bundle.putString("storageName", "" + storage.getName());
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: +9899056789"));
                startActivity(callIntent);
            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                addBottomDots(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void hideShow() {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgBack.setVisibility(View.VISIBLE);


        HomeActivity.imgSearch.setVisibility(View.GONE);
        HomeActivity.imgMenu.setVisibility(View.GONE);

    }

    private void addBottomDots(int currentPage) {

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dots = new TextView[layouts.length];


        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(6, 0, 6, 0);
            dotsLayout.addView(dots[i], layoutParams);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    public boolean isValidate() {

        if (editStorageUnit.getText().toString().isEmpty()) {
            return showErrorMsg(editStorageUnit, "Name can't be blank");
        } else if (editStorageName.getText().toString().isEmpty()) {
            return showErrorMsg(editStorageName, "Storage name can't be blank");
        } else if (editBillingEmail.getText().toString().isEmpty()) {
            return showErrorMsg(editBillingEmail, "Storage unit can't be blank");
        } else if (!Validator.isEmailValid(editBillingEmail.getText().toString())) {
            return showErrorMsg(editBillingEmail, "Invalid email");
        }else if (!Validator.isEmailValid(editBillingEmail.getText().toString())) {
            return showErrorMsg(editBillingEmail, "Invalid email");
        }else if (!tenant_check.isChecked()) {
            showToast(mContext,"Please accept the tenant unit check");
            return false;
        } else if (!term_check.isChecked()) {
            showToast(mContext,"Please accept terms & condition's");
            return false;
        } else {
            return true;
        }

    }

    void callStorageClaimApi() {
        if (Utility.isInternetConnected(getActivity())) {
            if (isValidate()) {
                try {
                    JSONObject jsonObjectPayload = new JSONObject();
                    jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                    jsonObjectPayload.put("storageid", storage.getID());
                    jsonObjectPayload.put("unitnumber", editStorageUnit.getText().toString());
                    jsonObjectPayload.put("name", editStorageName.getText().toString());
                    jsonObjectPayload.put("billingemail", editStorageName.getText().toString());

                    Logger.debug(TAG, jsonObjectPayload.toString());
                    String token = Utility.getJwtToken(jsonObjectPayload.toString());
                    new StorageClaimApiCall(getContext(), this, token, Constants.STORAGE_CLAIM_CODE);
                    showLoading("Loading...");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showToast(getActivity(), "No Internet Connection");
        }
    }

    public void callStorageDetails(int storageId) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("StorageID", "" + storageId);
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new StorageDetailsApiCall(getActivity(), this, token, Constants.STORAGE_DETAILS_CODE);
                showLoading("Loading...");

                Log.e(TAG,"Input_storage_detail====>"+jsonObjectPayload.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast(getContext(), "No Internet Connection");
        }

    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {
        hideLoading();

        switch (code) {
            case Constants.STORAGE_CLAIM_CODE:

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
                                //NavDirections directions=StorageClaimFragmentDirections.actionStorageClaimFragmentToThankYouFragment(message,Constants.FROM_STORAGE_CLAIM);
                                //Navigation.findNavController(getActivity(),R.id.main).navigate(directions);

                                Fragment fragment = new ThankYouFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("message", message);
                                bundle.putString("from", Constants.FROM_STORAGE_CLAIM);
                                fragment.setArguments(bundle);
                                Common.loadFragment(getActivity(), fragment, false, null);
                            } else {
                                Utility.commonMsgDialog(getContext(), "" + message, true, null);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.STORAGE_DETAILS_CODE:
                if (response != null) {

                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {

                            Log.e(TAG,"STORAGE_DETAILS_CODE=======>"+response);
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {
                                StorageDetailsBean storageDetailsBean = new Gson().fromJson(response.toString(), StorageDetailsBean.class);
                                if (storageDetailsBean != null) {
                                    updateUi(storageDetailsBean);
                                }
                            } else {


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

        if (Constants.STORAGE_CLAIM_CODE == code) {
            Logger.debug(TAG, "" + Utility.returnErrorMsg(error, getContext()));
            hideLoading();
            showToast(getActivity(), Utility.returnErrorMsg(error, getContext()));
        } else if (Constants.STORAGE_DETAILS_CODE == code) {
            Logger.debug(TAG, "" + Utility.returnErrorMsg(error, getContext()));
            hideLoading();
            showToast(getActivity(), Utility.returnErrorMsg(error, getContext()));
        }
    }

    public class CustomPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private List<Photo> photoBannerList;

        public CustomPagerAdapter(List<Photo> photoBannerList) {

            this.photoBannerList = photoBannerList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            AppCompatImageView imgBanner = view.findViewById(R.id.img_banner);
            Photo photo = photoBannerList.get(position);
            Utility.loadImage(getActivity(), photo.getURL(), imgBanner);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
