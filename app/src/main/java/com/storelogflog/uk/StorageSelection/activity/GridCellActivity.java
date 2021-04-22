package com.storelogflog.uk.StorageSelection.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.SelectedCellModel;
import com.storelogflog.uk.StorageSelection.model.SelectedGridModel;
import com.storelogflog.uk.activity.BaseActivity;
import com.storelogflog.uk.apputil.ExpandableHeightGridView;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridCellActivity extends BaseActivity {
    private static final String TAG = "GridCellActivity";
    Context mContext;
    AppCompatTextView txt_toolbar_title;
    LinearLayout toolbar_back;
    ExpandableHeightGridView Grid_View;
    GridCellAdapter adapter;
    TextView reset_btn, next_btn;
    ArrayList<SelectedCellModel> GridCell_array = new ArrayList<SelectedCellModel>();
    ArrayList<SelectedCellModel> SelectedGridCell_array = new ArrayList<SelectedCellModel>();
    int MaxValue;
    String numberofColumn = "";
    String[] grid_cell = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35"/*, "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50"*/};
    ArrayList<SelectedGridModel> CheckedCell = new ArrayList<>();

    public static Integer findMax(List<Integer> list) {

        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Integer.MIN_VALUE;
        }

        // create a new list to avoid modification
        // in the original list
        List<Integer> sortedlist = new ArrayList<>(list);

        // sort list in natural order
        Collections.sort(sortedlist);

        // last element in the sorted list would be maximum
        return sortedlist.get(sortedlist.size() - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_cell);

        mContext = this;

        initViews();
        initListeners();

    }

    @Override
    public void initViews() {
        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        toolbar_back = findViewById(R.id.toolbar_back);
        Grid_View = findViewById(R.id.Grid_View);
        reset_btn = findViewById(R.id.reset_btn);
        next_btn = findViewById(R.id.next_btn);

        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.garage))) {
            txt_toolbar_title.setText("Garage Shape Selection");

        } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.shed))) {
            txt_toolbar_title.setText("Shed Shape Selection");

        } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
            txt_toolbar_title.setText("Loft Shape Selection");

        } else if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.other))) {
            txt_toolbar_title.setText("Other Shape Selection");

        }


        adapter = new GridCellAdapter(mContext, getCell());
        Grid_View.setExpanded(true);
        Grid_View.setAdapter(adapter);

    }

    @Override
    public void initListeners() {
        toolbar_back.setOnClickListener(this);
        reset_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    private ArrayList<SelectedCellModel> getCell() {
        GridCell_array = new ArrayList<SelectedCellModel>();

        for (int i = 0; i < grid_cell.length; i++) {
            SelectedCellModel selectedCellModel = new SelectedCellModel(grid_cell[i]);

            GridCell_array.add(selectedCellModel);

        }


        return GridCell_array;

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

            case R.id.reset_btn:
                SelectedGridCell_array.clear();
                adapter = null;
                adapter = new GridCellAdapter(mContext, getCell());
                Grid_View.setAdapter(adapter);
                reset_btn.setBackgroundResource(R.drawable.green_border_light);
                next_btn.setBackgroundColor(mContext.getResources().getColor(R.color.light_greeen));
                adapter.notifyDataSetChanged();
                break;


            case R.id.next_btn:
                ArrayList<Integer> selectedCell = new ArrayList<>();
                CheckedCell = new ArrayList<SelectedGridModel>();
                ArrayList<SelectedGridModel> CheckedCell4 = new ArrayList<SelectedGridModel>();
                ArrayList<SelectedGridModel> CheckedCell3 = new ArrayList<SelectedGridModel>();


                for (int i = 0; i < SelectedGridCell_array.size(); i++) {

                    int value = Integer.parseInt(SelectedGridCell_array.get(i).getGridcell());
                    selectedCell.add(value);

                }

                for (int j = 0; j < selectedCell.size(); j++) {
                    MaxValue = findMax(selectedCell);
                    Log.e("MaxValue", String.valueOf(MaxValue));
                }
                for (int k = 0; k <= GridCell_array.size(); k++) {

                    if (k <= MaxValue - 1) {
                        SelectedGridModel selectedGridModel = new SelectedGridModel();
                        selectedGridModel.setChecked(GridCell_array.get(k).isChecked());
                        selectedGridModel.setGridcell(GridCell_array.get(k).getGridcell());
                        CheckedCell.add(selectedGridModel);

                        if (String.valueOf(GridCell_array.get(4).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(9).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(14).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(19).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(24).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(29).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(34).isChecked()).equals("true")) {

                            Log.e(TAG, "numberofColumn==========>5");
                            numberofColumn = "5";
                        } else if (String.valueOf(GridCell_array.get(3).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(8).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(13).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(18).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(23).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(28).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(33).isChecked()).equals("true")) {
                            numberofColumn = "4";
                            Log.e(TAG, "numberofColumn==========>4");
                        } else if (String.valueOf(GridCell_array.get(2).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(7).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(12).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(17).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(22).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(27).isChecked()).equals("true") ||
                                String.valueOf(GridCell_array.get(32).isChecked()).equals("true")) {
                            numberofColumn = "3";
                            Log.e(TAG, "numberofColumn==========>3");

                        }
                    }

                }


                if (numberofColumn.equals("5")) {
                    if (CheckedCell.size() > 0) {
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(CheckedCell);
                        editor.putString(PrefKeys.GridCell, json);

                        String json1 = gson.toJson(CheckedCell);
                        editor.putString(PrefKeys.GridCell_column, json1);
                        editor.putString(PrefKeys.numberofColumn, numberofColumn);
                        editor.commit();
                        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                            PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection, getResources().getString(R.string.single_door));
                            Intent intent = new Intent(mContext, ConfirmationActivity.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(mContext, DoorSelectionActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        showToast("Please select your storage shape first!");
                    }

                } else if (numberofColumn.equals("4")) {
                    for (int l = 0; l < CheckedCell.size(); l++) {

                        if (l == 4 || l == 9 || l == 14 || l == 19 || l == 24 || l == 29 || l == 34) {

                        } else {
                            SelectedGridModel selectedGridModel = new SelectedGridModel();
                            selectedGridModel.setChecked(GridCell_array.get(l).isChecked());
                            selectedGridModel.setGridcell(GridCell_array.get(l).getGridcell());
                            CheckedCell4.add(selectedGridModel);
                        }
                    }
                    if (CheckedCell4.size() > 0) {
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(CheckedCell);
                        editor.putString(PrefKeys.GridCell, json);

                        String json1 = gson.toJson(CheckedCell4);
                        editor.putString(PrefKeys.GridCell_column, json1);
                        editor.putString(PrefKeys.numberofColumn, numberofColumn);
                        editor.commit();
                        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                            PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection, getResources().getString(R.string.single_door));
                            Intent intent = new Intent(mContext, ConfirmationActivity.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(mContext, DoorSelectionActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        showToast("Please select your storage shape first!");
                    }

                } else if (numberofColumn.equals("3")) {
                    for (int l = 0; l < CheckedCell.size(); l++) {

                        if (l == 3 || l == 8 || l == 13 || l == 18 || l == 23 || l == 28 || l == 33 ||
                                l == 4 || l == 9 || l == 14 || l == 19 || l == 24 || l == 29 || l == 34) {
                            Log.e("GridCell_array===>", String.valueOf(GridCell_array.get(l).isChecked()) + "======>" + GridCell_array.get(l).getGridcell());

                        } else {
                            Log.e("GridCell_array===>", String.valueOf(GridCell_array.get(l).isChecked()) + "======>" + GridCell_array.get(l).getGridcell());

                            SelectedGridModel selectedGridModel = new SelectedGridModel();
                            selectedGridModel.setChecked(GridCell_array.get(l).isChecked());
                            selectedGridModel.setGridcell(GridCell_array.get(l).getGridcell());
                            CheckedCell3.add(selectedGridModel);
                        }
                    }


                    if (CheckedCell3.size() > 0) {
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(CheckedCell);
                        editor.putString(PrefKeys.GridCell, json);

                        String json1 = gson.toJson(CheckedCell3);
                        editor.putString(PrefKeys.GridCell_column, json1);
                        editor.putString(PrefKeys.numberofColumn, numberofColumn);
                        editor.commit();
                        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.StorageType).equals(getResources().getString(R.string.loft))) {
                            PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection, getResources().getString(R.string.single_door));
                            Intent intent = new Intent(mContext, ConfirmationActivity.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(mContext, DoorSelectionActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        showToast("Please select your storage shape first!");
                    }

                }


                break;
        }
    }


    public class GridCellAdapter extends BaseAdapter {

        private final String TAG = GridCellAdapter.class.getSimpleName();
        Context mContext;
        ArrayList<SelectedCellModel> playersArrayList;
        LayoutInflater inflater;
        String value = "";
        private ViewHolder holder;

        public GridCellAdapter(Context context, ArrayList<SelectedCellModel> arrayList) {
            mContext = context;
            playersArrayList = arrayList;
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
                convertView = inflater.inflate(R.layout.grid_cell_item, null);
                holder = new ViewHolder();

                holder.item_layout = convertView.findViewById(R.id.garage_relative);
                holder.info_text = convertView.findViewById(R.id.info_text);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            //finalConvertView = convertView;
            holder.item_layout.setOnClickListener(new MainItemClick(position, holder));
            return convertView;
        }

        public class ViewHolder {
            RelativeLayout item_layout;
            TextView info_text;
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

                if (position == 0) {
                    int id = Integer.parseInt(playersArrayList.get(position).getGridcell());
                    for (int i = 0; i < GridCell_array.size(); i++) {
                        if (Integer.parseInt(GridCell_array.get(i).getGridcell()) == id) {
                            if (GridCell_array.get(i).isChecked()) {
                                GridCell_array.get(i).setChecked(false);
                                playersArrayList.get(i).setChecked(false);
                                SelectedGridCell_array.remove(GridCell_array.get(i));
                                viewHolder.item_layout.setBackgroundResource(R.drawable.background_edit_square);
                            } else {
                                GridCell_array.get(i).setChecked(true);
                                playersArrayList.get(i).setChecked(true);
                                SelectedGridCell_array.add(GridCell_array.get(i));
                                viewHolder.item_layout.setBackgroundResource(R.drawable.green_back);
                            }
                        }
                    }
                } else if (playersArrayList.get(0).isChecked() == false) {
                    Toast.makeText(mContext, "Please select from top first row", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(playersArrayList.get(position).getGridcell());
                    for (int i = 0; i < GridCell_array.size(); i++) {
                        if (Integer.parseInt(GridCell_array.get(i).getGridcell()) == id) {
                            if (GridCell_array.get(i).isChecked()) {
                                GridCell_array.get(i).setChecked(false);
                                playersArrayList.get(i).setChecked(false);
                                SelectedGridCell_array.remove(GridCell_array.get(i));
                                viewHolder.item_layout.setBackgroundResource(R.drawable.background_edit_square);
                            } else {
                                GridCell_array.get(i).setChecked(true);
                                playersArrayList.get(i).setChecked(true);
                                SelectedGridCell_array.add(GridCell_array.get(i));
                                viewHolder.item_layout.setBackgroundResource(R.drawable.green_back);
                            }
                        }
                    }
                }

                if (SelectedGridCell_array.size() > 0) {
                    reset_btn.setBackgroundResource(R.drawable.green_border);
                    next_btn.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                } else {
                    reset_btn.setBackgroundResource(R.drawable.green_border_light);
                    next_btn.setBackgroundColor(mContext.getResources().getColor(R.color.light_greeen));
                }

                notifyDataSetChanged();
            }
        }
    }

}
