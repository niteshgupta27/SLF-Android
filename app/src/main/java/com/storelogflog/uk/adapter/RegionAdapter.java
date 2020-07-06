package com.storelogflog.uk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.bean.RegionBean;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RegionAdapter extends BaseAdapter {

    Context context;
    List<String> reasonList;

    public RegionAdapter(ArrayList<String> reasonList, Context context) {

        this.context = context;
        this.reasonList=reasonList;
    }

    @Override
    public int getCount() {
        return reasonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.item_region, null);

        TextView txtRegionName = (TextView) view.findViewById(R.id.txt_region_name);
        txtRegionName.setText(reasonList.get(i));

        return view;
    }
}

