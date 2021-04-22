package com.storelogflog.uk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.fragment.CardsFragment;
import com.storelogflog.uk.StorageSelection.fragment.ManageShelftFragment;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.ViewItemApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.itemListBean.ItemListBean;
import com.storelogflog.uk.bean.storageBean.Storage;

import org.json.JSONException;
import org.json.JSONObject;


public class LogFragment extends BaseFragment implements VolleyApiResponseString {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Storage storage;
    LinearLayout llTab;
    private AppCompatTextView txtErrorMsg;
    ItemListBean itemListBean;
    ImageView view_item, view_plan;
    Fragment fragment;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        view_item = view.findViewById(R.id.view_item);
        view_plan = view.findViewById(R.id.view_plan);

        if (getArguments() != null) {
            storage = (Storage) getArguments().getSerializable("storage");

            ((HomeActivity)getActivity()).enableViews(false,"Log");

        }

       /* tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);
        llTab=view.findViewById(R.id.ll_tab);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);


        tabLayout.addTab(tabLayout.newTab().setText("Item List"));
        tabLayout.addTab(tabLayout.newTab().setText("Auction Items"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(),getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

*/


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initListeners() {

       /* tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CardsFragment();
                bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putString("log", "log");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
            }
        });


        view_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ManageShelftFragment();
                bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);

            }
        });


    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context context;
        int totalTabs;
        Bundle bundle;
        ItemListBean itemListBean;

        public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);

            this.context = context;
            this.totalTabs = totalTabs;
            // this.itemListBean=itemListBean;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position) {

                case 0:
                    fragment = new ItemListFragment();
                    //  bundle=new Bundle();
                    //  bundle.putSerializable("itemList",itemListBean);
                    //   fragment.setArguments(bundle);
                    break;

                case 1:
                    fragment = new AuctionItemsListFragment();
                    // bundle=new Bundle();
                    // bundle.putSerializable("auctionList",itemListBean);
                    //  fragment.setArguments(bundle);
                    break;

                default:
                    return fragment;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }


    void callViewItemApi(Storage storage) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("unit", "" + PreferenceManger.getPreferenceManger().getString(PrefKeys.UNITID));
                jsonObjectPayload.put("storage", "" + PreferenceManger.getPreferenceManger().getString(PrefKeys.STORAGEID));


                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new ViewItemApiCall(getContext(), this, token, Constants.VIEW_ITEM_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showToast(getActivity(), "No Internet Connection");
        }
    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code) {
            case Constants.VIEW_ITEM_CODE:
                if (response != null) {
                    hideLoading();
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            //   String message=getStringFromJsonObj(jsonObject,"message");

                            try {
                               /* itemListBean=new Gson().fromJson(response.toString(), ItemListBean.class);
                                ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(),getChildFragmentManager(),tabLayout.getTabCount(),itemListBean);
                                viewPager.setAdapter(adapter);*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (result == 1) {
                                // ItemListBean itemListBean=new Gson().fromJson(response.toString(), ItemListBean.class);
                                //  ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(),getChildFragmentManager(),tabLayout.getTabCount(),itemListBean);
                                //  viewPager.setAdapter(adapter);

                                if (itemListBean != null) {

                                    // txtErrorMsg.setVisibility(View.GONE);
                                    // llTab.setVisibility(View.VISIBLE);
                                    //  ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(),getChildFragmentManager(),tabLayout.getTabCount(),itemListBean);
                                    //  viewPager.setAdapter(adapter);
                                } else {
                                    // txtErrorMsg.setVisibility(View.VISIBLE);
                                    //llTab.setVisibility(View.GONE);
                                }
                            } else {
                                //txtErrorMsg.setVisibility(View.VISIBLE);
                                // llTab.setVisibility(View.GONE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //txtErrorMsg.setVisibility(View.VISIBLE);
                            //llTab.setVisibility(View.GONE);
                        }
                    } else {
                        // txtErrorMsg.setVisibility(View.VISIBLE);
                        // llTab.setVisibility(View.GONE);
                    }

                } else {
                    //txtErrorMsg.setVisibility(View.VISIBLE);
                    // llTab.setVisibility(View.GONE);
                }
                break;


        }

    }


    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code) {
            case Constants.VIEW_ITEM_CODE:
                hideLoading();
                txtErrorMsg.setVisibility(View.VISIBLE);
                llTab.setVisibility(View.GONE);
                txtErrorMsg.setText("" + Utility.returnErrorMsg(error, getContext()));
                break;
        }
    }


}
