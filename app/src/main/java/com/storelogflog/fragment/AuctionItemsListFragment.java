package com.storelogflog.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.adapter.AuctionItemsListAdapter;
import com.storelogflog.adapter.ItemListAdapter;
import com.storelogflog.apiCall.ViewItemApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.itemListBean.Auction;
import com.storelogflog.bean.itemListBean.ItemListBean;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AuctionItemsListFragment extends BaseFragment implements SearchView.OnQueryTextListener,VolleyApiResponseString {

    private RecyclerView rvItemList;
    private AuctionItemsListAdapter adapter;
    private Fragment fragment;
    private Bundle bundle;
    private Storage storage;
    private ItemListBean itemListBean;
    List<Auction> auctionList;
    private AppCompatTextView txtErrorMsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auction_item_list, container, false);
        initViews(view);
        initListeners();

        return view;
    }


    @Override
    public void initViews(View view) {
        rvItemList=view.findViewById(R.id.rv_item_list);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);

        callViewItemApi();

    /*    if (getArguments() != null) {

            itemListBean= (ItemListBean) getArguments().getSerializable("auctionList");
            if (itemListBean != null) {

                if (itemListBean!=null && itemListBean.getAuction() !=null && itemListBean.getAuction().size()>0)
                {

                    Log.e("auctionList",""+auctionList.size());

                    auctionList=itemListBean.getAuction();
                    adapter = new AuctionItemsListAdapter(getActivity(),itemListBean.getAuction());
                    rvItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvItemList.setAdapter(adapter);

                    txtErrorMsg.setVisibility(View.GONE);
                    rvItemList.setVisibility(View.VISIBLE);
                }
                 else
                    {
                        txtErrorMsg.setVisibility(View.VISIBLE);
                        rvItemList.setVisibility(View.GONE);
                    }

            }
            else
            {   txtErrorMsg.setVisibility(View.VISIBLE);
                rvItemList.setVisibility(View.GONE);
            }
        }
        else
        {
            txtErrorMsg.setVisibility(View.VISIBLE);
            rvItemList.setVisibility(View.GONE);
        }
*/
    }



    @Override
    public void initListeners() {


    }

    void callViewItemApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("unit",""+PreferenceManger.getPreferenceManger().getString(PrefKeys.UNITID));
                jsonObjectPayload.put("storage",""+PreferenceManger.getPreferenceManger().getString(PrefKeys.STORAGEID));


                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new ViewItemApiCall(getContext(),this,token, Constants.VIEW_ITEM_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }




    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.VIEW_ITEM_CODE:
                hideLoading();

                if (response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);

                        if (response!=null)
                        {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                Logger.debug(TAG,""+jsonObject.toString());
                                int result=getIntFromJsonObj(jsonObject,"result");
                                String message=getStringFromJsonObj(jsonObject,"message");
                                if(result==1)
                                {
                                    ItemListBean itemListBean=new Gson().fromJson(response.toString(), ItemListBean.class);
                                    if (itemListBean!=null && itemListBean.getAuction() !=null && itemListBean.getAuction().size()>0)
                                    {

                                        auctionList=itemListBean.getAuction();
                                        adapter = new AuctionItemsListAdapter(getActivity(),itemListBean.getAuction());
                                        rvItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                        rvItemList.setAdapter(adapter);

                                        txtErrorMsg.setVisibility(View.GONE);
                                        rvItemList.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        txtErrorMsg.setVisibility(View.VISIBLE);
                                        rvItemList.setVisibility(View.GONE);
                                    }
                                }
                                else
                                {
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                    rvItemList.setVisibility(View.GONE);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                txtErrorMsg.setVisibility(View.VISIBLE);
                                rvItemList.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            txtErrorMsg.setVisibility(View.VISIBLE);
                            rvItemList.setVisibility(View.GONE);
                        }

                    }
                    else
                    {
                        txtErrorMsg.setVisibility(View.VISIBLE);
                        rvItemList.setVisibility(View.GONE);
                    }
                }
                else
                {
                    txtErrorMsg.setVisibility(View.VISIBLE);
                    rvItemList.setVisibility(View.GONE);
                }


                break;


        }

    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code)
        {
            case Constants.VIEW_ITEM_CODE:
                hideLoading();
                txtErrorMsg.setVisibility(View.VISIBLE);
                rvItemList.setVisibility(View.GONE);
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

        if(auctionList!=null && auctionList.size()>0)
        {
            adapter.getFilter().filter(query);

        }
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(auctionList!=null && auctionList.size()>0)
        {
            adapter.getFilter().filter(newText);

        }

        return false;
    }


}
