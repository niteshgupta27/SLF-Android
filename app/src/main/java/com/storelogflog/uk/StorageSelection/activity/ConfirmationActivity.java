package com.storelogflog.uk.StorageSelection.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.adapter.GridAdapter;
import com.storelogflog.uk.StorageSelection.model.SelectedCellModel;
import com.storelogflog.uk.activity.BaseActivity;
import com.storelogflog.uk.apputil.ExpandableHeightGridView;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmationActivity extends BaseActivity {
    private static final String TAG = "GridCellActivity";

    Context mContext;
    LinearLayout toolbar_back, door_1, door_2, door_3, door_4, door_5,
            linear_door_1, linear_door_2, linear_door_3, linear_door_4, linear_door_5,
            door_11, door_12, door_13, door_14,
            linear_door_11, linear_door_12, linear_door_13, linear_door_14,
            door_21, door_22, door_23,
            linear_door_21, linear_door_22, linear_door_23;
    ImageView door_img1, door_img2, door_img3, door_img4, door_img5,
            door_img11, door_img12, door_img13, door_img14,
            door_img21, door_img22, door_img23;
    ExpandableHeightGridView Grid_View5, Grid_View4,Grid_View3;
    GridAdapter adapter;
    TextView proceed_btn;
    String value = "";
    LinearLayout Grid5_linear, Grid4_linear,Grid3_linear;
    ArrayList<SelectedCellModel> GridArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mContext = this;

        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        toolbar_back = findViewById(R.id.toolbar_back);
        Grid_View5 = findViewById(R.id.Grid_View5);
        Grid_View4 = findViewById(R.id.Grid_View4);
        Grid_View3 =  findViewById(R.id.Grid_View3);

        proceed_btn = findViewById(R.id.proceed_btn);

        door_1 = findViewById(R.id.door_1);
        door_2 = findViewById(R.id.door_2);
        door_3 = findViewById(R.id.door_3);
        door_4 = findViewById(R.id.door_4);
        door_5 = findViewById(R.id.door_5);

        door_img1 = findViewById(R.id.door_img1);
        door_img2 = findViewById(R.id.door_img2);
        door_img3 = findViewById(R.id.door_img3);
        door_img4 = findViewById(R.id.door_img4);
        door_img5 = findViewById(R.id.door_img5);

        linear_door_1 = findViewById(R.id.linear_door_1);
        linear_door_2 = findViewById(R.id.linear_door_2);
        linear_door_3 = findViewById(R.id.linear_door_3);
        linear_door_4 = findViewById(R.id.linear_door_4);
        linear_door_5 = findViewById(R.id.linear_door_5);

        Grid5_linear = findViewById(R.id.Grid5_linear);
        Grid4_linear = findViewById(R.id.Grid4_linear);
        Grid3_linear = findViewById(R.id.Grid3_linear);

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



        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(PrefKeys.GridCell_column, "");
        Type type = new TypeToken<List<SelectedCellModel>>() {
        }.getType();
        List<SelectedCellModel> arrayList = gson.fromJson(json, type);

        GridArrayList.addAll(arrayList);


        if (arrayList != null) {
            if (Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")) == 5) {
                Grid5_linear.setVisibility(View.VISIBLE);
                Grid4_linear.setVisibility(View.GONE);
                Grid3_linear.setVisibility(View.GONE);
                adapter = new GridAdapter(mContext, (ArrayList<SelectedCellModel>) arrayList);
                Grid_View5.setNumColumns(Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")));
                Grid_View5.setExpanded(true);
                Grid_View5.setAdapter(adapter);
            } else if (Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")) == 4) {
                Grid5_linear.setVisibility(View.GONE);
                Grid4_linear.setVisibility(View.VISIBLE);
                Grid3_linear.setVisibility(View.GONE);
                adapter = new GridAdapter(mContext, (ArrayList<SelectedCellModel>) arrayList);
                Grid_View4.setNumColumns(Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")));
                Grid_View4.setExpanded(true);
                Grid_View4.setAdapter(adapter);
            } else if (Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")) == 3) {
                Grid5_linear.setVisibility(View.GONE);
                Grid4_linear.setVisibility(View.GONE);
                Grid3_linear.setVisibility(View.VISIBLE);
                adapter = new GridAdapter(mContext, (ArrayList<SelectedCellModel>) arrayList);
                Grid_View3.setNumColumns(Integer.parseInt(sharedPrefs.getString(PrefKeys.numberofColumn, "")));
                Grid_View3.setExpanded(true);
                Grid_View3.setAdapter(adapter);

            }


        }

    }


    @Override
    public void initListeners() {
        toolbar_back.setOnClickListener(this);
        proceed_btn.setOnClickListener(this);

        door_1.setOnClickListener(this);
        door_2.setOnClickListener(this);
        door_3.setOnClickListener(this);
        door_4.setOnClickListener(this);
        door_5.setOnClickListener(this);


        door_11.setOnClickListener(this);
        door_12.setOnClickListener(this);
        door_13.setOnClickListener(this);
        door_14.setOnClickListener(this);

        door_21.setOnClickListener(this);
        door_22.setOnClickListener(this);
        door_23.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                onBackPressed();
                break;

            case R.id.proceed_btn:

                if (!value.equals("")) {
                    Intent intent = new Intent(mContext, AddStorageActivity.class);
                    startActivity(intent);

                } else {
                    showToast("Please select the position of your doors first!");
                }
                break;

            case R.id.door_1:
                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                    door_img1.setImageResource(R.drawable.loft_door);
                    door_img1.setVisibility(View.VISIBLE);
                    door_img2.setVisibility(View.GONE);
                    door_img3.setVisibility(View.GONE);
                    door_img4.setVisibility(View.GONE);
                    door_img5.setVisibility(View.GONE);
                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        door_img1.setImageResource(R.drawable.doorleft);
                        door_img1.setVisibility(View.VISIBLE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0,0");
                        door_img1.setImageResource(R.drawable.doorleft);
                        door_img2.setImageResource(R.drawable.doorright);
                        door_img1.setVisibility(View.VISIBLE);
                        door_img2.setVisibility(View.VISIBLE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        linear_door_1.setBackgroundResource(R.drawable.white_door);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0,0");


                        linear_door_1.setBackgroundResource(R.drawable.white_door);
                        linear_door_2.setBackgroundResource(R.drawable.white_door);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);


                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }
                }

                break;

            case R.id.door_2:

                value = "1";

                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                    door_img1.setVisibility(View.GONE);
                    door_img2.setVisibility(View.VISIBLE);
                    door_img2.setImageResource(R.drawable.loft_door);
                    door_img3.setVisibility(View.GONE);
                    door_img4.setVisibility(View.GONE);
                    door_img5.setVisibility(View.GONE);
                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.VISIBLE);
                        door_img2.setImageResource(R.drawable.doorleft);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.VISIBLE);
                        door_img3.setVisibility(View.VISIBLE);
                        door_img2.setImageResource(R.drawable.doorleft);
                        door_img3.setImageResource(R.drawable.doorright);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);


                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.white_door);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");

                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.white_door);
                        linear_door_3.setBackgroundResource(R.drawable.white_door);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);


                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                }
                break;


            case R.id.door_3:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                    door_img1.setVisibility(View.GONE);
                    door_img2.setVisibility(View.GONE);
                    door_img3.setVisibility(View.VISIBLE);
                    door_img3.setImageResource(R.drawable.loft_door);
                    door_img4.setVisibility(View.GONE);
                    door_img5.setVisibility(View.GONE);
                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.VISIBLE);
                        door_img3.setImageResource(R.drawable.doorleft);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.VISIBLE);
                        door_img4.setVisibility(View.VISIBLE);
                        door_img3.setImageResource(R.drawable.doorleft);
                        door_img4.setImageResource(R.drawable.doorright);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.white_door);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);


                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");
                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.white_door);
                        linear_door_4.setBackgroundResource(R.drawable.white_door);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);


                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }
                }
                break;


            case R.id.door_4:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                    door_img1.setVisibility(View.GONE);
                    door_img2.setVisibility(View.GONE);
                    door_img3.setVisibility(View.GONE);
                    door_img4.setVisibility(View.VISIBLE);
                    door_img4.setImageResource(R.drawable.loft_door);
                    door_img5.setVisibility(View.GONE);
                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.VISIBLE);
                        door_img4.setImageResource(R.drawable.doorleft);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,1");

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.VISIBLE);
                        door_img5.setVisibility(View.VISIBLE);
                        door_img4.setImageResource(R.drawable.doorleft);
                        door_img5.setImageResource(R.drawable.doorright);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.white_door);
                        linear_door_5.setBackgroundResource(R.drawable.dash_img);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,1");

                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.white_door);
                        linear_door_5.setBackgroundResource(R.drawable.white_door);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }

                }

                break;


            case R.id.door_5:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,0,1");

                    door_img1.setVisibility(View.GONE);
                    door_img2.setVisibility(View.GONE);
                    door_img3.setVisibility(View.GONE);
                    door_img4.setVisibility(View.GONE);
                    door_img5.setVisibility(View.VISIBLE);
                    door_img5.setImageResource(R.drawable.loft_door);
                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,0,1");

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.VISIBLE);
                        door_img5.setImageResource(R.drawable.doorleft);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,1");

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.VISIBLE);
                        door_img5.setVisibility(View.VISIBLE);
                        door_img4.setImageResource(R.drawable.doorleft);
                        door_img5.setImageResource(R.drawable.doorright);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,0,1");

                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.dash_img);
                        linear_door_5.setBackgroundResource(R.drawable.white_door);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,1");
                        linear_door_1.setBackgroundResource(R.drawable.dash_img);
                        linear_door_2.setBackgroundResource(R.drawable.dash_img);
                        linear_door_3.setBackgroundResource(R.drawable.dash_img);
                        linear_door_4.setBackgroundResource(R.drawable.white_door);
                        linear_door_5.setBackgroundResource(R.drawable.white_door);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);
                        door_img5.setVisibility(View.GONE);

                    }

                }
                break;

            case R.id.door_11:
                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                    door_img11.setImageResource(R.drawable.loft_door);
                    door_img11.setVisibility(View.VISIBLE);
                    door_img12.setVisibility(View.GONE);
                    door_img13.setVisibility(View.GONE);
                    door_img14.setVisibility(View.GONE);

                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        door_img11.setImageResource(R.drawable.doorleft);
                        door_img11.setVisibility(View.VISIBLE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0,0");
                        door_img11.setImageResource(R.drawable.doorleft);
                        door_img12.setImageResource(R.drawable.doorright);
                        door_img11.setVisibility(View.VISIBLE);
                        door_img12.setVisibility(View.VISIBLE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        linear_door_11.setBackgroundResource(R.drawable.white_door);
                        linear_door_12.setBackgroundResource(R.drawable.dash_img);
                        linear_door_13.setBackgroundResource(R.drawable.dash_img);
                        linear_door_14.setBackgroundResource(R.drawable.dash_img);


                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0");


                        linear_door_11.setBackgroundResource(R.drawable.white_door);
                        linear_door_12.setBackgroundResource(R.drawable.white_door);
                        linear_door_13.setBackgroundResource(R.drawable.dash_img);
                        linear_door_14.setBackgroundResource(R.drawable.dash_img);


                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                }

                break;
            case R.id.door_12:

                value = "1";

                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                    door_img11.setVisibility(View.GONE);
                    door_img12.setVisibility(View.VISIBLE);
                    door_img12.setImageResource(R.drawable.loft_door);
                    door_img13.setVisibility(View.GONE);
                    door_img14.setVisibility(View.GONE);

                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.VISIBLE);
                        door_img12.setImageResource(R.drawable.doorleft);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.VISIBLE);
                        door_img13.setVisibility(View.VISIBLE);
                        door_img12.setImageResource(R.drawable.doorleft);
                        door_img13.setImageResource(R.drawable.doorright);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);


                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.white_door);
                        linear_door_13.setBackgroundResource(R.drawable.dash_img);
                        linear_door_14.setBackgroundResource(R.drawable.dash_img);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");

                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.white_door);
                        linear_door_13.setBackgroundResource(R.drawable.white_door);
                        linear_door_14.setBackgroundResource(R.drawable.dash_img);


                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                }
                break;

            case R.id.door_13:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                    door_img11.setVisibility(View.GONE);
                    door_img12.setVisibility(View.GONE);
                    door_img13.setVisibility(View.VISIBLE);
                    door_img13.setImageResource(R.drawable.loft_door);
                    door_img14.setVisibility(View.GONE);

                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.VISIBLE);
                        door_img13.setImageResource(R.drawable.doorleft);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.VISIBLE);
                        door_img14.setVisibility(View.VISIBLE);
                        door_img13.setImageResource(R.drawable.doorleft);
                        door_img14.setImageResource(R.drawable.doorright);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.dash_img);
                        linear_door_13.setBackgroundResource(R.drawable.white_door);
                        linear_door_14.setBackgroundResource(R.drawable.dash_img);


                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");
                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.dash_img);
                        linear_door_13.setBackgroundResource(R.drawable.white_door);
                        linear_door_14.setBackgroundResource(R.drawable.white_door);


                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                }
                break;
            case R.id.door_14:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                    door_img11.setVisibility(View.GONE);
                    door_img12.setVisibility(View.GONE);
                    door_img13.setVisibility(View.GONE);
                    door_img14.setVisibility(View.VISIBLE);
                    door_img14.setImageResource(R.drawable.loft_door);

                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.VISIBLE);
                        door_img14.setImageResource(R.drawable.doorleft);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");

                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.VISIBLE);
                        door_img14.setVisibility(View.VISIBLE);
                        door_img13.setImageResource(R.drawable.doorleft);
                        door_img14.setImageResource(R.drawable.doorright);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,0,1,0");
                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.dash_img);
                        linear_door_13.setBackgroundResource(R.drawable.dash_img);
                        linear_door_14.setBackgroundResource(R.drawable.white_door);

                        door_img11.setVisibility(View.GONE);
                        door_img12.setVisibility(View.GONE);
                        door_img13.setVisibility(View.GONE);
                        door_img14.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,1,0");

                        linear_door_11.setBackgroundResource(R.drawable.dash_img);
                        linear_door_12.setBackgroundResource(R.drawable.dash_img);
                        linear_door_13.setBackgroundResource(R.drawable.white_door);
                        linear_door_14.setBackgroundResource(R.drawable.white_door);

                        door_img1.setVisibility(View.GONE);
                        door_img2.setVisibility(View.GONE);
                        door_img3.setVisibility(View.GONE);
                        door_img4.setVisibility(View.GONE);


                    }

                }

                break;


            case R.id.door_21:
                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                    door_img21.setImageResource(R.drawable.loft_door);
                    door_img21.setVisibility(View.VISIBLE);
                    door_img22.setVisibility(View.GONE);
                    door_img23.setVisibility(View.GONE);


                }
                else {

                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        door_img21.setImageResource(R.drawable.doorleft);
                        door_img21.setVisibility(View.VISIBLE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0,0");
                        door_img21.setImageResource(R.drawable.doorleft);
                        door_img22.setImageResource(R.drawable.doorright);
                        door_img21.setVisibility(View.VISIBLE);
                        door_img22.setVisibility(View.VISIBLE);
                        door_img23.setVisibility(View.GONE);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,0,0,0,0");
                        linear_door_21.setBackgroundResource(R.drawable.white_door);
                        linear_door_22.setBackgroundResource(R.drawable.dash_img);
                        linear_door_23.setBackgroundResource(R.drawable.dash_img);


                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "1,1,0,0,0");


                        linear_door_21.setBackgroundResource(R.drawable.white_door);
                        linear_door_22.setBackgroundResource(R.drawable.white_door);
                        linear_door_23.setBackgroundResource(R.drawable.dash_img);


                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);

                    }
                }

                break;
            case R.id.door_22:

                value = "1";

                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                    door_img11.setVisibility(View.GONE);
                    door_img12.setVisibility(View.VISIBLE);
                    door_img12.setImageResource(R.drawable.loft_door);
                    door_img13.setVisibility(View.GONE);
                    door_img14.setVisibility(View.GONE);

                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.VISIBLE);
                        door_img22.setImageResource(R.drawable.doorleft);
                        door_img23.setVisibility(View.GONE);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");
                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.VISIBLE);
                        door_img23.setVisibility(View.VISIBLE);
                        door_img22.setImageResource(R.drawable.doorleft);
                        door_img23.setImageResource(R.drawable.doorright);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,0,0,0");
                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);



                        linear_door_21.setBackgroundResource(R.drawable.dash_img);
                        linear_door_22.setBackgroundResource(R.drawable.white_door);
                        linear_door_23.setBackgroundResource(R.drawable.dash_img);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");

                        linear_door_21.setBackgroundResource(R.drawable.dash_img);
                        linear_door_22.setBackgroundResource(R.drawable.white_door);
                        linear_door_23.setBackgroundResource(R.drawable.white_door);



                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);

                    }
                }
                break;

            case R.id.door_23:

                value = "1";
                if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                    PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                    door_img11.setVisibility(View.GONE);
                    door_img12.setVisibility(View.GONE);
                    door_img13.setVisibility(View.VISIBLE);
                    door_img13.setImageResource(R.drawable.loft_door);
                    door_img14.setVisibility(View.GONE);

                }
                else {
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.VISIBLE);
                        door_img23.setImageResource(R.drawable.doorleft);

                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_door))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");
                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.VISIBLE);
                        door_img23.setVisibility(View.VISIBLE);
                        door_img22.setImageResource(R.drawable.doorleft);
                        door_img23.setImageResource(R.drawable.doorright);
                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.single_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,0,1,0,0");
                        linear_door_21.setBackgroundResource(R.drawable.dash_img);
                        linear_door_22.setBackgroundResource(R.drawable.dash_img);
                        linear_door_23.setBackgroundResource(R.drawable.white_door);


                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);


                    }
                    if (PreferenceManger.getPreferenceManger().getString(PrefKeys.DoorSelection).equals(getResources().getString(R.string.double_garage))) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.Storage_doors, "0,1,1,0,0");
                        linear_door_21.setBackgroundResource(R.drawable.dash_img);
                        linear_door_22.setBackgroundResource(R.drawable.dash_img);
                        linear_door_23.setBackgroundResource(R.drawable.white_door);


                        door_img21.setVisibility(View.GONE);
                        door_img22.setVisibility(View.GONE);
                        door_img23.setVisibility(View.GONE);

                    }
                }

                break;

        }
    }


}



