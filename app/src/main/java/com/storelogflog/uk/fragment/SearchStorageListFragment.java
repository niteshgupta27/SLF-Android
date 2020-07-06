package com.storelogflog.uk.fragment;

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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.adapter.LocalStorageSearchAdapter;
import com.storelogflog.uk.apiCall.SearchStorageListApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.searchStorageBean.SearchStorage;
import com.storelogflog.uk.bean.searchStorageBean.SearchStorageBean;
import com.storelogflog.uk.bean.storageBean.StorageBean;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;
import com.storelogflog.uk.apputil.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchStorageListFragment extends BaseFragment implements VolleyApiResponseString, SearchView.OnQueryTextListener {

    private RecyclerView rvStorage;
    private AppCompatTextView txtErrorMsg;
    private LocalStorageSearchAdapter adapter;
    private LinearLayout llContinue;
    private Fragment fragment;
    private Bundle bundle;
    private List<SearchStorage> storageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_storage_list, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {
        ((HomeActivity)getActivity()).enableViews(false,"Storage Yard Search");

        rvStorage=view.findViewById(R.id.rv_local_storage);
        llContinue=view.findViewById(R.id.ll_continue);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);

        rvStorage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        HomeActivity.toolbar.setVisibility(View.VISIBLE);


        if (getArguments() != null) {
            String searchText=getArguments().getString("searchText");
            callSearchStroageApi(searchText);
        }

    }

    void callSearchStroageApi(String searchText)
    {
        JSONObject jsonObjectPayload = null;
        try {
            jsonObjectPayload = new JSONObject();
            jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
            jsonObjectPayload.put("text",searchText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showLoading("Storage List...");
        String token = Utility.getJwtToken(jsonObjectPayload.toString());
        new SearchStorageListApiCall(getActivity(), this, token, Constants.SEARCH_STORAGE_CODE);

    }

    @Override
    public void initListeners() {

        llContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment =new StorageLeadFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
            }
        });
    }

    public void hideShow()
    {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgSearch.setVisibility(View.VISIBLE);
        HomeActivity.imgMenu.setVisibility(View.VISIBLE);
        HomeActivity.toolbar.setVisibility(View.VISIBLE);

        HomeActivity.imgBack.setVisibility(View.GONE);
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        hideLoading();
        switch (code)
        {
            case Constants.SEARCH_STORAGE_CODE:
                if (response != null) {

                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {
                                SearchStorageBean storageBean= new Gson().fromJson(response, SearchStorageBean.class);
                                if (storageBean!=null && storageBean.getStorage()!=null && storageBean.getStorage().size()>0)
                                {
                                    storageList=storageBean.getStorage();
                                    adapter = new LocalStorageSearchAdapter(getActivity(),storageBean.getStorage());
                                    rvStorage.setAdapter(adapter);
                                    rvStorage.setVisibility(View.VISIBLE);
                                    txtErrorMsg.setVisibility(View.GONE);
                                }
                                else
                                {
                                    rvStorage.setVisibility(View.GONE);
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                }
                            } else {

                                rvStorage.setVisibility(View.GONE);
                                txtErrorMsg.setVisibility(View.VISIBLE);

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
        switch (code)
        {
            case Constants.STORAGE_LIST_CODE:
                rvStorage.setVisibility(View.GONE);
                txtErrorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText(""+Utility.returnErrorMsg(error,getActivity()));
                break;

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menu.clear();

        getActivity().getMenuInflater().inflate(R.menu.top_menu,menu);

        MenuItem actionEditItem=menu.findItem(R.id.action_edit_item);
        actionEditItem.setVisible(false);

        MenuItem actionEditPhoto=menu.findItem(R.id.action_edit_photo);
        actionEditPhoto.setVisible(false);

        MenuItem actionDeletePhoto=menu.findItem(R.id.action_delete_item);
        actionDeletePhoto.setVisible(false);

        MenuItem actionSearch=menu.findItem(R.id.action_done);
        actionSearch.setVisible(false);



        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.action_edit_photo:

                break;
            case R.id.action_delete_item:

                break;
            case R.id.action_edit_item:

                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        if(storageList!=null && storageList.size()>0)
        {
            adapter.getFilter().filter(query);

        }
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(storageList!=null && storageList.size()>0)
        {
            adapter.getFilter().filter(newText);

        }

        return false;
    }
}
