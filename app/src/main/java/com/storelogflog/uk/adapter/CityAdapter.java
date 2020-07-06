package com.storelogflog.uk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.bean.RegionBean;
import com.storelogflog.uk.bean.cityBean.City;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CityAdapter extends BaseAdapter {

    Context context;
    List<City> cityList;

    public CityAdapter(List<City> cityList, Context context) {

        this.context = context;
        this.cityList=cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
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
        txtRegionName.setText(cityList.get(i).getName());

        return view;
    }
}

