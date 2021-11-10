package com.storelogflog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;


import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.fragment.StorageSelectionFragment;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.activity.LoginActivity;
import com.storelogflog.apiCall.ChecktTokenApiCall;
import com.storelogflog.apiCall.StorageDetailsApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.storageDetailsBean.StorageDetailsBean;
import com.storelogflog.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout llContinue;
    private LinearLayout llFindStorage,ll_CTA;
    private Fragment fragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {

        ((HomeActivity)getActivity()).enableViews(false,"Welcome to Store Log Fog");

        llContinue = view.findViewById(R.id.ll_continue);
        llFindStorage = view.findViewById(R.id.ll_find_storage);
        ll_CTA = view.findViewById(R.id.ll_CTA);
        HomeActivity.toolbar.setVisibility(View.VISIBLE);


    }

    @Override
    public void initListeners() {

        llContinue.setOnClickListener(this);
        llFindStorage.setOnClickListener(this);
        ll_CTA.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.ll_continue:
                fragment=new StorageSearchFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;

            case R.id.ll_find_storage:
                fragment=new StorageListFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;

            case R.id.ll_CTA:
                fragment=new StorageSelectionFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.top_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }





}
