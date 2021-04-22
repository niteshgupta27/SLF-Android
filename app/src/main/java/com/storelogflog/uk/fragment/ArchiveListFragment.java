package com.storelogflog.uk.fragment;

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
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.adapter.ActiveListAdapter;
import com.storelogflog.uk.adapter.ArchiveListAdapter;
import com.storelogflog.uk.apiCall.ActiveListApiCall;
import com.storelogflog.uk.apiCall.ArchiveListApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.ArchiveListBean.ArchiveListBean;
import com.storelogflog.uk.bean.activeListBean.ActiveAuction;
import com.storelogflog.uk.bean.activeListBean.ActiveListBean;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ArchiveListFragment extends BaseFragment implements VolleyApiResponseString, SearchView.OnQueryTextListener {

    private RecyclerView rvItemList;
    private ArchiveListAdapter adapter;
    List<ArchiveListBean.Auction> activeAuctionList;
    private Fragment fragment;
    private Bundle bundle;
    private AppCompatTextView txtErrorMsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.active_item_list, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {
        rvItemList=view.findViewById(R.id.rv_item_list);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);

        ((HomeActivity)getActivity()).enableViews(false,"Auction List");

         callArchiveListApi();

    }

    @Override
    public void initListeners() {

    }

    void callArchiveListApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new ArchiveListApiCall(getContext(),this,token, Constants.ARCHIVE_LIST_CODE);
                showLoading("Loading...");

                Log.e(TAG,"auction-list-archive======>"+jsonObjectPayload.toString());

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
            case Constants.ARCHIVE_LIST_CODE:


                if(response!=null)
                {
                    hideLoading();
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            Log.e(TAG,"auction-list-archive_res======>"+response);

                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                       //     String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {
                                ArchiveListBean archiveListBean =new Gson().fromJson(response.toString(), ArchiveListBean.class);
                                if(archiveListBean!=null && archiveListBean.getAuctions()!=null && archiveListBean.getAuctions().size()>0)
                                {
                                    rvItemList.setVisibility(View.VISIBLE);
                                    txtErrorMsg.setVisibility(View.GONE);
                                    activeAuctionList=archiveListBean.getAuctions();
                                    adapter = new ArchiveListAdapter(getActivity(),archiveListBean.getAuctions());
                                    rvItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                    rvItemList.setAdapter(adapter);
                                }
                                else
                                {
                                    rvItemList.setVisibility(View.GONE);
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                rvItemList.setVisibility(View.GONE);
                                txtErrorMsg.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            rvItemList.setVisibility(View.GONE);
                            txtErrorMsg.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        rvItemList.setVisibility(View.GONE);
                        txtErrorMsg.setVisibility(View.VISIBLE);
                    }
                    break;
                }



        }

    }


    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code)
        {
            case Constants.ARCHIVE_LIST_CODE:
                hideLoading();
                rvItemList.setVisibility(View.GONE);
                txtErrorMsg.setVisibility(View.VISIBLE);
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

        if(activeAuctionList!=null && activeAuctionList.size()>0)
        {
            adapter.getFilter().filter(query);

        }
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(activeAuctionList!=null && activeAuctionList.size()>0)
        {
            adapter.getFilter().filter(newText);

        }

        return false;
    }


}
