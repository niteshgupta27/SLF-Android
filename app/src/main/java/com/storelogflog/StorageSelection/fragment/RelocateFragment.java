package com.storelogflog.StorageSelection.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.model.StorageShapeModel;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.activity.PaymentActivity;
import com.storelogflog.apiCall.PricingApiCall;
import com.storelogflog.apiCall.RelocateItemApiCall;
import com.storelogflog.apiCall.StorageListShapeApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.ExpandableHeightGridView;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.fragment.AddItemFragment;
import com.storelogflog.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.storelogflog.apputil.Common.getIntFromJsonObj;


public class RelocateFragment extends BaseFragment implements VolleyApiResponseString {
    private final String TAG = RelocateFragment.this.getClass().toString();
    Context mContext;
    View view;
    ExpandableHeightGridView Grid_View5, Grid_View4, Grid_View3;
    LinearLayout door_1, door_2, door_3, door_4, door_5,
            linear_door_1, linear_door_2, linear_door_3, linear_door_4, linear_door_5,
            door_11, door_12, door_13, door_14,
            linear_door_11, linear_door_12, linear_door_13, linear_door_14,
            door_21, door_22, door_23,
            linear_door_21, linear_door_22, linear_door_23;
    ;
    ImageView door_img1, door_img2, door_img3, door_img4, door_img5,
            door_img11, door_img12, door_img13, door_img14, door_img21, door_img22, door_img23;

