package com.storelogflog.StorageSelection.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.model.StorageShapeModel;
import com.storelogflog.activity.BaseActivity;
import com.storelogflog.apiCall.StorageListShapeApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.ExpandableHeightGridView;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.fragment.AddItemFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShanpeInfoActivity extends BaseActivity implements VolleyApiResponseString {
    String TAG = this.getClass().getSimpleName();
    Context mContext;
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

    LinearLayout Grid5_linear, Grid4_linear, Grid3_linear, toolbar_back;
    ArrayList<String> GridCell_list = new ArrayList<>();
    ArrayList<StorageShapeModel.Storage.ShapsList> Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list5 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list4 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list3 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list5 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list4 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list3 = new ArrayList<>();
    String[] grid_cell = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "y", "Z",
            "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL", "MM", "NN", "OO",
            "PP", "QQ", "RR", "SS", "TT", "UU", "VV", "WW", "XX", "Yy", "ZZ"};
    TextView view_item;
    private String numberofColumn,Rack_ID;
    AppCompatTextView txt_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanpe_info);

        initViews();


    }

    @Override
    public void initViews() {

        mContext = ShanpeInfoActivity.this;

        Grid_View5 = findViewById(R.id.Grid_View5);
        Grid_View4 = findViewById(R.id.Grid_View4);
        Grid_View3 = findViewById(R.id.Grid_View3);

        Grid5_linear = findViewById(R.id.Grid5_linear);
        Grid4_linear = findViewById(R.id.Grid4_linear);
        Grid3_linear = findViewById(R.id.Grid3_linear);

        door_1 = findViewById(R.id.door_1);
        door_2 = findViewById(R.id.door_2);
        door_3 = findViewById(R.id.door_3);
        door_4 = findViewById(R.id.door_4);
        door_5 = findViewById(R.id.door_5);

        linear_door_1 = findViewById(R.id.linear_door_1);
        linear_door_2 = findViewById(R.id.linear_door_2);
        linear_door_3 = findViewById(R.id.linear_door_3);
        linear_door_4 = findViewById(R.id.linear_door_4);
        linear_door_5 = findViewById(R.id.linear_door_5);

        door_img1 = findViewById(R.id.door_img1);
        door_img2 = findViewById(R.id.door_img2);
        door_img3 = findViewById(R.id.door_img3);
        door_img4 = findViewById(R.id.door_img4);
        door_img5 = findViewById(R.id.door_img5);


        toolbar_back = findViewById(R.id.toolbar_back);
        view_item = findViewById(R.id.view_item);


        door_11 = findViewById(R.id.door_11);
        door_12 = findViewById(R.id.door_12);
        door_13 = findViewById(R.id.door_13);
        door_14 = findViewById(R.id.door_14);
        linear_door_11 = findViewById(R.id.linear_door_11);
        linear_door_12 = findViewById(R.id.linear_door_12);
        linear_door_13 = findViewById(R.id.linear_door_13);
        linear_door_14 = findViewById(R.id.linear_door_14);
        door_img11 = findViewById(R.id.door_img11);
        door_img12 = findViewById(R.id.door_img12);
        door_img13 = findViewById(R.id.door_img13);
        door_img14 = findViewById(R.id.door_img14);

        door_21 = findViewById(R.id.door_21);
        door_22 = findViewById(R.id.door_22);
        door_23 = findViewById(R.id.door_23);

        linear_door_21 = findViewById(R.id.linear_door_21);
        linear_door_22 = findViewById(R.id.linear_door_22);
        linear_door_23 = findViewById(R.id.linear_door_23);

        door_img21 = findViewById(R.id.door_img21);
        door_img22 = findViewById(R.id.door_img22);
        door_img23 = findViewById(R.id.door_img23);

        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        txt_toolbar_title.setText(getIntent().getStringExtra("title_name"));


        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GetStorageShape();
    }

    @Override
    public void initListeners() {

    }

    void GetStorageShape() {
        if (Utility.isInternetConnected(mContext)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();

                jsonObjectPayload.put("unit_id", getIntent().getStringExtra("unitID"));
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new StorageListShapeApiCall(mContext, this, token, Constants.StorageListShape);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast("No Internet Connection");
        }

    }

    @Override
    public void onClick(View v) {

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
                                Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();
                                if (storageShapeModel.getStorage().getShapsList().size() > 0) {

                                    if (storageShapeModel.getStorage().getShapsList().size() > 0) {
                                        Shape_name = new ArrayList<>();
                                        Shape_value_list5 = new ArrayList<>();
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
                                }

                                Rack_ID = getIntent().getStringExtra("RackID");
                                if (numberofColumn.equals("5")) {
                                    Grid5_linear.setVisibility(View.VISIBLE);
                                    Grid4_linear.setVisibility(View.GONE);
                                    Grid3_linear.setVisibility(View.GONE);

                                    GridCellAdapter gridCellAdapter = new GridCellAdapter(mContext, storageShapeModel.getStorage().getShapsList(), Shape_name,
                                            getIntent().getStringExtra("Shape_id2"), getIntent().getStringExtra("RackID_position"));
                                    Grid_View5.setExpanded(true);
                                    Grid_View5.setNumColumns(5);
                                    Grid_View5.setAdapter(gridCellAdapter);
                                    gridCellAdapter.notifyDataSetChanged();


                                } else if (numberofColumn.equals("4")) {
                                    Grid4_linear.setVisibility(View.VISIBLE);
                                    Grid3_linear.setVisibility(View.GONE);
                                    Grid5_linear.setVisibility(View.GONE);

                                    GridCellAdapter gridCellAdapter = new GridCellAdapter(mContext, Shap_list4, Shape_name,
                                            getIntent().getStringExtra("Shape_id2"), getIntent().getStringExtra("RackID_position"));
                                    Grid_View4.setExpanded(true);
                                    Grid_View4.setNumColumns(4);
                                    Grid_View4.setAdapter(gridCellAdapter);
                                    gridCellAdapter.notifyDataSetChanged();

                                } else if (numberofColumn.equals("3")) {
                                    Grid3_linear.setVisibility(View.VISIBLE);
                                    Grid4_linear.setVisibility(View.GONE);
                                    Grid5_linear.setVisibility(View.GONE);

                                    GridCellAdapter gridCellAdapter = new GridCellAdapter(mContext, Shap_list3, Shape_name,
                                            getIntent().getStringExtra("Shape_id2"), getIntent().getStringExtra("RackID_position"));
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
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

                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
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

                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("2")) {
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

                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("3")) {
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
                                    }
                                    else if (storageShapeModel.getStorage().getDoorType().equals("4")) {
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
                            Log.e(TAG,"Error========>"+e.toString());
                        }
                    }
                }
                    break;
                }
        }

        @Override
        public void onAPiResponseError (VolleyError error,int code){
            Log.e(TAG,"Error========>"+error.toString());
        switch (code) {
                case Constants.StorageListShape:
                    hideLoading();


                    break;
            }
        }

        @Override
        public void onBackPressed () {
            finish();
            super.onBackPressed();
        }

        public class GridCellAdapter extends BaseAdapter {

            private final String TAG = GridCellAdapter.class.getSimpleName();
            Context mContext;
            List<StorageShapeModel.Storage.ShapsList> playersArrayList;
            ArrayList<StorageShapeModel.Storage.ShapsList> grid_cell_name;
            LayoutInflater inflater;
            String rackID_position;
            String shape_id2;
            private ViewHolder holder;


            public GridCellAdapter(Context context, List<StorageShapeModel.Storage.ShapsList> arrayList, ArrayList<StorageShapeModel.Storage.ShapsList> grid_cell, String shape_id2, String rackID_position) {
                mContext = context;
                playersArrayList = arrayList;
                this.grid_cell_name = grid_cell;
                this.shape_id2 = shape_id2;
                this.rackID_position = rackID_position;
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
                    holder.door_img = convertView.findViewById(R.id.door_img);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                if(playersArrayList.get(position).getRackList().size()>0) {
                    holder.rack_list_avalablity.setVisibility(View.VISIBLE);
                }else {
                    holder.rack_list_avalablity.setVisibility(View.GONE);
                }

                if (String.valueOf(playersArrayList.get(position).getShapID()).equals(String.valueOf(shape_id2))) {

                    if (!grid_cell_name.get(position).getShape_name().equals("")) {
                        if (playersArrayList.get(position).getRackList().size() > 0) {
                            holder.item_layout.setBackgroundResource(R.drawable.green_back);
                            holder.info_text.setText(grid_cell_name.get(position).getShape_name() + " - " + rackID_position);

                            if (grid_cell_name.get(position).getDoorPosition().equals("1")){
                                holder.door_img.setVisibility(View.VISIBLE);
                                holder.info_text.setVisibility(View.GONE);
                            }else {
                                holder.door_img.setVisibility(View.GONE);
                                holder.info_text.setVisibility(View.VISIBLE);
                            }

                        } else {
                            holder.item_layout.setBackgroundResource(R.drawable.green_back);
                            holder.info_text.setText(grid_cell_name.get(position).getShape_name());

                            if (grid_cell_name.get(position).getDoorPosition().equals("1")){
                                holder.door_img.setVisibility(View.VISIBLE);
                                holder.info_text.setVisibility(View.GONE);
                            }else {
                                holder.door_img.setVisibility(View.GONE);
                                holder.info_text.setVisibility(View.VISIBLE);
                            }

                        }
                    } else {
                        holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                    }


                } else {
                    if (!grid_cell_name.get(position).getShape_name().equals("")) {
                        holder.item_layout.setBackgroundResource(R.drawable.deactive_character);
                        holder.info_text.setText(grid_cell_name.get(position).getShape_name());
                        if (grid_cell_name.get(position).getDoorPosition().equals("1")){
                            holder.door_img.setVisibility(View.VISIBLE);
                            holder.info_text.setVisibility(View.GONE);
                        }else {
                            holder.door_img.setVisibility(View.GONE);
                            holder.info_text.setVisibility(View.VISIBLE);
                        }

                    } else {
                        holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                    }
                }


                holder.item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!grid_cell_name.get(position).getDoorPosition().equals("1")) {

                            if (String.valueOf(playersArrayList.get(position).getShapID()).equals(String.valueOf(shape_id2))) {
                                Log.e("RackList_size", String.valueOf(playersArrayList.get(position).getRackList().size()));
                                if (playersArrayList.get(position).getRackList().size() > 0) {
                                    ShowRack_dialog1(position, rackID_position);
                                }
                            } else {
                                Log.e("RackList_size", String.valueOf(playersArrayList.get(position).getRackList().size()));
                            }
                        }

                    }
                });

                return convertView;
            }

            private void ShowRack_dialog1(int position, String rackID_position) {
                Dialog dialog1 = new Dialog(mContext, android.R.style.Theme_Light);

                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.dialogue_select_shelft);
                dialog1.getWindow().setBackgroundDrawableResource(R.color.black);

                ListView Rack_list = dialog1.findViewById(R.id.Rack_list);
                LinearLayout toolbar_back = dialog1.findViewById(R.id.toolbar_back);
                TextView proceed_btn = dialog1.findViewById(R.id.proceed_btn);

                List<StorageShapeModel.Storage.ShapsList.RackList> rack_list = new ArrayList();
                rack_list = playersArrayList.get(position).getRackList();

                ArrayList<Integer>Sr_no_list = new ArrayList<>();

                int Index = 0;
                for (int j=0; j<rack_list.size(); j++){

                    if (Index==0){
                        Index = rack_list.size() - 1;

                        Log.e("Index11", String.valueOf(Index));
                        Sr_no_list.add(Index);

                    }else {

                        Index = Index-1;
                        Log.e("Index12", String.valueOf(Index));
                        Sr_no_list.add(Index);

                    }

                }



                Collections.reverse(rack_list);
                proceed_btn.setVisibility(View.GONE);
                CustomRacklistAdapter customracklistadapter = new GridCellAdapter.CustomRacklistAdapter(mContext, rack_list, rackID_position,Sr_no_list);
                Rack_list.setAdapter(customracklistadapter);


                toolbar_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });


                dialog1.show();
                // notifyDataSetChanged();
            }

            public class ViewHolder {
                RelativeLayout item_layout;
                TextView info_text;
                View rack_list_avalablity;
                ImageView door_img;
            }

            private class CustomRacklistAdapter extends BaseAdapter {

                private final String TAG = AddItemFragment.GridCellAdapter.class.getSimpleName();
                Context mContext;
                List<StorageShapeModel.Storage.ShapsList.RackList> CustomRackList;
                ArrayList<Integer> sr_no_list;
                LayoutInflater inflater;
                String teamType;
                String rackID_position;
                private ViewHolder holder;

                public CustomRacklistAdapter(Context mContext, List<StorageShapeModel.Storage.ShapsList.RackList> rackList, String rackID_position, ArrayList<Integer> sr_no_list) {
                    this.mContext = mContext;
                    CustomRackList = rackList;
                    this.rackID_position = rackID_position;
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

                    if (String.valueOf(sr_no_list.get(position)).equals(rackID_position)) {
                        holder.rack.setBackgroundColor(Color.WHITE);
                    } else {
                        holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                    }
                    holder.rack_relative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.rack_relative.setBackgroundColor(Color.TRANSPARENT);
                        }
                    });

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
