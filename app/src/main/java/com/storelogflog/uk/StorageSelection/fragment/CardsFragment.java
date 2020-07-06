package com.storelogflog.uk.StorageSelection.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.adapter.MyCustomPagerAdapter;
import com.storelogflog.uk.StorageSelection.model.CardViewModel;
import com.storelogflog.uk.StorageSelection.model.StorageShapeModel;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.StorageListShapeApiCall;
import com.storelogflog.uk.apiCall.ViewItemApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.fragment.AddItemFragment;
import com.storelogflog.uk.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.storelogflog.uk.apputil.Utility.UNRELIABLE_INTEGER_FACTORY;

public class CardsFragment extends BaseFragment implements VolleyApiResponseString {
    String TAG = this.getClass().getSimpleName();
    View view;
    Context mContext;
    ViewPager viewPager1;
    int pagNo = 0, Card_position;
    List<CardViewModel.Item> cardlist;
    RelativeLayout rlPre, rlNext;
    ImageView img_splash;
    Bundle bundle;
    String AddedItems = "";
    ArrayList<String> Location_id_list = new ArrayList<String>();
    ArrayList<String> Shape_id_list = new ArrayList<String>();
    ArrayList<StorageShapeModel.Storage.ShapsList.RackList> Rack_list = new ArrayList<StorageShapeModel.Storage.ShapsList.RackList>();
    List<String> Rack_Value = new ArrayList<String>();
    ArrayList<String> GridCell_list = new ArrayList<String>();
    ArrayList<String> Shap_name_list2 = new ArrayList<String>();
    String RackID_position;
    List<StorageShapeModel.Storage.ShapsList> Shap_list5 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list4 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list3 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list5 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list4 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list3 = new ArrayList<>();
    ArrayList<StorageShapeModel.Storage.ShapsList> Shape_name = new ArrayList<>();
    String[] grid_cell = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "y", "Z",
            "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL", "MM", "NN", "OO",
            "PP", "QQ", "RR", "SS", "TT", "UU", "VV", "WW", "XX", "Yy", "ZZ"};
    private String numberofColumn;
    private Storage storage;
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cards, container, false);

        initViews(view);
        initListeners();


        return view;
    }


    @Override
    public void initViews(View view) {

        viewPager1 = view.findViewById(R.id.viewpager1);
        rlPre = view.findViewById(R.id.rlPre);
        rlNext = view.findViewById(R.id.rlNext);

        img_splash = view.findViewById(R.id.img_splash);


        ((HomeActivity) getActivity()).enableViews(false, "Single Door Space 2");

        if (getArguments() != null) {
            storage = (Storage) getArguments().getSerializable("storage");
            if (storage != null) {
                GetStorageShape(storage.getUnitID());

            }


        }

    }

    @Override
    public void initListeners() {
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i == 0) {
                    rlPre.setVisibility(View.GONE);
                    rlNext.setVisibility(View.VISIBLE);
                } else if (i == cardlist.size() - 1) {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.GONE);

                } else {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    rlPre.setVisibility(View.GONE);
                    rlNext.setVisibility(View.VISIBLE);
                } else if (i == cardlist.size() - 1) {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.GONE);

                } else {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        rlPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPre(false);
            }
        });

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPre(true);
            }
        });


    }

    public void NextPre(boolean check) {
        int setPos = 0;
        int pos = viewPager1.getCurrentItem();
        if (check) //next
        {
            if (pos >= (cardlist.size() - 1)) {
                setPos = 0;
            } else {
                setPos = pos + 1;
            }
        } else//previous
        {
            if (pos > 0) {
                setPos = pos - 1;
            } else {
                setPos = cardlist.size();
            }
        }


        viewPager1.setCurrentItem(setPos);
    }


    private void CardItemList(Storage storage) {


        try {
            JSONObject jsonObjectPayload = new JSONObject();
            jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
            jsonObjectPayload.put("unit_id", storage.getUnitID());
            Logger.debug(TAG, jsonObjectPayload.toString());
            String token = Utility.getJwtToken(jsonObjectPayload.toString());
            new ViewItemApiCall(mContext, this, token, Constants.VIEW_ITEM);
            //   showLoading("Login...");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void GetStorageShape(String storage_ID) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("unit_id", storage_ID);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new StorageListShapeApiCall(getActivity(), this, token, Constants.StorageListShape);

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
            case Constants.VIEW_ITEM:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Gson gson = new GsonBuilder()
                                    .registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY)
                                    .create();
                            CardViewModel cardViewModel = gson.fromJson(response.toString(), CardViewModel.class);
                            if (cardViewModel.getItems() != null) {
                                AddedItems = String.valueOf(cardViewModel.getItems().size());
                            }
                            Log.e("Card_respose", String.valueOf(response.toString()));

                            Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();
                            Location_id_list = new ArrayList<>();
                            Rack_Value = new ArrayList<>();
                            Shape_id_list = new ArrayList<>();
                            Rack_list = new ArrayList<>();
                            GridCell_list = new ArrayList<>();


                            if (cardViewModel.getItems().size() > 0) {
                                for (int i = 0; i < cardViewModel.getItems().size(); i++) {
                                    Location_id_list.add(String.valueOf(cardViewModel.getItems().get(i).getLocationId()));
                                    Rack_Value.add(String.valueOf(cardViewModel.getItems().get(i).getRackId()));

                                }

                                for (int i = 0; i < Shap_list5.size(); i++) {
                                    Shape_id_list.add(String.valueOf(Shap_list5.get(i).getShapID()));
                                    Rack_list.addAll(Shap_list5.get(i).getRackList());
                                    Shape_value_list5.add(Shap_list5.get(i).getShapValue());
                                }


                                for (int i = 0; i < grid_cell.length; i++) {
                                    GridCell_list.add(grid_cell[i]);
                                }


                                int S = 0;

                                for (int i = 0; i < GridCell_list.size(); i++) {

                                    if (i < Shape_value_list5.size()) {

                                        int value = Shape_value_list5.get(i);
                                        if (value == 0) {
                                            Shap_name_list2.add("");
                                        } else {
                                            Log.e(TAG, "value =====> " + value);
                                            Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(S));

                                            Shap_name_list2.add(GridCell_list.get(S));
                                            S++;

                                        }
                                    }
                                }


                                for (int i = 0; i < Shap_name_list2.size(); i++) {


                                    if (i < Shape_id_list.size()) {

                                        for (int k = 0; k < Location_id_list.size(); k++) {

                                            for (int j = 0; j < Shape_id_list.size(); j++) {

                                                if (String.valueOf(Shape_id_list.get(j)).equals(String.valueOf(cardViewModel.getItems().get(k).getLocationId()))) {

                                                    if (Shap_list5.get(j).getRackList().size() > 0) {

                                                        for (int l = 0; l < Shap_list5.get(j).getRackList().size(); l++) {

                                                            for (int M = 0; M < cardViewModel.getItems().size(); M++) {

                                                                if (String.valueOf(Shap_list5.get(j).getRackList().get(l).getFldRacksId()).equals(String.valueOf(cardViewModel.getItems().get(M).getRackId()))) {
                                                                    RackID_position = String.valueOf(l);
                                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                                    selectedGridModel.setShape_name(Shap_name_list2.get(j) + " - " + RackID_position);
                                                                    selectedGridModel.setRackID_position(RackID_position);
                                                                    selectedGridModel.setShape_id2(String.valueOf(Shape_id_list.get(j)));
                                                                    Shape_name.add(selectedGridModel);

                                                                    Log.e(TAG, "Card_Item====>" + Shap_name_list2.get(j) + " - " + RackID_position);

                                                                }
                                                            }

                                                        }


                                                    } else {
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(Shap_name_list2.get(j));
                                                        selectedGridModel.setRackID_position("");
                                                        selectedGridModel.setShape_id2(String.valueOf(Shape_id_list.get(j)));
                                                        Shape_name.add(selectedGridModel);

                                                        Log.e(TAG, "Card_Item====>" + Shap_name_list2.get(j));

                                                    }

                                                }
                                            }
                                        }


                                    }
                                }
                                img_splash.setVisibility(View.GONE);

                                cardlist = cardViewModel.getItems();
                                MyCustomPagerAdapter customPagerAdapter = new MyCustomPagerAdapter(getActivity(), cardlist, Shape_name, storage);
                                viewPager1.setAdapter(customPagerAdapter);

                                if (!String.valueOf(getArguments().getString("position")).equals("null")) {
                                    Card_position = Integer.parseInt(getArguments().getString("position"));
                                    Log.e("Card_position", String.valueOf(Card_position));
                                    viewPager1.setCurrentItem(Card_position);

                                } else {
                                    viewPager1.setCurrentItem(pagNo);
                                }


                            } else {
                                img_splash.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragment = new AddItemFragment();
                                        bundle = new Bundle();
                                        bundle.putSerializable("storage", storage);
                                        bundle.putString("AddedItems", AddedItems);
                                        fragment.setArguments(bundle);
                                        Common.loadFragment(getActivity(), fragment, false, null);
                                    }
                                }, 1000);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error===>", e.toString());
                        }
                    }
                }
                break;

            case Constants.StorageListShape:
                //  hideLoading();
                CardItemList(storage);
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());

                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                StorageShapeModel storageShapeModel = new Gson().fromJson(response.toString(), StorageShapeModel.class);

                                if (storageShapeModel.getStorage().getShapsList().size() > 0) {

                                    Shap_list5 = storageShapeModel.getStorage().getShapsList();


                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

    }


}

