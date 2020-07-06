package com.storelogflog.uk.StorageSelection.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.StorageShapeModel;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.AddRackAPICall;
import com.storelogflog.uk.apiCall.DeleteRackAPICall;
import com.storelogflog.uk.apiCall.StorageListShapeApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.ExpandableHeightGridView;
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


public class ManageShelftFragment extends BaseFragment implements VolleyApiResponseString {

    private final String TAG = ManageShelftFragment.class.getSimpleName();
    // TODO: Rename and change types and number of parameters
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
    String SelectedGridShapeID, SelectedGridRackID, Selectedshape_name = "";
    ArrayList<String> GridCell_list = new ArrayList<>();
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
    int Add_Rack_count = 1;
    int Count = 1;
    AlertDialog alertDialog, alertDialog1;
    private Storage storage1;
    private String numberofColumn, pos = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_shelft, container, false);

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


        ((HomeActivity) getActivity()).enableViews(true, "Manage Shelf");

        if (getArguments() != null) {
            storage1 = (Storage) getArguments().getSerializable("storage");
            if (storage1 != null) {
                GetStorageShape(String.valueOf(storage1.getUnitID()));

                Log.e("UnitID", storage1.getUnitID());
            }

        }
    }

    @Override
    public void initListeners() {

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


    void GetStorageShape2(String storage_ID) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("unit_id", storage_ID);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
              //  showLoading("Loading...");
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
            case Constants.StorageListShape:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                StorageShapeModel storageShapeModel = new Gson().fromJson(response, StorageShapeModel.class);

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

                                        if (i <= 4) {
                                            if (i == 4) {
                                                if (String.valueOf(Shape_value_list5.get(4)).equals("1")) {
                                                    numberofColumn = "5";
                                                }
                                            } else if (i == 3) {
                                                if (String.valueOf(Shape_value_list5.get(3)).equals("1")) {
                                                    numberofColumn = "4";
                                                }
                                            } else if (i == 2) {
                                                if (String.valueOf(Shape_value_list5.get(2)).equals("1")) {
                                                    numberofColumn = "3";
                                                }
                                            }
                                        } else if (i <= 9) {
                                            if (i == 9) {
                                                if (String.valueOf(Shape_value_list5.get(9)).equals("1")) {
                                                    numberofColumn = "5";
                                                } else if (i == 8) {

                                                    if (String.valueOf(Shape_value_list5.get(8)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 7) {

                                                    if (String.valueOf(Shape_value_list5.get(7)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }

                                            } else if (i <= 14) {

                                                if (i == 14) {
                                                    if (String.valueOf(Shape_value_list5.get(14)).equals("1")) {
                                                        numberofColumn = "5";
                                                    }
                                                } else if (i == 13) {

                                                    if (String.valueOf(Shape_value_list5.get(13)).equals("1")) {
                                                        numberofColumn = "4";
                                                    }
                                                } else if (i == 12) {

                                                    if (String.valueOf(Shape_value_list5.get(12)).equals("1")) {
                                                        numberofColumn = "3";
                                                    }
                                                }
                                            } else if (i <= 19) {

                                                if (i == 19) {
                                                    if (String.valueOf(Shape_value_list5.get(19)).equals("1")) {
                                                        numberofColumn = "5";
                                                    } else if (i == 18) {
                                                        if (String.valueOf(Shape_value_list5.get(18)).equals("1")) {
                                                            numberofColumn = "4";
                                                        }
                                                    } else if (i == 17) {
                                                        if (String.valueOf(Shape_value_list5.get(17)).equals("1")) {
                                                            numberofColumn = "3";
                                                        }
                                                    }
                                                }
                                            } else if (i <= 24) {

                                                if (i == 24) {
                                                    if (String.valueOf(Shape_value_list5.get(24)).equals("1")) {
                                                        numberofColumn = "5";
                                                    } else if (i == 23) {
                                                        if (String.valueOf(Shape_value_list5.get(23)).equals("1")) {
                                                            numberofColumn = "4";
                                                        }
                                                    } else if (i == 22) {
                                                        if (String.valueOf(Shape_value_list5.get(22)).equals("1")) {
                                                            numberofColumn = "3";
                                                        }
                                                    }
                                                }

                                            } else if (i <= 29) {
                                                if (i == 29) {
                                                    if (String.valueOf(Shape_value_list5.get(29)).equals("1")) {
                                                        numberofColumn = "5";
                                                    } else if (i == 28) {
                                                        if (String.valueOf(Shape_value_list5.get(28)).equals("1")) {
                                                            numberofColumn = "4";
                                                        }
                                                    } else if (i == 27) {
                                                        if (String.valueOf(Shape_value_list5.get(27)).equals("1")) {
                                                            numberofColumn = "3";
                                                        }
                                                    }
                                                }
                                            }

                                        } else if (i <= 34) {
                                            if (i == 34) {
                                                if (String.valueOf(Shape_value_list5.get(34)).equals("1")) {
                                                    numberofColumn = "5";
                                                }
                                            } else if (i == 33) {
                                                if (String.valueOf(Shape_value_list5.get(33)).equals("1")) {
                                                    numberofColumn = "4";
                                                }
                                            } else if (i == 32) {
                                                if (String.valueOf(Shape_value_list5.get(32)).equals("1")) {
                                                    numberofColumn = "3";
                                                }
                                            }


                                        }
                                    }


                                    Log.e(TAG, "numberofColumn==========>" + numberofColumn);


                                    if (numberofColumn.equals("4")) {
                                        for (int l = 0; l < Shap_list5.size(); l++) {

                                            if (l == 4 || l == 9 || l == 14 || l == 19 || l == 24 || l == 29 || l == 34) {

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

                                            if (l == 4 || l == 9 || l == 14 || l == 19 || l == 24 || l == 29 || l == 34 ||
                                                    l == 3 || l == 8 || l == 13 || l == 18 || l == 23 || l == 28 || l == 33) {

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

                                    if (numberofColumn.equals("5")) {
                                        int j = 0;
                                        for (int i = 0; i < GridCell_list.size(); i++) {

                                            if (i < Shape_value_list5.size()) {

                                                int value = Shape_value_list5.get(i);
                                                if (value == 0) {
                                                    Log.e(TAG, "value =====> " + value);
                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                    selectedGridModel.setShape_name("");
                                                    Shape_name.add(selectedGridModel);
                                                } else {
                                                    Log.e(TAG, "value =====> " + value);
                                                    Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));


                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                    selectedGridModel.setShape_name(GridCell_list.get(j));
                                                    Shape_name.add(selectedGridModel);

                                                    j++;

                                                }
                                            }
                                        }
                                    }
                                    else if (numberofColumn.equals("4")) {
                                        int j = 0;
                                        for (int i = 0; i < GridCell_list.size(); i++) {

                                            if (i < Shape_value_list4.size()) {

                                                int value = Shape_value_list4.get(i);

                                                if (value == 0) {
                                                    Log.e(TAG, "value =====> " + value);
                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                    selectedGridModel.setShape_name("");
                                                    Shape_name.add(selectedGridModel);

                                                } else {
                                                    Log.e(TAG, "value =====> " + value);
                                                    Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));

                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                    selectedGridModel.setShape_name(GridCell_list.get(j));
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
                                                    Shape_name.add(selectedGridModel);

                                                } else {
                                                    Log.e(TAG, "value =====> " + value);
                                                    Log.e(TAG, "GridCell_list =====> " + GridCell_list.get(j));

                                                    StorageShapeModel.Storage.ShapsList selectedGridModel = new StorageShapeModel.Storage.ShapsList();
                                                    selectedGridModel.setShape_name(GridCell_list.get(j));
                                                    Shape_name.add(selectedGridModel);

                                                    j++;

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
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("5")) {

                                        if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                            door_img1.setImageResource(R.drawable.loft_door);
                                            door_img1.setVisibility(View.VISIBLE);
                                            door_img2.setVisibility(View.GONE);
                                            door_img3.setVisibility(View.GONE);
                                            door_img4.setVisibility(View.GONE);
                                            door_img5.setVisibility(View.GONE);
                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                            door_img2.setImageResource(R.drawable.loft_door);
                                            door_img1.setVisibility(View.GONE);
                                            door_img2.setVisibility(View.VISIBLE);
                                            door_img3.setVisibility(View.GONE);
                                            door_img4.setVisibility(View.GONE);
                                            door_img5.setVisibility(View.GONE);
                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                            door_img3.setImageResource(R.drawable.loft_door);
                                            door_img1.setVisibility(View.GONE);
                                            door_img2.setVisibility(View.GONE);
                                            door_img3.setVisibility(View.VISIBLE);
                                            door_img4.setVisibility(View.GONE);
                                            door_img5.setVisibility(View.GONE);
                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                            door_img4.setImageResource(R.drawable.loft_door);
                                            door_img1.setVisibility(View.GONE);
                                            door_img2.setVisibility(View.GONE);
                                            door_img3.setVisibility(View.GONE);
                                            door_img4.setVisibility(View.VISIBLE);
                                            door_img5.setVisibility(View.GONE);
                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                            door_img5.setImageResource(R.drawable.loft_door);
                                            door_img1.setVisibility(View.GONE);
                                            door_img2.setVisibility(View.GONE);
                                            door_img3.setVisibility(View.GONE);
                                            door_img4.setVisibility(View.GONE);
                                            door_img5.setVisibility(View.VISIBLE);
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
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("5")) {

                                        if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                            door_img11.setImageResource(R.drawable.loft_door);
                                            door_img11.setVisibility(View.VISIBLE);
                                            door_img12.setVisibility(View.GONE);
                                            door_img13.setVisibility(View.GONE);
                                            door_img14.setVisibility(View.GONE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                            door_img12.setImageResource(R.drawable.loft_door);
                                            door_img11.setVisibility(View.GONE);
                                            door_img12.setVisibility(View.VISIBLE);
                                            door_img13.setVisibility(View.GONE);
                                            door_img14.setVisibility(View.GONE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                            door_img13.setImageResource(R.drawable.loft_door);
                                            door_img11.setVisibility(View.GONE);
                                            door_img12.setVisibility(View.GONE);
                                            door_img13.setVisibility(View.VISIBLE);
                                            door_img14.setVisibility(View.GONE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,1,0")) {
                                            door_img14.setImageResource(R.drawable.loft_door);
                                            door_img11.setVisibility(View.GONE);
                                            door_img12.setVisibility(View.GONE);
                                            door_img13.setVisibility(View.GONE);
                                            door_img14.setVisibility(View.VISIBLE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,0,0,1")) {
                                            door_img14.setImageResource(R.drawable.loft_door);
                                            door_img11.setVisibility(View.GONE);
                                            door_img12.setVisibility(View.GONE);
                                            door_img13.setVisibility(View.GONE);
                                            door_img14.setVisibility(View.VISIBLE);

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
                                    } else if (storageShapeModel.getStorage().getDoorType().equals("5")) {

                                        if (storageShapeModel.getStorage().getDoors().equals("1,0,0,0,0")) {
                                            door_img21.setImageResource(R.drawable.loft_door);
                                            door_img21.setVisibility(View.VISIBLE);
                                            door_img22.setVisibility(View.GONE);
                                            door_img23.setVisibility(View.GONE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,1,0,0,0")) {
                                            door_img22.setImageResource(R.drawable.loft_door);
                                            door_img21.setVisibility(View.GONE);
                                            door_img22.setVisibility(View.VISIBLE);
                                            door_img23.setVisibility(View.GONE);

                                        } else if (storageShapeModel.getStorage().getDoors().equals("0,0,1,0,0")) {
                                            door_img23.setImageResource(R.drawable.loft_door);
                                            door_img21.setVisibility(View.GONE);
                                            door_img22.setVisibility(View.GONE);
                                            door_img23.setVisibility(View.VISIBLE);

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

            case Constants.Add_Rack:
            //    hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        Log.e("payload", String.valueOf(payload));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                GetStorageShape2(String.valueOf(storage1.getUnitID()));
                            }
                        } catch (Exception e) {
                            Log.e("Error=====>", e.toString());
                        }
                    }
                }

                break;

            case Constants.Delete_Rack:
              //  hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        Log.e("payload", String.valueOf(payload));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {
                                GetStorageShape2(String.valueOf(storage1.getUnitID()));

                            }
                        } catch (Exception e) {
                            Log.e("Error=====>", e.toString());
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

            case Constants.Add_Rack:
                hideLoading();


                break;

            case Constants.Delete_Rack:
                break;
        }
    }

    private void AddRack() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("unit_id", storage1.getUnitID());
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("shap_id", Integer.parseInt(SelectedGridShapeID));
                jsonObjectPayload.put("rack_location", String.valueOf(Add_Rack_count + 1));

                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                Log.e("InputToken", token);
                Log.e("InputPerameter", jsonObjectPayload.toString());
                showLoading("Loading...");
                new AddRackAPICall(getActivity(), this, token, Constants.Add_Rack);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast(getActivity(), "No Internet Connection");
        }
    }

    private void DeleteRack(Integer shapID) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("shape_id", shapID);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                Log.e("InputToken", token);
                Log.e("InputPerameter", jsonObjectPayload.toString());
                showLoading("Loading...");
                new DeleteRackAPICall(getActivity(), this, token, Constants.Delete_Rack);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast(getActivity(), "No Internet Connection");
        }

    }

    public class GridCellAdapter extends BaseAdapter {

        private final String TAG = GridCellAdapter.class.getSimpleName();
        Context mContext;
        List<StorageShapeModel.Storage.ShapsList> playersArrayList;
        ArrayList<StorageShapeModel.Storage.ShapsList> grid_cell_name;
        LayoutInflater inflater;
        String teamType;
        private GridCellAdapter.ViewHolder holder;


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
                holder = new GridCellAdapter.ViewHolder();

                holder.item_layout = convertView.findViewById(R.id.garage_relative);
                holder.info_text = convertView.findViewById(R.id.info_text);
                holder.rack_list_avalablity = convertView.findViewById(R.id.rack_list_avalablity);

                convertView.setTag(holder);
            } else {
                holder = (GridCellAdapter.ViewHolder) convertView.getTag();
            }

           /* if (playersArrayList.get(position).isChecked()) {
                holder.item_layout.setBackgroundResource(R.drawable.green_back);
            } else {
                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    holder.item_layout.setBackgroundResource(R.drawable.deactive_character);
                } else {

                    holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                }
            }*/


            if (grid_cell_name.get(position).getShape_name().equals(Selectedshape_name)) {

                if (!grid_cell_name.get(position).getShape_name().equals("")) {

                } else {

                    holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                }
            } else {
                holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()));
                holder.item_layout.setBackgroundResource(R.drawable.deactive_character);
            }

            if (playersArrayList.get(position).getRackList() != null) {
                if (playersArrayList.get(position).getRackList().size() > 0) {
                    holder.rack_list_avalablity.setVisibility(View.VISIBLE);
                    if (!grid_cell_name.get(position).getShape_name().equals("")) {
                        holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name())+"-"+String.valueOf(playersArrayList.get(position).getRackList().size()));
                        holder.item_layout.setBackgroundResource(R.drawable.green_back);
                    } else {
                        holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                    }
                } else {
                    holder.rack_list_avalablity.setVisibility(View.GONE);
                    if (!grid_cell_name.get(position).getShape_name().equals("")) {
                        holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()));
                        holder.item_layout.setBackgroundResource(R.drawable.deactive_character);
                    } else {

                        holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                    }

                }
            } else {
                holder.rack_list_avalablity.setVisibility(View.GONE);
            }
            //finalConvertView = convertView;
            holder.item_layout.setOnClickListener(new MainItemClick(position, holder));


            return convertView;
        }

        private void AddRack_dialog(int position, ViewHolder holder) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.add_rack_dialogue,
                    null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

            builder.setView(layout);
            builder.setCancelable(true);
            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            RelativeLayout minus_count = layout.findViewById(R.id.minus_count);
            RelativeLayout add_count = layout.findViewById(R.id.add_count);
            TextView count_txt = layout.findViewById(R.id.count_txt);
            TextView reset_btn = layout.findViewById(R.id.reset_btn);
            TextView proceed_btn = layout.findViewById(R.id.proceed_btn);

            alertDialog.show();

            count_txt.setText(String.valueOf(Count));

            add_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Count == 10) {
                        ShowDialogue();
                    } else {
                        Count++;
                        count_txt.setText(String.valueOf(Count));
                    }

                }
            });

            minus_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Count == 1) {

                        count_txt.setText("1");
                    } else {
                        Count--;
                        count_txt.setText(String.valueOf(Count));
                    }


                }
            });


            reset_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Count = 1;
                    count_txt.setText(String.valueOf(Count));
                }
            });


            Selectedshape_name = grid_cell_name.get(position).getShape_name();
            proceed_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Add_Rack_count = Count;
                    gridCellAdapter.notifyDataSetChanged();
                    AddRack();
                    alertDialog.dismiss();
                }
            });

        }

        private void ShowDialogue() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialogue_cancle_shelft,
                    null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

            builder.setView(layout);
            builder.setCancelable(true);
            alertDialog1 = builder.create();
            alertDialog1.setCanceledOnTouchOutside(true);
            alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            ImageView close = layout.findViewById(R.id.close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });

            alertDialog1.show();
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


            GridCellAdapter.CustomRacklistAdapter customracklistadapter = new GridCellAdapter.CustomRacklistAdapter(mContext, playersArrayList.get(position).getRackList());
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

        private void DeleteRack_dialog(Integer shapID) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.cam_permission,
                    null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

            builder.setView(layout);
            builder.setCancelable(true);
            alertDialog1 = builder.create();
            alertDialog1.setCanceledOnTouchOutside(true);
            alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            TextView facebook_txt = layout.findViewById(R.id.facebook_txt);
            TextView allow_txt = layout.findViewById(R.id.allow_txt);
            TextView btn_cancle = layout.findViewById(R.id.btn_cancle);
            TextView btn_OK = layout.findViewById(R.id.btn_OK);

            facebook_txt.setText(mContext.getResources().getString(R.string.app_name));
            allow_txt.setText(mContext.getResources().getString(R.string.delete_rack));
            btn_OK.setText(mContext.getResources().getString(R.string.delete));

            alertDialog1.show();

            btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });

            btn_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteRack(shapID);
                    alertDialog1.dismiss();
                }
            });


        }

        public class ViewHolder {
            RelativeLayout item_layout;
            TextView info_text;
            View rack_list_avalablity;
        }

        class MainItemClick implements View.OnClickListener {

            int position;
            GridCellAdapter.ViewHolder viewHolder;

            MainItemClick(int pos, ViewHolder holder) {
                position = pos;
                viewHolder = holder;
            }

            @Override
            public void onClick(View view) {

                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    if (prevSelection == -1) //nothing selected
                    {
                        playersArrayList.get(position).setChecked(true);
                        gridCellAdapter.notifyDataSetChanged();
                        prevSelection = position;
                        viewHolder.item_layout.setBackgroundResource(R.drawable.green_back);
                        SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                        if (playersArrayList.get(position).getRackList().size() > 0) {
                            SelectedGridRackID = String.valueOf(playersArrayList.get(position).getRackList().get(0).getFldRacksId());
                        } else {
                            SelectedGridRackID = "0";
                        }


                    } else // Some other selection
                    {
                        //deselect previously selected
                        if (prevSelection != position) {
                            playersArrayList.get(prevSelection).setChecked(false);

                            viewHolder.item_layout.setBackgroundResource(R.drawable.deactive_character);
                            playersArrayList.get(position).setChecked(true);
                            gridCellAdapter.notifyDataSetChanged();
                            prevSelection = position;
                            SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                            if (playersArrayList.get(position).getRackList().size() > 0) {
                                SelectedGridRackID = String.valueOf(playersArrayList.get(position).getRackList().get(0).getFldRacksId());
                            } else {
                                SelectedGridRackID = "0";
                            }
                        }

                    }

                    if (playersArrayList.get(position).getRackList().size() > 0) {
                        // showToast(mContext, "You have already added rack to this loaction");
                        DeleteRack_dialog(playersArrayList.get(position).getShapID());
                    } else {

                        AddRack_dialog(prevSelection, holder);

                    }

                }

                Log.e("SelectedGridRackID", String.valueOf(SelectedGridRackID));
                Log.e("SelectedGridLocation", String.valueOf(SelectedGridShapeID));

                notifyDataSetChanged();
            }
        }

        private class CustomRacklistAdapter extends BaseAdapter {

            private final String TAG = AddItemFragment.GridCellAdapter.class.getSimpleName();
            Context mContext;
            List<StorageShapeModel.Storage.ShapsList.RackList> CustomRackList;

            LayoutInflater inflater;
            String teamType;
            private GridCellAdapter.CustomRacklistAdapter.ViewHolder holder;


            public CustomRacklistAdapter(Context mContext, List<StorageShapeModel.Storage.ShapsList.RackList> rackList) {
                this.mContext = mContext;
                CustomRackList = rackList;
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
                    holder = new GridCellAdapter.CustomRacklistAdapter.ViewHolder();

                    holder.rack_relative = convertView.findViewById(R.id.rack_relative);
                    holder.sr_no_txt = convertView.findViewById(R.id.sr_no_txt);
                    holder.rack = convertView.findViewById(R.id.rack);
                    convertView.setTag(holder);
                } else {
                    holder = (GridCellAdapter.CustomRacklistAdapter.ViewHolder) convertView.getTag();
                }

                holder.sr_no_txt.setText(String.valueOf(position + 1));

                if (CustomRackList.get(position).isCheckRack()) {
                    holder.rack.setBackgroundColor(Color.WHITE);
                } else {
                    holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                }

                //finalConvertView = convertView;
                return convertView;
            }

            public class ViewHolder {
                RelativeLayout rack_relative, rack;
                TextView sr_no_txt;

            }


        }


    }

}
