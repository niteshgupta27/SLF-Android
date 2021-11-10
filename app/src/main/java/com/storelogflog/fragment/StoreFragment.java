package com.storelogflog.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.adapter.StoreAdapter;
import com.storelogflog.apiCall.GetUserStorageListApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.bean.storageBean.StorageBean;
import com.storelogflog.apputil.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class StoreFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString, SearchView.OnQueryTextListener {

    private StoreAdapter adapter;
    private RecyclerView rvStore;
    private com.google.android.material.floatingactionbutton.FloatingActionButton menu_floating;
    private Fragment fragment;
    private String from="store",ItemId ="";
    private Bundle bundle;
    List<Storage> storageList;
    private AppCompatTextView txtErrorMsg;
    public static StoreFragment instance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initViews(view);
        initListeners();

        instance = StoreFragment.this;

        return view;
    }


    @Override
    public void initViews(View view) {

        rvStore=view.findViewById(R.id.rv_store);
       /* fbAddOwn=view.findViewById(R.id.fb_add_own);
        fbAddAnother=view.findViewById(R.id.fb_add_another);*/
        menu_floating = view.findViewById(R.id.menu_floating);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);


       if (getArguments()!=null)
        {
             from=getArguments().getString("from");

            if (from.equals("store"))
            {
                ((HomeActivity)getActivity()).enableViews(false,"My Storage Locations");

                if (!String.valueOf(getArguments().getString(Constants.ItemID)).equals("null")) {
                    ItemId = String.valueOf(getArguments().getString(Constants.ItemID));
                    menu_floating.setVisibility(View.GONE);
                }
            }
            else if(from.equals("log"))
            {
                ((HomeActivity)getActivity()).enableViews(false,"Log");
                menu_floating.setVisibility(View.GONE);
            }



        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserStorageList();
    }

    @Override
    public void initListeners() {

     /*   fbAddAnother.setOnClickListener(this);
        fbAddOwn.setOnClickListener(this);
*/
        menu_floating.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
           /* case R.id.fb_add_another:

                fragment=new HomeFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
            case R.id.fb_add_own:
                fragment=new StorageSelectionFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;*/

            case  R.id.menu_floating:
                fragment=new HomeFragment();
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
        }
    }


    public void getUserStorageList()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new GetUserStorageListApiCall(getActivity(),this,token, Constants.USER_STORAGE_LIST_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getContext(),"No Internet Connection");
        }

    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.USER_STORAGE_LIST_CODE:
                hideLoading();
                if(response!=null)
                {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        Log.e("payload", String.valueOf(payload[1]));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result=getIntFromJsonObj(jsonObject,"result");

                            if(result==1)
                            {
                                txtErrorMsg.setVisibility(View.GONE);
                                rvStore.setVisibility(View.VISIBLE);

                                StorageBean storageBean= new Gson().fromJson(response, StorageBean.class);

                                if (storageBean!=null && storageBean.getStorage()!=null && storageBean.getStorage().size()>0)
                                {

                                    storageList=storageBean.getStorage();
                                    if (from.equals("store"))
                                    {


                                        adapter = new StoreAdapter(getActivity(),storageBean.getStorage(),StoreAdapter.STORE,ItemId);
                                        rvStore.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                        rvStore.setAdapter(adapter);
                                    }
                                    else if(from.equals("log"))
                                    {
                                        /*if(storageBean.getStorage().size()==1)
                                        {
                                            PreferenceManger.getPreferenceManger().setString(PrefKeys.UNITID,storageBean.getStorage().get(0).getUnitID());
                                            PreferenceManger.getPreferenceManger().setString(PrefKeys.STORAGEID,storageBean.getStorage().get(0).getID()+"");
                                            PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_STORAGE_BEAN,storageBean.getStorage().get(0));
                                            fragment=new LogFragment();
                                            bundle=new Bundle();
                                            bundle.putSerializable("storage",storageBean.getStorage().get(0));
                                            fragment.setArguments(bundle);
                                            Common.loadFragment(getActivity(),fragment,true,null);
                                        }
                                        else
                                        {
                                        */    adapter = new StoreAdapter(getActivity(),storageBean.getStorage(),StoreAdapter.LOG, ItemId);
                                            rvStore.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                            rvStore.setAdapter(adapter);
                                       // }


                                    }


                                }
                                else
                                {
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                    rvStore.setVisibility(View.GONE);
                                }



                            }
                            else
                            {
                                txtErrorMsg.setVisibility(View.VISIBLE);
                                rvStore.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    txtErrorMsg.setVisibility(View.VISIBLE);
                    rvStore.setVisibility(View.GONE);
                    txtErrorMsg.setText("Server Error!");
                }
                break;
        }

    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code) {
            case Constants.USER_STORAGE_LIST_CODE:
                hideLoading();
                txtErrorMsg.setVisibility(View.VISIBLE);
                rvStore.setVisibility(View.GONE);
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
