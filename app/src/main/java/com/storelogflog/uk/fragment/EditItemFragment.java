package com.storelogflog.uk.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.CardViewModel;
import com.storelogflog.uk.StorageSelection.model.StorageShapeModel;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.adapter.CategoryItemAdapter2;
import com.storelogflog.uk.apiCall.AddCategoryApiCall;
import com.storelogflog.uk.apiCall.DeleteItemtApiCall;
import com.storelogflog.uk.apiCall.GetListCategoryApiCall;
import com.storelogflog.uk.apiCall.PricingApiCall;
import com.storelogflog.uk.apiCall.StorageListShapeApiCall;
import com.storelogflog.uk.apiCall.UpdateItemApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.ExpandableHeightGridView;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.categoryBean.CategoryBean;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;
import com.storelogflog.uk.dialog.AddCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EditItemFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {

    public static EditItemFragment instance;
    Context mContext;
    RelativeLayout rlAddItem;
    String[] unitArray;
    String[] Currency_array;
    String unittype = "I";
    String Currency_Type = "£";
    ExpandableHeightGridView Grid_View5, Grid_View4, Grid_View3;
    LinearLayout door_1, door_2, door_3, door_4, door_5,
            linear_door_1, linear_door_2, linear_door_3, linear_door_4, linear_door_5,
            door_11, door_12, door_13, door_14,
            linear_door_11, linear_door_12, linear_door_13, linear_door_14,
            door_21, door_22, door_23,
            linear_door_21, linear_door_22, linear_door_23;
    ImageView door_img1, door_img2, door_img3, door_img4, door_img5,
            door_img11, door_img12, door_img13, door_img14, door_img21, door_img22, door_img23;
    LinearLayout Grid5_linear, Grid4_linear, Grid3_linear;
    GridCellAdapter gridCellAdapter;
    int pondValue;
    int prevSelection = -1;
    private  MenuItem action_edit_menu;
    String SelectedGridShapeID, SelectedRack_ID, Selectedshape_name = "", Shape_id2, RackID_position;
    ArrayList<StorageShapeModel.Storage.ShapsList> Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();
    String[] grid_cell = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "y", "Z",
            "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL", "MM", "NN", "OO",
            "PP", "QQ", "RR", "SS", "TT", "UU", "VV", "WW", "XX", "Yy", "ZZ"};

    ArrayList<String> GridCell_list = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list5 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list4 = new ArrayList<>();
    List<StorageShapeModel.Storage.ShapsList> Shap_list3 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list5 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list4 = new ArrayList<>();
    ArrayList<Integer> Shape_value_list3 = new ArrayList<>();
    GridCellAdapter.CustomRacklistAdapter customracklistadapter;
    int prevSelection_rack = -1;
    private AppCompatEditText editItemName, editItemDescription, editItemValue, editItemQty, editLength,
            editWidth, editHeight;
    private AppCompatTextView txtInch;
    private AppCompatSpinner spUnit, sp_currency;
    private RecyclerView rvCategory;
    private Storage storage1;
    private CardViewModel.Item item;
    private int locationNo = -1;
    private CategoryItemAdapter2 adapter;
    private AppCompatTextView txtAddCategory;
    private String numberofColumn;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {


        hideShow();
        ((DrawerLocker) getActivity()).setDrawerLocked(true);
        mContext = getActivity();
        instance = EditItemFragment.this;
        rlAddItem = view.findViewById(R.id.rl_add);
        editItemName = view.findViewById(R.id.edit_item_name);
        editItemDescription = view.findViewById(R.id.edit_item_description);
        editItemValue = view.findViewById(R.id.edit_item_value);
        editLength = view.findViewById(R.id.edit_length);
        editWidth = view.findViewById(R.id.edit_width);
        editHeight = view.findViewById(R.id.edit_height);
        spUnit = view.findViewById(R.id.sp_unit);
        rvCategory = view.findViewById(R.id.rv_item_category);
        txtAddCategory = view.findViewById(R.id.txt_add_category);
        editItemQty = view.findViewById(R.id.edit_item_qty);


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
        sp_currency = view.findViewById(R.id.sp_currency);

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


        unitArray = getActivity().getResources().getStringArray(R.array.SelectUnit);

        ArrayAdapter<String> adapterUnit = new ArrayAdapter(getActivity(), R.layout.spinner_list_item, unitArray);
        spUnit.setAdapter(adapterUnit);


        Currency_array = getActivity().getResources().getStringArray(R.array.Select_Currency);

        ArrayAdapter<String> AdapterCurrency = new ArrayAdapter(getActivity(), R.layout.spinner_list_item, Currency_array);
        sp_currency.setAdapter(AdapterCurrency);

        callPricingApi();

        if (getArguments() != null) {
            storage1 = (Storage) getArguments().getSerializable("storage");
            item = (CardViewModel.Item) getArguments().getSerializable("item");
            Log.e("Item_ID", String.valueOf(item.getID()));
            Shape_id2 = getArguments().getString("Shape_id2");
            RackID_position = getArguments().getString("RackID_position");


            if (storage1 != null) {
                GetStorageShape(String.valueOf(storage1.getUnitID()));

            }

        }

        ((HomeActivity) getActivity()).enableViews(true, "" + storage1.getName());

        callGetCategoryList();

        editItemName.setText(item.getName());
        editItemDescription.setText(item.getDesp());
        editItemValue.setText(String.valueOf(item.getValue()));
        editItemQty.setText(String.valueOf(item.getQty()));
        editLength.setText(String.valueOf(item.getLength()));
        editWidth.setText(String.valueOf(item.getWidth()));
        editHeight.setText(String.valueOf(item.getHeight()));

        if (item.getUnit().equals("I")) {
            unittype = "I";
            spUnit.setSelection(2);
        } else {
            unittype = "C";
            spUnit.setSelection(1);
        }

        Log.e("getCurrency", String.valueOf(item.getCurrency()));

        if (String.valueOf(item.getCurrency()).equals("£")) {
            Currency_Type = "£";
            sp_currency.setSelection(1);
        } else if (String.valueOf(item.getCurrency()).equals("$")) {
            Currency_Type = "$";
            sp_currency.setSelection(2);
        } else if (String.valueOf(item.getCurrency()).equals("€")) {
            Currency_Type = "€";
            sp_currency.setSelection(3);
        }

    }


    @Override
    public void initListeners() {

        rlAddItem.setOnClickListener(this);
        txtAddCategory.setOnClickListener(this);

        HomeActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackClick();
            }
        });

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    unittype = "C";
                } else if (i == 2) {
                    unittype = "I";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    Currency_Type = "£";
                } else if (i == 2) {
                    Currency_Type = "$";
                } else if (i == 3) {
                    Currency_Type = "€";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void hideShow() {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);
        HomeActivity.imgBack.setVisibility(View.VISIBLE);
        HomeActivity.imgSearch.setVisibility(View.GONE);
        HomeActivity.imgMenu.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_add:
                callUpdateItem();
                break;

            case R.id.txt_add_category:
                new AddCategory(getActivity()) {
                    @Override
                    public void onClickSubmit(String categoryName) {

                        callAddCategory(categoryName);

                    }
                };
                break;

        }
    }

    public boolean isValidate() {
        if (editItemName.getText().toString().isEmpty()) {
            return showErrorMsg(editItemName, "Item name can't be blank");
        } else if (editItemDescription.getText().toString().isEmpty()) {
            return showErrorMsg(editItemDescription, "Description can't be blank");
        } else if (editItemValue.getText().toString().isEmpty()) {
            return showErrorMsg(editItemValue, "Item value can't be blank");
        } else if (editItemQty.getText().toString().isEmpty()) {
            return showErrorMsg(editItemQty, "Item Qty can't be blank");
        } else if (editLength.getText().toString().isEmpty()) {
            return showErrorMsg(editLength, "Item length can't be blank");

        } else if (editWidth.getText().toString().isEmpty()) {
            return showErrorMsg(editWidth, "Item width can't be blank");

        } else if (editHeight.getText().toString().isEmpty()) {
            return showErrorMsg(editHeight, "Item height can't be blank");

        } else if (adapter != null && adapter.getSelectedCatId().size() == 0) {
            showToast(getActivity(), "Please add and select category first");
            return false;
        } else if (TextUtils.isEmpty(SelectedGridShapeID)) {
            showToast(getActivity(), "Please select item location");
            return false;

        } else {
            return true;
        }

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


    void callUpdateItem() {
        if (Utility.isInternetConnected(getActivity())) {

            if (isValidate()) {
                try {

                    if (storage1 != null) {
                        JSONObject jsonObjectPayload = new JSONObject();
                        jsonObjectPayload.put("storage", "" + storage1.getID());
                        jsonObjectPayload.put("unit", storage1.getUnitID());
                        jsonObjectPayload.put("name", editItemName.getText().toString());
                        jsonObjectPayload.put("description", editItemDescription.getText().toString());
                        jsonObjectPayload.put("length", editLength.getText().toString());
                        jsonObjectPayload.put("width", "" + editWidth.getText().toString());
                        jsonObjectPayload.put("height", "" + editHeight.getText().toString());
                        jsonObjectPayload.put("qty", editItemQty.getText().toString());
                        jsonObjectPayload.put("value", "" + editItemValue.getText().toString());
                        jsonObjectPayload.put("unittype", unittype);
                        jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                        jsonObjectPayload.put("category", getJsonCat(adapter.getSelectedCatId()));
                        jsonObjectPayload.put("shape_id", SelectedGridShapeID);
                        jsonObjectPayload.put("rack_id", SelectedRack_ID);
                        jsonObjectPayload.put("item_id", item.getID());
                        jsonObjectPayload.put("currency", Currency_Type);


                        Log.e("jsonObjectPayload", jsonObjectPayload.toString());
                        Logger.debug(TAG, jsonObjectPayload.toString());
                        String token = Utility.getJwtToken(jsonObjectPayload.toString());
                        showLoading("Loading...");
                        new UpdateItemApiCall(getActivity(), this, token, Constants.Update_Item_API);


                    } else {
                        showToast(getActivity(), "Some thing went wrong");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else {
            showToast(getActivity(), "No Internet Connection");
        }
    }

    String getJsonCat(ArrayList<String> catArrayList) {
        JSONArray jsonArray = null;
        if (catArrayList != null && catArrayList.size() > 0) {
            try {

                jsonArray = new JSONArray();
                for (int i = 0; i < catArrayList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", catArrayList.get(i));
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    void callAddCategory(String catName) {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("name", catName);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new AddCategoryApiCall(getActivity(), this, token, Constants.ADD_CATEGORY_CODE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            showToast(getActivity(), "No Internet Connection");
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

    public void callGetCategoryList() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                //  showLoading("Loading...");
                new GetListCategoryApiCall(getActivity(), this, token, Constants.LIST_CATEGORY_CODE);

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
            case Constants.Update_Item_API:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("EditItemFragment", jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");

                            if (result == 1) {


                                Fragment fragment = new PhotoFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("storage", storage1);
                                bundle.putString("sotrageId", String.valueOf(storage1.getID()));
                                bundle.putString("itemId", String.valueOf(getIntFromJsonObj(jsonObject, "ItemId")));
                                bundle.putString("position", getArguments().getString("position"));
                                fragment.setArguments(bundle);
                                Common.loadFragment(getActivity(), fragment, false, null);

                            } else {
                                //   showToast(getActivity(), message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.LIST_CATEGORY_CODE:
                //  hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                CategoryBean categoryBean = new Gson().fromJson(response.toString(), CategoryBean.class);
                                if (categoryBean != null && categoryBean.getCategories() != null && categoryBean.getCategories().size() > 0) {
                                    adapter = new CategoryItemAdapter2(getActivity(), categoryBean.getCategories(), item.getCategory());
                                    rvCategory.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                                    rvCategory.setAdapter(adapter);
                                    rvCategory.setVisibility(View.VISIBLE);
                                } else {
                                    rvCategory.setVisibility(View.GONE);
                                }

                            } else {
                                rvCategory.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            rvCategory.setVisibility(View.GONE);
                        }
                    }
                }
                break;

            case Constants.ADD_CATEGORY_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String msg = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                showToast(getActivity(), msg);
                                callGetCategoryList();

                            } else {
                                showToast(getActivity(), msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

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
                                        Shape_name = new ArrayList<StorageShapeModel.Storage.ShapsList>();


                                        if (storageShapeModel.getStorage().getShapsList().size() > 0) {
                                            Shape_name = new ArrayList<>();
                                            Shap_list5 = new ArrayList<>();
                                            Shap_list4 = new ArrayList<>();
                                            Shap_list3 = new ArrayList<>();
                                            Shape_value_list5 = new ArrayList<>();
                                            Shape_value_list4 = new ArrayList<>();
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
                                            } else if (numberofColumn.equals("4")) {
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
                                            } else if (numberofColumn.equals("3")) {
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

                                        Log.e("Shap_list5======>", String.valueOf(Shap_list5.size()));

                                        if (numberofColumn.equals("5")) {
                                            Grid4_linear.setVisibility(View.GONE);
                                            Grid3_linear.setVisibility(View.GONE);
                                            Grid5_linear.setVisibility(View.VISIBLE);

                                            gridCellAdapter = new GridCellAdapter(mContext, Shap_list5, Shape_name);
                                            Grid_View5.setExpanded(true);
                                            Grid_View5.setNumColumns(5);
                                            Grid_View5.setAdapter(gridCellAdapter);
                                            gridCellAdapter.notifyDataSetChanged();

                                        } else if (numberofColumn.equals("4")) {
                                            Grid4_linear.setVisibility(View.VISIBLE);
                                            Grid3_linear.setVisibility(View.GONE);
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
                                        } else if (numberofColumn.equals("4")) {
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

                                        } else if (numberofColumn.equals("3")) {
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
                                }
                            }


                        } catch (Exception e) {
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


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case Constants.DELETE_ITEM_CODE:
                hideLoading();

                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            String message = getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {
                                showToast(getActivity(), message);
                                Fragment fragment = new LogFragment();
                              Bundle bundle = new Bundle();
                                bundle.putSerializable("storage", storage1);
                                fragment.setArguments(bundle);
                                Common.loadFragment(getActivity(), fragment, false, null);

                            } else {
                                showToast(getActivity(), message);
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
            case Constants.ADD_ITEM_CODE:
                hideLoading();
                break;
            case Constants.LIST_CATEGORY_CODE:
                hideLoading();
                break;
            case Constants.ADD_CATEGORY_CODE:
                hideLoading();
                break;

            case Constants.StorageListShape:
                hideLoading();
                break;

            case Constants.PRICING_CODE:
                hideLoading();
                break;

            case Constants.DELETE_ITEM_CODE:
                hideLoading();
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

        getActivity().getMenuInflater().inflate(R.menu.edit_menu,menu);

        action_edit_menu = menu.findItem(R.id.action_edit_menu);

        action_edit_menu.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.action_edit_menu:
                SelectItem();
                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }
    private void SelectItem() {

        LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.edit_cam,
                null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);


        CardView Take_Photo = layout.findViewById(R.id.Take_Photo);
        CardView Gallary = layout.findViewById(R.id.Gallary);
        CardView cancle = layout.findViewById(R.id.cancle);
        TextView Take_Photo_txt = layout.findViewById(R.id.Take_Photo_txt);
        TextView Choose_from_Library_txt = layout.findViewById(R.id.Choose_from_Library_txt);

        Take_Photo_txt.setText("Edit Item");
        Choose_from_Library_txt.setText("Delete Item");

        Take_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new PhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage1);
                bundle.putString("sotrageId", String.valueOf(storage1.getID()));
                bundle.putString("itemId", String.valueOf(item.getID()));
                bundle.putString("position", getArguments().getString("position"));

                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
                alertDialog.dismiss();
            }
        });

        Gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDeleteItemApi();
                alertDialog.dismiss();
            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }



    void callDeleteItemApi() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("item", item.getID());

                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new DeleteItemtApiCall(getContext(), this, token, Constants.DELETE_ITEM_CODE);
                showLoading("Loading...");

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
                    holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()) + "-" + String.valueOf(prevSelection_rack + 1));
                } else {
                    holder.item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.diactive_grid));
                }
            } else {

                holder.info_text.setText(String.valueOf(grid_cell_name.get(position).getShape_name()));

            }


            if (playersArrayList.get(position).getRackList().size() > 0) {
                holder.rack_list_avalablity.setVisibility(View.VISIBLE);
                if (!Shape_id2.equals("")) {
                    if (String.valueOf(playersArrayList.get(position).getShapID()).equals(String.valueOf(Shape_id2))) {
                        holder.item_layout.setBackgroundResource(R.drawable.green_back);
                        holder.info_text.setText(grid_cell_name.get(position).getShape_name() + " - " + RackID_position);
                        playersArrayList.get(position).setChecked(true);

                        prevSelection = position;

                        SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                        int pos = Integer.parseInt(RackID_position);
                        if (playersArrayList.get(position).getRackList().size() > 0) {
                            SelectedRack_ID = String.valueOf(playersArrayList.get(position).getRackList().get(pos).getFldRacksId());
                        } else {
                            SelectedRack_ID = "0";
                        }
                    }
                }
            } else {
                if (!Shape_id2.equals("")) {
                    if (String.valueOf(playersArrayList.get(position).getShapID()).equals(String.valueOf(Shape_id2))) {
                        holder.item_layout.setBackgroundResource(R.drawable.green_back);
                        holder.info_text.setText(grid_cell_name.get(position).getShape_name());
                        playersArrayList.get(position).setChecked(true);

                        prevSelection = position;
                        SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                        SelectedRack_ID = "0";
                    }
                }
                holder.rack_list_avalablity.setVisibility(View.GONE);
            }

            //finalConvertView = convertView;
            holder.item_layout.setOnClickListener(new MainItemClick(position, holder));
            return convertView;
        }

        private void ShowRack_dialog(int position, ViewHolder holder, String rack_id_position) {
            Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogue_select_shelft);
            dialog.getWindow().setBackgroundDrawableResource(R.color.black);

            ListView Rack_list = dialog.findViewById(R.id.Rack_list);
            LinearLayout toolbar_back = dialog.findViewById(R.id.toolbar_back);
            TextView proceed_btn = dialog.findViewById(R.id.proceed_btn);


            customracklistadapter = new CustomRacklistAdapter(mContext, playersArrayList.get(position).getRackList(), rack_id_position);
            Rack_list.setAdapter(customracklistadapter);
            customracklistadapter.notifyDataSetChanged();
            dialog.show();

            toolbar_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Selectedshape_name = grid_cell_name.get(position).getShape_name();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });


            proceed_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Selectedshape_name = grid_cell_name.get(position).getShape_name();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });


            // notifyDataSetChanged();
        }

        public class ViewHolder {
            RelativeLayout item_layout;
            TextView info_text;
            View rack_list_avalablity;
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

                Selectedshape_name = "";
                if (!grid_cell_name.get(position).getShape_name().equals("")) {
                    String name = String.valueOf(playersArrayList.get(position).getShapID());
                    if (prevSelection == -1) //nothing selected
                    {
                        playersArrayList.get(position).setChecked(true);

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
                            prevSelection = position;
                            SelectedGridShapeID = String.valueOf(playersArrayList.get(position).getShapID());
                            if (playersArrayList.get(position).getRackList().size() > 0) {
                                SelectedRack_ID = String.valueOf(playersArrayList.get(position).getRackList().get(0).getFldRacksId());
                            } else {
                                SelectedRack_ID = "0";
                            }
                        }

                    }
                    if (!Shape_id2.equals("")) {
                        if (String.valueOf(playersArrayList.get(position).getShapID()).equals(String.valueOf(Shape_id2))) {
                            if (playersArrayList.get(position).getRackList().size() > 0) {

                                ShowRack_dialog(prevSelection, holder, RackID_position);
                            }
                        } else {
                            if (playersArrayList.get(position).getRackList().size() > 0) {

                                ShowRack_dialog(prevSelection, holder, "");
                            }
                        }
                    } else {
                        if (playersArrayList.get(position).getRackList().size() > 0) {

                            ShowRack_dialog(prevSelection, holder, "");
                        }

                    }
                    Shape_id2 = "";

                    Log.e("SelectedGridRackID", String.valueOf(SelectedRack_ID));
                    Log.e("SelectedGridLocation", String.valueOf(SelectedGridShapeID));

                    notifyDataSetChanged();
                }
            }
        }

        private class CustomRacklistAdapter extends BaseAdapter {

            private final String TAG = CustomRacklistAdapter.class.getSimpleName();
            Context mContext;
            List<StorageShapeModel.Storage.ShapsList.RackList> CustomRackList;

            LayoutInflater inflater;
            String rack_id_position;
            private CustomRacklistAdapter.ViewHolder holder;


            public CustomRacklistAdapter(Context mContext, List<StorageShapeModel.Storage.ShapsList.RackList> rackList, String rack_id_position) {
                this.mContext = mContext;
                CustomRackList = rackList;
                this.rack_id_position = rack_id_position;
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
                    holder = new CustomRacklistAdapter.ViewHolder();

                    holder.rack_relative = convertView.findViewById(R.id.rack_relative);
                    holder.sr_no_txt = convertView.findViewById(R.id.sr_no_txt);
                    holder.rack = convertView.findViewById(R.id.rack);
                    convertView.setTag(holder);
                } else {
                    holder = (CustomRacklistAdapter.ViewHolder) convertView.getTag();
                }

                holder.sr_no_txt.setText(String.valueOf(position + 1));

                if (!rack_id_position.equals("")) {
                    if (String.valueOf(position + 1).equals(rack_id_position)) {
                        holder.rack.setBackgroundColor(Color.WHITE);
                        SelectedRack_ID = String.valueOf(CustomRackList.get(position).getFldRacksId());
                        CustomRackList.get(position).setCheckRack(true);
                        prevSelection_rack = position;

                    } else {
                        holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                    }
                }
                if (CustomRackList.get(position).isCheckRack()) {
                    holder.rack.setBackgroundColor(Color.WHITE);

                } else {
                    holder.rack.setBackgroundColor(Color.parseColor("#41464D"));
                }


                holder.rack_relative.setOnClickListener(new CustomRacklistAdapter.RackClick(position, holder));

                //finalConvertView = convertView;
                return convertView;
            }

            public class ViewHolder {
                RelativeLayout rack_relative, rack;
                TextView sr_no_txt;

            }

            class RackClick implements View.OnClickListener {

                int position;
                CustomRacklistAdapter.ViewHolder viewHolder;

                RackClick(int pos, CustomRacklistAdapter.ViewHolder holder) {
                    position = pos;
                    viewHolder = holder;
                }

                @Override
                public void onClick(View view) {

                    rack_id_position = "";
                    if (prevSelection_rack == -1) //nothing selected
                    {
                        CustomRackList.get(position).setCheckRack(true);
                        customracklistadapter.notifyDataSetChanged();
                        prevSelection_rack = position;
                        viewHolder.rack.setBackgroundColor(Color.WHITE);
                        SelectedRack_ID = String.valueOf(CustomRackList.get(position).getFldRacksId());


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

                        }

                    }
                    Log.e("SelectedRack_ID", String.valueOf(CustomRackList.get(position).getFldRacksId()));
                    Log.e("SelectedRack_Position", String.valueOf(position));


                    notifyDataSetChanged();
                }
            }

        }
    }
}

