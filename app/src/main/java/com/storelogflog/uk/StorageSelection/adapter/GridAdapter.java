package com.storelogflog.uk.StorageSelection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.activity.GridCellActivity;
import com.storelogflog.uk.StorageSelection.model.SelectedCellModel;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final String TAG = GridAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<SelectedCellModel> playersArrayList;
    LayoutInflater inflater;
    String teamType;
    private GridAdapter.ViewHolder holder;

    public GridAdapter(Context context, ArrayList<SelectedCellModel> arrayList) {
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
            holder = new GridAdapter.ViewHolder();

            holder.item_layout = convertView.findViewById(R.id.garage_relative);
            holder.info_text = convertView.findViewById(R.id.info_text);


            convertView.setTag(holder);
        } else {
            holder = (GridAdapter.ViewHolder) convertView.getTag();
        }


        if (playersArrayList.get(position).isChecked()){
            holder.item_layout.setBackgroundResource(R.drawable.green_back);
        }else {
            holder.item_layout.setBackgroundResource(R.drawable.background_edit_square);
        }


        return convertView;
    }

    public class ViewHolder {
        RelativeLayout item_layout;
        TextView info_text;
    }
}