    LinearLayout Grid5_linear, Grid4_linear, Grid3_linear;
    GridCellAdapter gridCellAdapter;
    int prevSelection = -1;
    String SelectedGridShapeID, SelectedRack_ID, Selectedshape_name = "", ItemId = "";
    ArrayList<StorageShapeModel.Storage.ShapsList> Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();
    String[] grid_cell = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "y", "Z",
            "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL", "MM", "NN", "OO",
            "PP", "QQ", "RR", "SS", "TT", "UU", "VV", "WW", "XX", "Yy", "ZZ"};
    TextView proceed_btn;
    int pondValue;
    Storage storage;
    AlertDialog alertDialog;
    GridCellAdapter.CustomRacklistAdapter customracklistadapter;
    int prevSelection_rack = -1;
    String Selection_rack_no = "";
    ArrayList<String> GridCell_list = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list5 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list4 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list3 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list5 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list4 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list3 = new ArrayList<>();
    private Storage storage1;
    private String numberofColumn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relocate, container, false);

        initViews(view);

        return view;
    }


    @Override
    public void initViews(View view) {

        Grid_View5 = view.findViewById(R.id.Grid_View5);
        Grid_View4 = view.findViewById(R.id.Grid_View4);
        Grid_View3 = view.findViewById(R.id.Grid_View3);

        Grid5_linear = view.findViewById(R.id.Grid5_linear);
        Grid4_linear = view.findViewById(R.id.Grid4_linear);
        Grid3_linear = view.findViewById(R.id.Grid3_linear);


        door_1 = view.findViewById(R.id.door_1);
        door_2 = view.findViewById(R.id.door_2);
        door_3 = view.findViewById(R.id.door_3);
        door_4 = view.findViewById(R.id.door_4);
        door_5 = view.findViewById(R.id.door_5);

        linear_door_1 = view.findViewById(R.id.linear_door_1);
        linear_door_2 = view.findViewById(R.id.linear_door_2);
        linear_door_3 = view.findViewById(R.id.linear_door_3);
        linear_door_4 = view.findViewById(R.id.linear_door_4);
        linear_door_5 = view.findViewById(R.id.linear_door_5);

        door_img1 = view.findViewById(R.id.door_img1);
        door_img2 = view.findViewById(R.id.door_img2);
        door_img3 = view.findViewById(R.id.door_img3);
        door_img4 = view.findViewById(R.id.door_img4);
        door_img5 = view.findViewById(R.id.door_img5);


        door_11 = view.findViewById(R.id.door_11);
        door_12 = view.findViewById(R.id.door_12);
        door_13 = view.findViewById(R.id.door_13);
        door_14 = view.findViewById(R.id.door_14);
        linear_door_11 = view.findViewById(R.id.linear_door_11);
        linear_door_12 = view.findViewById(R.id.linear_door_12);
        linear_door_13 = view.findViewById(R.id.linear_door_13);
        linear_door_14 = view.findViewById(R.id.linear_door_14);
        door_img11 = view.findViewById(R.id.door_img11);
        door_img12 = view.findViewById(R.id.door_img12);
        door_img13 = view.findViewById(R.id.door_img13);
        door_img14 = view.findViewById(R.id.door_img14);


        door_21 = view.findViewById(R.id.door_21);
        door_22 = view.findViewById(R.id.door_22);
        door_23 = view.findViewById(R.id.door_23);

        linear_door_21 = view.findViewById(R.id.linear_door_21);
        linear_door_22 = view.findViewById(R.id.linear_door_22);
        linear_door_23 = view.findViewById(R.id.linear_door_23);

        door_img21 = view.findViewById(R.id.door_img21);
        door_img22 = view.findViewById(R.id.door_img22);
        door_img23 = view.findViewById(R.id.door_img23);


        proceed_btn = view.findViewById(R.id.proceed_btn);

        ((HomeActivity) getActivity()).enableViews(true, "Relocate Item");

        if (getArguments() != null) {
            storage = (Storage) getArguments().getSerializable("storage");
            if (storage != null) {
                GetStorageShape(storage.getUnitID());
            }

            if (!String.valueOf(getArguments().getString(Constants.ItemID)).equals("null")) {
                ItemId = String.valueOf(getArguments().getString(Constants.ItemID));
                Log.e("ItemId", ItemId);
            }

        }

        proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SelectedGridShapeID)) {
                    showToast(mContext, "Please select shelves location First!");
                }
                callRelocateItem();
            }
        });

    }

    private void callRelocateItem() {
        if (Utility.isInternetConnected(getActivity())) {
            try {

                if (storage != null) {
                    JSONObject jsonObjectPayload = new JSONObject();
                    jsonObjectPayload.put("storage_id", "" + storage.getID());
                    jsonObjectPayload.put("unit_id", storage.getUnitID());
                    jsonObjectPayload.put("location_id", SelectedGridShapeID);
                    jsonObjectPayload.put("rack_id", SelectedRack_ID);
                    jsonObjectPayload.put("item_id", ItemId);
                    jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));

                    Logger.debug(TAG, jsonObjectPayload.toString());
                    String token = Utility.getJwtToken(jsonObjectPayload.toString());
                    showLoading("Loading...");
                    new RelocateItemApiCall(getActivity(), this, token, Constants.Relocate_Item);

                }
            } catch (Exception e) {
                Log.e("Error=====>", e.toString());
            }

        }
    }

    @Override
    public void initListeners() {


    }

    void GetStorageShape(String unitID) {
        if (Utility.isInternetConnected(mContext)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("unit_id", unitID);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new StorageListShapeApiCall(getActivity(), this, token, Constants.StorageListShape);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast(mContext, "No Internet Connection");
        }

    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {
        switch (code) {
            case Constants.StorageListShape:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                StorageShapeModel storageShapeModel = new Gson().fromJson(response.toString(), StorageShapeModel.class);
    
    
                                if (storageShapeModel.getStorage().getShapsList().size() > 0) {
                                    Shape_name = new ArrayList<>();
                                    Shap_list5 = new ArrayList<>();
                                    Shape_value_list5 = new ArrayList<>();
                                    Shap_list4 = new ArrayList<>();
                                    Shape_value_list4 = new ArrayList<>();
                                    Shap_list3 = new ArrayList<>();
                                    Shape_value_list3 = new ArrayList<>();
                                    GridCell_list = new ArrayList<>();
        
        
                                    for (int i = 0; i < grid_cell.length; i++) {
                                        GridCell_list.add(grid_cell[i]);
                                    }
        
                                    Shap_list5 = storageShapeModel.getStorage().getShapsList();
        
        
                                    for (int i = 0; i < Shap_list5.size(); i++) {
                                        Shape_value_list5.add(Shap_list5.get(i).getShapValue());
                                    }
        
        
                                    Log.e(TAG, "Shape_value_list5 =====> " + Shape_value_list5.size());
                                    for (int i = 0; i < Shape_value_list5.size(); i++) {
            
                                        if (i <= 5) {
                                            if (i == 5) {
                                                if (String.valueOf(Shape_value_list5.get(5)).equals("1")) {
                                                    numberofColumn = "5";
                                                }
                                            } else if (i == 4) {
                                                if (String.valueOf(Shape_value_list5.get(4)).equals("1")) {
                                                    numberofColumn = "4";
                                                }
                                            } else if (i == 3) {
                                                if (String.valueOf(Shape_value_list5.get(3)).equals("1")) {
                                                    numberofColumn = "3";
                                                }
                                            }
                                        }
                                        else if (i <= 10) {
                                            if (i == 10) {
                                                if (String.valueOf(Shape_value_list5.get(10)).equals(
                                                        "1")) {
                                                    numberofColumn = "5";
                                                } else if (i == 9) {
                        
                                                    if (String.valueOf(Shape_value_list5.get(9)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 8) {
                        
                                                    if (String.valueOf(Shape_value_list5.get(8)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }
                                            }
                                        }
                                        else if (i <= 15) {
                
                                            if (i == 15) {
                                                if (String.valueOf(Shape_value_list5.get(15)).equals("1")) {
                                                    numberofColumn = "5";
                                                }
                                            } else if (i == 14) {
                    
                                                if (String.valueOf(Shape_value_list5.get(14)).equals("1")) {
                                                    numberofColumn = "4";
                                                }
                                            } else if (i == 13) {
                    
                                                if (String.valueOf(Shape_value_list5.get(13)).equals("1")) {
                                                    numberofColumn = "3";
                                                }
                                            }
                                        }
                                        else if (i <= 20) {
                
                                            if (i == 20) {
                                                if (String.valueOf(Shape_value_list5.get(20)).equals("1")) {
                                                    numberofColumn = "5";
                                                } else if (i == 19) {
                                                    if (String.valueOf(Shape_value_list5.get(19)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 18) {
                                                    if (String.valueOf(Shape_value_list5.get(18)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }
                                            }
                                        }
                                        else if (i <= 25) {
                
                                            if (i == 25) {
                                                if (String.valueOf(Shape_value_list5.get(25)).equals("1")) {
                                                    numberofColumn = "5";
                                                } else if (i == 24) {
                                                    if (String.valueOf(Shape_value_list5.get(24)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 23) {
                                                    if (String.valueOf(Shape_value_list5.get(23)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }
                                            }
                
                                        }
                                        else if (i <= 30) {
                                            if (i == 30) {
                                                if (String.valueOf(Shape_value_list5.get(30)).equals("1")) {
                                                    numberofColumn = "5";
                                                } else if (i == 29) {
                                                    if (String.valueOf(Shape_value_list5.get(29)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 28) {
                                                    if (String.valueOf(Shape_value_list5.get(28)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }
                                            }
                                        }
                                        else if (i <= 35) {
                                            if (i == 35) {
                                                if (String.valueOf(Shape_value_list5.get(35)).equals("1")) {
                                                    numberofColumn = "5";
                                                }
                                            } else if (i == 34) {
                                                if (String.valueOf(Shape_value_list5.get(34)).equals("1")) {
                                                    numberofColumn = "4";
                                                }
                                            } else if (i == 33) {
                                                if (String.valueOf(Shape_value_list5.get(33)).equals("1")) {
                                                    numberofColumn = "3";
                                                }
                                            }
                
                
                                        }
                                    }
        
        
                                    Log.e(TAG, "numberofColumn==========>" + numberofColumn);
        
        
                                    if (numberofColumn.equals("4")) {
                                        for (int l = 0; l < Shap_list5.size(); l++) {
                
                                            if (l == 5 || l == 10 || l == 15 || l == 20 || l == 25 || l == 30 || l == 35) {
                    
                                            } else {
                                                StorageShapeModel.Storage.ShapsList shapsList = new StorageShapeModel.Storage.ShapsList();
                                                shapsList.setShapID(Shap_list5.get(l).getShapID());
                                                shapsList.setShapValue(Shap_list5.get(l).getShapValue());
                                                shapsList.setRackStatus(Shap_list5.get(l).getRackStatus());
                                                shapsList.setRackList(Shap_list5.get(l).getRackList());
                                                Shap_list4.add(shapsList);
                                            }
                                        }
                                        for (int i = 0; i < Shap_list4.size(); i++) {
                                            Shape_value_list4.add(Shap_list4.get(i).getShapValue());
                                        }
                                    }
        
                                    if (numberofColumn.equals("3")) {
                                        for (int l = 0; l < Shap_list5.size(); l++) {
                
                                            if (l == 5 || l == 10 || l == 15 || l == 20 || l == 25 || l == 30 || l == 35 ||
                                                    l == 4 || l == 9 || l == 14 || l == 19 || l == 24 || l == 29 || l == 34) {
                    
                                            } else {
                                                StorageShapeModel.Storage.ShapsList shapsList = new StorageShapeModel.Storage.ShapsList();
                                                shapsList.setShapID(Shap_list5.get(l).getShapID());
                                                shapsList.setShapValue(Shap_list5.get(l).getShapValue());
                                                shapsList.setRackStatus(Shap_list5.get(l).getRackStatus());
                                                shapsList.setRackList(Shap_list5.get(l).getRackList());
                                                Shap_list3.add(shapsList);
                                            }
                                        }
                                        for (int i = 0; i < Shap_list3.size(); i++) {
                                            Shape_value_list3.add(Shap_list3.get(i).getShapValue());
                                        }
                                    }
        
        
        
                                    if (storageShapeModel.getStorage().getDoorType().equals("5")) {
                                        String door = String.valueOf(storageShapeModel.getStorage().getDoors());
                                        List<String> myList = new ArrayList<String>(Arrays.asList(door.split(",")));
            
            
                                        if (numberofColumn.equals("5")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list5.size()) {
                        
                                                    int value = Shape_value_list5.get(i);
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                                                        Log.e("door_array", myList.get(j));
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition(myList.get(j));
                            
                                                        Shape_name.add(selectedGridModel);
                            
                                                        Log.e("door_array", myList.get(j));
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        } else if (numberofColumn.equals("4")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list4.size()) {
                        
                                                    int value = Shape_value_list4.get(i);
                        
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition(myList.get(j));
                                                        Shape_name.add(selectedGridModel);
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        }
                                        else if (numberofColumn.equals("3")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list3.size()) {
                        
                                                    int value = Shape_value_list3.get(i);
                        
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                                                        Log.e("door_array", myList.get(j));
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition(myList.get(j));
                                                        Shape_name.add(selectedGridModel);
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        }
                                    }else {
                                        if (numberofColumn.equals("5")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list5.size()) {
                        
                                                    int value = Shape_value_list5.get(i);
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                            
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition("");
                            
                                                        Shape_name.add(selectedGridModel);
                            
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        } else if (numberofColumn.equals("4")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list4.size()) {
                        
                                                    int value = Shape_value_list4.get(i);
                        
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                            
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition("");
                            
                                                        Shape_name.add(selectedGridModel);
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        } else if (numberofColumn.equals("3")) {
                                            int j = 0;
                                            for (int i = 0; i < GridCell_list.size(); i++) {
                    
                                                if (i < Shape_value_list3.size()) {
                        
                                                    int value = Shape_value_list3.get(i);
                        
                                                    if (value == 0) {
                                                        Log.e(TAG, "value =====> " + value);
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name("");
                                                        selectedGridModel.setDoorPosition("");
                                                        Shape_name.add(selectedGridModel);
                            
                                                    } else {
                                                        Log.e(TAG, "value =====> " + value);
                                                        Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));
                            
                            
                                                        StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                        selectedGridModel.setShape_name(GridCell_list.get(j));
                                                        selectedGridModel.setDoorPosition("");
                            
                                                        Shape_name.add(selectedGridModel);
                            
                                                        j++;
                            
                                                    }
                                                }
                                            }
                                        }
            
                                    }
        
        
                                }
    
                                if (numberofColumn.equals("5")) {
                                    Grid3_linear.setVisibility(View.GONE);
                                    Grid4_linear.setVisibility(View.GONE);
                                    Grid5_linear.setVisibility(View.VISIBLE);
                                    gridCellAdapter = new GridCellAdapter(mContext, storageShapeModel.getStorage().getShapsList(), Shape_name);
                                    Grid_View5.setExpanded(true);
                                    Grid_View5.setNumColumns(5);
                                    Grid_View5.setAdapter(gridCellAdapter);
                                    gridCellAdapter.notifyDataSetChanged();

                                } else if (numberofColumn.equals("4")) {
                                    Grid3_linear.setVisibility(View.GONE);
                                    Grid4_linear.setVisibility(View.VISIBLE);
                                    Grid5_linear.setVisibility(View.GONE);

                                    gridCellAdapter = new GridCellAdapter(mContext, Shap_list4, Shape_name);
                                    Grid_View4.setExpanded(true);
                                    Grid_View4.setNumColumns(4);
                                    Grid_View4.setAdapter(gridCellAdapter);
                                    gridCellAdapter.notifyDataSetChanged();

                                } else if (numberofColumn.equals("3")) {
                                    Grid3_linear.setVisibility(View.VISIBLE);
                                    Grid4_linear.setVisibility(View.GONE);
                                    Grid5_linear.setVisibility(View.GONE);

                                    gridCellAdapter = new GridCellAdapter(mContext, Shap_list3, Shape_name);
                                    Grid_View3.setExpanded(true);
                                    Grid_View3.setNumColumns(3);
                                    Grid_View3.setAdapter(gridCellAdapter);
                                    gridCellAdapter.notifyDataSetChanged();

                                }

                                if (numberofColumn.equals("5")) {
                                    if (storageShapeModel.getStorage().getDoorType().equals("1")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.doorleft);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img2.setImageResource(R.drawable.doorleft);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img3.setImageResource(R.drawable.doorleft);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img4.setImageResource(R.drawable.doorleft);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img5.setImageResource(R.drawable.doorleft);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_blue_left);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_blue_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img3.setImageResource(R.drawable.door_blue_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img4.setImageResource(R.drawable.door_blue_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img5.setImageResource(R.drawable.door_blue_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_left_red);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_left_red);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img3.setImageResource(R.drawable.door_left_red);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img4.setImageResource(R.drawable.door_left_red);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img5.setImageResource(R.drawable.door_left_red);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_green_left);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_green_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img3.setImageResource(R.drawable.door_green_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img4.setImageResource(R.drawable.door_green_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img5.setImageResource(R.drawable.door_green_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_yellow_left);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_yellow_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img3.setImageResource(R.drawable.door_yellow_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img4.setImageResource(R.drawable.door_yellow_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img5.setImageResource(R.drawable.door_yellow_left);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.doorleft);
                                                    door_img2.setImageResource(R.drawable.doorright);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img2.setImageResource(R.drawable.doorleft);
                                                    door_img3.setImageResource(R.drawable.doorright);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img3.setImageResource(R.drawable.doorleft);
                                                    door_img4.setImageResource(R.drawable.doorright);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img4.setImageResource(R.drawable.doorleft);
                                                    door_img5.setImageResource(R.drawable.doorright);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_blue_left);
                                                    door_img2.setImageResource(R.drawable.door_blue_right);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_blue_left);
                                                    door_img3.setImageResource(R.drawable.door_blue_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img3.setImageResource(R.drawable.door_blue_left);
                                                    door_img4.setImageResource(R.drawable.door_blue_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img4.setImageResource(R.drawable.door_blue_left);
                                                    door_img5.setImageResource(R.drawable.door_blue_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_left_red);
                                                    door_img2.setImageResource(R.drawable.door_red_right);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_left_red);
                                                    door_img3.setImageResource(R.drawable.door_red_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img3.setImageResource(R.drawable.door_left_red);
                                                    door_img4.setImageResource(R.drawable.door_red_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img4.setImageResource(R.drawable.door_left_red);
                                                    door_img5.setImageResource(R.drawable.door_red_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }

                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_green_left);
                                                    door_img2.setImageResource(R.drawable.door_green_right);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_green_left);
                                                    door_img3.setImageResource(R.drawable.door_green_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img3.setImageResource(R.drawable.door_green_left);
                                                    door_img4.setImageResource(R.drawable.door_green_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img4.setImageResource(R.drawable.door_green_left);
                                                    door_img5.setImageResource(R.drawable.door_green_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }

                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img1.setImageResource(R.drawable.door_yellow_left);
                                                    door_img2.setImageResource(R.drawable.door_yellow_right);
                                                    door_img1.setVisibility(View.VISIBLE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img2.setImageResource(R.drawable.door_yellow_left);
                                                    door_img3.setImageResource(R.drawable.door_yellow_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.VISIBLE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.GONE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img3.setImageResource(R.drawable.door_yellow_left);
                                                    door_img4.setImageResource(R.drawable.door_yellow_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.VISIBLE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img4.setImageResource(R.drawable.door_yellow_left);
                                                    door_img5.setImageResource(R.drawable.door_yellow_right);
                                                    door_img1.setVisibility(View.GONE);
                                                    door_img2.setVisibility(View.GONE);
                                                    door_img3.setVisibility(View.GONE);
                                                    door_img4.setVisibility(View.VISIBLE);
                                                    door_img5.setVisibility(View.VISIBLE);
                                                }

                                            }
                                        }

                                    } else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.white_door);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.blue_door);
                                                }

                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.red_door);
                                                }

                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.green_door);
                                                }

                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.yellow_door);
                                                }

                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.white_door);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.blue_door);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.red_door);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.green_door);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_2.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_3.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_4.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.dash_img);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_1.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_2.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_3.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_4.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_5.setBackgroundResource(R.drawable.yellow_door);
                                                }
                                            }
                                        }
                                    }

                                }
                                else if (numberofColumn.equals("4")) {
                                    if (storageShapeModel.getStorage().getDoorType().equals("1")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.doorleft);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img12.setImageResource(R.drawable.doorleft);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img13.setImageResource(R.drawable.doorleft);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img14.setImageResource(R.drawable.doorleft);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img14.setImageResource(R.drawable.doorleft);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_blue_left);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_blue_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img13.setImageResource(R.drawable.door_blue_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img14.setImageResource(R.drawable.door_blue_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img14.setImageResource(R.drawable.door_blue_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_left_red);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_left_red);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img13.setImageResource(R.drawable.door_left_red);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img14.setImageResource(R.drawable.door_left_red);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img14.setImageResource(R.drawable.door_left_red);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_green_left);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_green_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img13.setImageResource(R.drawable.door_green_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img14.setImageResource(R.drawable.door_green_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img14.setImageResource(R.drawable.door_green_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_yellow_left);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_yellow_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img13.setImageResource(R.drawable.door_yellow_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    door_img14.setImageResource(R.drawable.door_yellow_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    door_img14.setImageResource(R.drawable.door_yellow_left);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.doorleft);
                                                    door_img12.setImageResource(R.drawable.doorright);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img12.setImageResource(R.drawable.doorleft);
                                                    door_img13.setImageResource(R.drawable.doorright);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img13.setImageResource(R.drawable.doorleft);
                                                    door_img14.setImageResource(R.drawable.doorright);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img13.setImageResource(R.drawable.doorleft);
                                                    door_img14.setImageResource(R.drawable.doorright);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_blue_left);
                                                    door_img12.setImageResource(R.drawable.door_blue_right);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_blue_left);
                                                    door_img13.setImageResource(R.drawable.door_blue_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img13.setImageResource(R.drawable.door_blue_left);
                                                    door_img14.setImageResource(R.drawable.door_blue_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img13.setImageResource(R.drawable.door_blue_left);
                                                    door_img14.setImageResource(R.drawable.door_blue_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_left_red);
                                                    door_img12.setImageResource(R.drawable.door_red_right);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_left_red);
                                                    door_img13.setImageResource(R.drawable.door_red_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img13.setImageResource(R.drawable.door_left_red);
                                                    door_img14.setImageResource(R.drawable.door_red_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img13.setImageResource(R.drawable.door_left_red);
                                                    door_img14.setImageResource(R.drawable.door_red_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_green_left);
                                                    door_img12.setImageResource(R.drawable.door_green_right);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_green_left);
                                                    door_img13.setImageResource(R.drawable.door_green_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img13.setImageResource(R.drawable.door_green_left);
                                                    door_img14.setImageResource(R.drawable.door_green_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img13.setImageResource(R.drawable.door_green_left);
                                                    door_img14.setImageResource(R.drawable.door_green_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img11.setImageResource(R.drawable.door_yellow_left);
                                                    door_img12.setImageResource(R.drawable.door_yellow_right);
                                                    door_img11.setVisibility(View.VISIBLE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.GONE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img12.setImageResource(R.drawable.door_yellow_left);
                                                    door_img13.setImageResource(R.drawable.door_yellow_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.VISIBLE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    door_img13.setImageResource(R.drawable.door_yellow_left);
                                                    door_img14.setImageResource(R.drawable.door_yellow_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    door_img13.setImageResource(R.drawable.door_yellow_left);
                                                    door_img14.setImageResource(R.drawable.door_yellow_right);
                                                    door_img11.setVisibility(View.GONE);
                                                    door_img12.setVisibility(View.GONE);
                                                    door_img13.setVisibility(View.VISIBLE);
                                                    door_img14.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }

                                    } else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.white_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.white_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.blue_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.blue_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.red_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.red_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.green_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.green_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.yellow_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.yellow_door);

                                                }

                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.white_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.white_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.blue_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.blue_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.red_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.red_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.green_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.green_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_12.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_13.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,1,0")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.yellow_door);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,1")) {
                                                    linear_door_11.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_12.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_13.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_14.setBackgroundResource(R.drawable.yellow_door);

                                                }
                                            }
                                        }
                                    }

                                }
                                else if (numberofColumn.equals("3")) {
                                    if (storageShapeModel.getStorage().getDoorType().equals("1")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.doorleft);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img22.setImageResource(R.drawable.doorleft);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);


                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img23.setImageResource(R.drawable.doorleft);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_blue_left);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_blue_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img23.setImageResource(R.drawable.door_blue_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_left_red);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_left_red);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img23.setImageResource(R.drawable.door_left_red);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.VISIBLE);
                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_green_left);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_green_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);


                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img23.setImageResource(R.drawable.door_green_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_yellow_left);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.GONE);
                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_yellow_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    door_img23.setImageResource(R.drawable.door_yellow_left);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.GONE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.doorleft);
                                                    door_img22.setImageResource(R.drawable.doorright);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img22.setImageResource(R.drawable.doorleft);
                                                    door_img23.setImageResource(R.drawable.doorright);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.VISIBLE);


                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_blue_left);
                                                    door_img22.setImageResource(R.drawable.door_blue_right);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_blue_left);
                                                    door_img23.setImageResource(R.drawable.door_blue_right);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_left_red);
                                                    door_img22.setImageResource(R.drawable.door_red_right);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_left_red);
                                                    door_img23.setImageResource(R.drawable.door_red_right);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_green_left);
                                                    door_img22.setImageResource(R.drawable.door_green_right);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_green_left);
                                                    door_img23.setImageResource(R.drawable.door_green_right);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    door_img21.setImageResource(R.drawable.door_yellow_left);
                                                    door_img22.setImageResource(R.drawable.door_yellow_right);
                                                    door_img21.setVisibility(View.VISIBLE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.GONE);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    door_img22.setImageResource(R.drawable.door_yellow_left);
                                                    door_img23.setImageResource(R.drawable.door_yellow_right);
                                                    door_img21.setVisibility(View.GONE);
                                                    door_img22.setVisibility(View.VISIBLE);
                                                    door_img23.setVisibility(View.VISIBLE);

                                                }
                                            }
                                        }

                                    } else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.white_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.blue_door);


                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.red_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);


                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.green_door);


                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_23.setBackgroundResource(R.drawable.yellow_door);

                                                }

                                            }
                                        }
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
                                        if (storageShapeModel.getStorage().getDoorColor() != null) {
                                            if (storageShapeModel.getStorage().getDoorColor().equals("1")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.white_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.white_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("2")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);


                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.blue_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.blue_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("3")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.red_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.red_door);


                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("4")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.green_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.green_door);

                                                }
                                            } else if (storageShapeModel.getStorage().getDoorColor().equals("5")) {
                                                if (storageShapeModel.getStorage().getDoors().equals("1,1,0,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_22.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.dash_img);

                                                } else if (storageShapeModel.getStorage().getDoors().equals("0,1,1,0,0")) {
                                                    linear_door_21.setBackgroundResource(R.drawable.dash_img);
                                                    linear_door_22.setBackgroundResource(R.drawable.yellow_door);
                                                    linear_door_23.setBackgroundResource(R.drawable.yellow_door);

                                                }
                                            }
                                        }
                                    }

                                }


                            }
                        } catch (Exception e) {
                            Log.e("Error======>", e.toString());
                        }
                    }
                }
                break;

            case Constants.Relocate_Item:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "Relocate_Item_Response===>" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String success = getStringFromJsonObj(jsonObject, "Success");
                            if (result == 1) {
                                if (success.equals("false")) {
                                    CheckStatus(storage);
                                } else {
                                    Fragment fragment = new CardsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("storage", storage);
                                    fragment.setArguments(bundle);
                                    Common.loadFragment(getActivity(), fragment, false, null);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error=====>", e.toString());
                        }
                    }
                }

                break;
            case Constants.PRICING_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                pondValue = getIntFromJsonObj(jsonObject, "NewStorage");
                                Log.e("pondValue", String.valueOf(pondValue));


                                mContext.startActivity(new Intent(mContext, PaymentActivity.class).
                                        putExtra("amount", String.valueOf(pondValue)).
                                        putExtra("storage", storage1).
                                        putExtra("FROM", "StorageRenew"));

                            }


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

        switch (code) {
            case Constants.StorageListShape:
                hideLoading();

                break;

            case Constants.Relocate_Item:
                hideLoading();

                break;

            case Constants.PRICING_CODE:
                hideLoading();

                break;

        }
    }

    private void CheckStatus(Storage storage) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cam_permission,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        TextView facebook_txt = layout.findViewById(R.id.facebook_txt);
        TextView allow_txt = layout.findViewById(R.id.allow_txt);
        TextView Cancle = layout.findViewById(R.id.btn_cancle);
        TextView btn_Ok = layout.findViewById(R.id.btn_OK);

        Cancle.setText(mContext.getResources().getString(R.string.Cancel));
        btn_Ok.setText(mContext.getResources().getString(R.string.continued));
        facebook_txt.setText(mContext.getResources().getString(R.string.app_name));
        allow_txt.setText(mContext.getResources().getString(R.string.subscription_expiered));

        storage1 = storage;
        alertDialog.show();

        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                callPricingApi();
            }
        });
    }

    void callPricingApi() {
        if (Utility.isInternetConnected(mContext)) {
            try {
                new PricingApiCall(mContext, this, null, Constants.PRICING_CODE);
                showLoading("Loading...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public class GridCellAdapter extends BaseAdapter {

        private final String TAG = GridCellAdapter.class.getSimpleName();
        Context mContext;
        List<StorageShapeModel.Storage.ShapsList> playersArrayList;
        ArrayList<StorageShapeModel.Storage.ShapsList> grid_cell_name;
        LayoutInflater inflater;
        String teamType;
        private ViewHolder holder;


        public GridCellAdapter(Context context, List<StorageShapeModel.Storage.ShapsList> arrayList, ArrayList<StorageShapeModel.Storage.ShapsList> grid_cell) {
            mContext = context;
            playersArrayList = arrayList;
            this.grid_cell_name = grid_cell;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return playersArrayList.size();
        }

        public Object getItem(int position) {
            return playersArrayList.get(position);
        }

        public long getItemId(int position) {
            return playersArrayList.indexOf(getItem(position));
        }


        public View getView(final int position, View convertView, final ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_cell_item2, null);
                holder = new ViewHolder();

                holder.item_layout = convertView.findViewById(R.id.garage_relative);
                holder.info_text = convertView.findViewById(R.id.info_text);
                holder.rack_list_avalablity = convertView.findViewById(R.id.rack_list_avalablity);
                holder.door_img = convertView.findViewById(R.id.door_img);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (playersArrayList.get(position).isChecked()) {
                holder.item_layout.setBackgroundResource(R.drawable.green_back);
            } else {
                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    holder.item_layout.setBackgroundResource(R.drawable.deactive_character);
                } else {
                    holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                }
            }

            if (grid_cell_name.get(position).getShape_name().equals(Selectedshape_name)) {

                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()) + "-" + String.valueOf(Selection_rack_no));
                    if (grid_cell_name.get(position).getDoorPosition().equals("1")) {
                        holder.door_img.setVisibility(View.VISIBLE);
                        holder.info_text.setVisibility(View.GONE);
                    } else {
                        holder.door_img.setVisibility(View.GONE);
                        holder.info_text.setVisibility(View.VISIBLE);
                    }

                } else {
                    holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                }


            } else {

                holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()));
                if (grid_cell_name.get(position).getDoorPosition().equals("1")) {
                    holder.door_img.setVisibility(View.VISIBLE);
                    holder.info_text.setVisibility(View.GONE);
                } else {
                    holder.door_img.setVisibility(View.GONE);
                    holder.info_text.setVisibility(View.VISIBLE);
                }


            }
            if (playersArrayList.get(position).getRackList() != null) {

                if (playersArrayList.get(position).getRackList().size() > 0) {
                    holder.rack_list_avalablity.setVisibility(View.VISIBLE);
                } else {
                    holder.rack_list_avalablity.setVisibility(View.GONE);
                }
            } else {
                holder.rack_list_avalablity.setVisibility(View.GONE);
            }

            //finalConvertView = convertView;
            holder.item_layout.setOnClickListener(new MainItemClick(position, holder));
            return convertView;
        }

        private void ShowRack_dialog(int position, ViewHolder holder) {
            Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogue_select_shelft);
            dialog.getWindow().setBackgroundDrawableResource(R.color.black);

            ListView Rack_list = dialog.findViewById(R.id.Rack_list);
            LinearLayout toolbar_back = dialog.findViewById(R.id.toolbar_back);
            TextView proceed_btn = dialog.findViewById(R.id.proceed_btn);
            List<StorageShapeModel.Storage.ShapsList.RackList> rack_list = new ArrayList();
            rack_list = playersArrayList.get(position).getRackList();

            ArrayList<Integer> Sr_no_list = new ArrayList<>();

            int Index = 0;
            for (int j = 0; j < rack_list.size(); j++) {

                if (Index == 0) {
                    Index = rack_list.size() - 1;

                    Log.e("Index11", String.valueOf(Index));
                    Sr_no_list.add(Index);

                } else {

                    Index = Index - 1;
                    Log.e("Index12", String.valueOf(Index));
                    Sr_no_list.add(Index);

                }

            }


            Collections.reverse(rack_list);

            customracklistadapter = new CustomRacklistAdapter(mContext, rack_list, Sr_no_list);
            Rack_list.setAdapter(customracklistadapter);

            dialog.show();

            toolbar_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            proceed_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Selectedshape_name = grid_cell_name.get(position).getShape_name();
                    gridCellAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });


            // notifyDataSetChanged();
        }

        public class ViewHolder {
            RelativeLayout item_layout;
            TextView info_text;
            View rack_list_avalablity;
            ImageView door_img;
        }

        class MainItemClick implements View.OnClickListener {

            int position;
            ViewHolder viewHolder;

            MainItemClick(int pos, ViewHolder holder) {
                position = pos;
                viewHolder = holder;
            }

            @Override
            public void onClick(View view) {
                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    if (!grid_cell_name.get(position).getDoorPosition().equals("1")) {

                        String name = String.valueOf(playersArrayList.get(position).getShapID());
                        if (prevSelection == -1) //nothing selected
                        {
                            playersArrayList.get(position).setChecked(true);
                            gridCellAdapter.notifyDataSetChanged();
                            prevSelection = position;
                            viewHolder.item_layout.setBackgroundResource(R.drawable.green_back);
                            SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                            if (playersArrayList.get(position).getRackList().size() > 0) {
                                SelectedRack_ID = String.valueOf(playersArrayList.get(position).getRackList().get(0).getFldRacksId());
                            } else {
                                SelectedRack_ID = "0";
                            }

                        } else // Some other selection
                        {
                            //deselect previously selected
                            if (prevSelection != position) {
                                playersArrayList.get(prevSelection).setChecked(false);

                                viewHolder.item_layout.setBackgroundResource(R.drawable.background_edit_square);
                                playersArrayList.get(position).setChecked(true);
                                gridCellAdapter.notifyDataSetChanged();
                                prevSelection = position;
                                SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                                if (playersArrayList.get(position).getRackList().size() > 0) {
                                    SelectedRack_ID = String.valueOf(playersArrayList.get(position).getRackList().get(0).getFldRacksId());
                                } else {
                                    SelectedRack_ID = "0";
                                }
                            }

                        }

                        if (playersArrayList.get(position).getRackList().size() > 0) {
                            ShowRack_dialog(prevSelection, holder);
                        }

                        Log.e("SelectedGridRackID", String.valueOf(SelectedRack_ID));
                        Log.e("SelectedGridLocation", String.valueOf(SelectedGridShapeID));

                        notifyDataSetChanged();
                    }
                }
            }
        }

        private class CustomRacklistAdapter extends BaseAdapter {

            private final String TAG = AddItemFragment.GridCellAdapter.class.getSimpleName();
            Context mContext;
            List<StorageShapeModel.Storage.ShapsList.RackList> CustomRackList;
            ArrayList<Integer> sr_no_list;
            LayoutInflater inflater;
            String teamType;
            private CustomRacklistAdapter.ViewHolder holder;


            public CustomRacklistAdapter(Context mContext, List<StorageShapeModel.Storage.ShapsList.RackList> rackList, ArrayList<Integer> sr_no_list) {
                this.mContext = mContext;
                CustomRackList = rackList;
                this.sr_no_list = sr_no_list;
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            public int getCount() {
                return CustomRackList.size();
            }

            public Object getItem(int position) {
                return CustomRackList.get(position);
            }

            public long getItemId(int position) {
                return CustomRackList.indexOf(getItem(position));
            }


            public View getView(final int position, View convertView, final ViewGroup parent) {

                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.rack_list_item, null);
                    holder = new ViewHolder();

                    holder.rack_relative = convertView.findViewById(R.id.rack_relative);
                    holder.sr_no_txt = convertView.findViewById(R.id.sr_no_txt);
                    holder.rack = convertView.findViewById(R.id.rack);
                    convertView.setTag(holder);


                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.sr_no_txt.setText(String.valueOf(sr_no_list.get(position)));

                if (!TextUtils.isEmpty(String.valueOf(Selection_rack_no))) {
                    if (Selection_rack_no.equals(String.valueOf(sr_no_list.get(position)))) {
                        holder.rack.setBackgroundColor(Color.WHITE);
                    } else {
                        holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                    }
                } else {
                    holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                }

                holder.rack_relative.setOnClickListener(new RackClick(position, holder));

                return convertView;
            }

            public class ViewHolder {
                RelativeLayout rack_relative, rack;
                TextView sr_no_txt;

            }

            class RackClick implements View.OnClickListener {

                int position;
                ViewHolder viewHolder;

                RackClick(int pos, ViewHolder holder) {
                    position = pos;
                    viewHolder = holder;
                }

                @Override
                public void onClick(View view) {


                    if (prevSelection_rack == -1) //nothing selected
                    {
                        CustomRackList.get(position).setCheckRack(true);
                        customracklistadapter.notifyDataSetChanged();
                        prevSelection_rack = position;
                        viewHolder.rack.setBackgroundColor(Color.WHITE);
                        SelectedRack_ID = String.valueOf(CustomRackList.get(position).getFldRacksId());
                        Selection_rack_no = String.valueOf(sr_no_list.get(position));


                    } else // Some other selection
                    {
                        //deselect previously selected
                        if (prevSelection_rack != position) {
                            CustomRackList.get(prevSelection_rack).setCheckRack(false);

                            viewHolder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                            CustomRackList.get(position).setCheckRack(true);
                            customracklistadapter.notifyDataSetChanged();
                            prevSelection_rack = position;
                            SelectedRack_ID = String.valueOf(CustomRackList.get(position).getFldRacksId());
                            Selection_rack_no = String.valueOf(sr_no_list.get(position));

                        }

                    }
                    Log.e("SelectedRack_Position", String.valueOf(position));


                    notifyDataSetChanged();
                }
            }
        }


    }
}
