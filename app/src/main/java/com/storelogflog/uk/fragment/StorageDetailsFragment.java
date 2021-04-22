package com.storelogflog.uk.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.fragment.CardsFragment;
import com.storelogflog.uk.StorageSelection.fragment.ManageShelftFragment;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.adapter.StorageUnitAdapter;
import com.storelogflog.uk.apiCall.StorageDetailsApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.bean.storageDetailsBean.Photo;
import com.storelogflog.uk.bean.storageDetailsBean.StorageDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class StorageDetailsFragment extends BaseFragment implements VolleyApiResponseString {

    Context mContext;
    int[] colorsActive;
    int[] colorsInactive;
    Storage storage;
    private RecyclerView rvStorageUnit;
    private StorageUnitAdapter adapter;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private AppCompatImageView imgContactUs;
    private CustomPagerAdapter customPagerAdapter;
    private int[] layouts;
    private TextView[] dots;
    private AppCompatTextView txtTitle;
    private AppCompatTextView txtAddressLine1;
    private AppCompatTextView txtAddressLine2;
    private AppCompatTextView txtDescription;
    private Fragment fragment;
    private Bundle bundle;
    private AppCompatImageView imgCall;
    private AppCompatImageView imgPlaceHolder;
    private AppCompatImageView imgGoogle;
    private RelativeLayout imgChat, img_card, imgLog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storage_details, container, false);

        mContext = getActivity();
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        viewPager = view.findViewById(R.id.view_pager);
        dotsLayout = view.findViewById(R.id.ll_dots);
        rvStorageUnit = view.findViewById(R.id.rv_storage_unit);
        imgContactUs = view.findViewById(R.id.img_contact_us);
        txtTitle = view.findViewById(R.id.txt_title);
        txtAddressLine1 = view.findViewById(R.id.txt_address_line1);
        txtAddressLine2 = view.findViewById(R.id.txt_address_line2);
        txtDescription = view.findViewById(R.id.txt_description);
        imgCall = view.findViewById(R.id.img_call);
        imgPlaceHolder = view.findViewById(R.id.img_place_holder);
        imgGoogle = view.findViewById(R.id.img_google);
        imgChat = view.findViewById(R.id.img_chat);
        img_card = view.findViewById(R.id.img_card);
        imgLog = view.findViewById(R.id.img_log);


        if (getArguments() != null) {

            storage = (Storage) getArguments().getSerializable("storage");
            if (storage != null) {
                callStorageDetails(storage.getID());

            }
        }

        hideShow();

    }

    void updateUi(StorageDetailsBean storageDetailsBean) {

        ((HomeActivity) getActivity()).enableViews(true, "" + storageDetailsBean.getStorage().getName());

        txtTitle.setText("" + storageDetailsBean.getStorage().getName());
        txtAddressLine1.setText("" + storageDetailsBean.getStorage().getAddress1());
        txtAddressLine2.setText("" + storageDetailsBean.getStorage().getAddress2());
        txtDescription.setText("" + storageDetailsBean.getStorage().getDescripiton());


        if (storageDetailsBean.getPhotos() != null && storageDetailsBean.getPhotos().size() > 0) {
            viewPager.setVisibility(View.VISIBLE);
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


            if (storageDetailsBean.getUnits() != null && storageDetailsBean.getUnits().size() > 0) {
                adapter = new StorageUnitAdapter(getActivity(), storageDetailsBean.getUnits());
                rvStorageUnit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rvStorageUnit.setAdapter(adapter);
            }

        } else {
            viewPager.setVisibility(View.GONE);
            imgPlaceHolder.setVisibility(View.VISIBLE);
        }


        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(storageDetailsBean.getStorage().getReviews())) {

                    if (!storageDetailsBean.getStorage().getReviews().equals(" ")) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(storageDetailsBean.getStorage().getReviews()));
                        startActivity(i);
                    } else {
                        showToast(getActivity(), "URL is not available!");
                    }
                } else {
                    showToast(getActivity(), "URL is not available!");
                }

            }
        });

    }

    @Override
    public void initListeners() {


        imgContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storage.getUnitID()!=null) {

                    if (!String.valueOf(storage.getUnitID()).equals(" ")) {

                        fragment = new ContactStorageFragment();
                        bundle = new Bundle();
                        bundle.putString("storageId", "" + storage.getID());
                        bundle.putString("storageName", "" + storage.getName());
                        fragment.setArguments(bundle);
                        Common.loadFragment(getActivity(), fragment, true, null);

                    }
                }
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


        imgLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (storage.getUnitID()!=null) {

                    if (!String.valueOf(storage.getUnitID()).equals("")) {

                        fragment = new CardsFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(getActivity(), fragment, true, null);
                    }
                }
            }
        });


        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storage.getUnitID()!=null) {

                    if (!String.valueOf(storage.getUnitID()).equals(" ")) {

                        fragment = new ChatingFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(getActivity(), fragment, true, null);

                    }
                }
            }
        });


        img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storage.getUnitID()!=null) {

                    if (!String.valueOf(storage.getUnitID()).equals(" ")) {

                        fragment = new ManageShelftFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(getActivity(), fragment, true, null);
                    }
                }
            }
        });


    }

    private void addBottomDots(int currentPage) {

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


    public void hideShow() {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);

        HomeActivity.imgBack.setVisibility(View.VISIBLE);
        HomeActivity.imgMenu.setVisibility(View.GONE);
        HomeActivity.imgSearch.setVisibility(View.GONE);
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        hideLoading();
        switch (code) {
            case Constants.STORAGE_DETAILS_CODE:
                if (response != null) {

                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
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

        hideLoading();
        switch (code) {
            case Constants.STORAGE_DETAILS_CODE:
                hideLoading();
                break;
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast(getContext(), "No Internet Connection");
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
