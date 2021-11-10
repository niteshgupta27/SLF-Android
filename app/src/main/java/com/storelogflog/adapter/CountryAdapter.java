package com.storelogflog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storelogflog.R;
import com.storelogflog.bean.countryBean.Country;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CountryAdapter extends BaseAdapter {

    Context context;
    List<Country> countryList;

    public CountryAdapter(List<Country> countryList, Context context) {

        this.context = context;
        this.countryList=countryList;
    }

    @Override
    public int getCount() {
        return countryList.size();
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
        txtRegionName.setText(countryList.get(i).getName());

        return view;
    }
}

