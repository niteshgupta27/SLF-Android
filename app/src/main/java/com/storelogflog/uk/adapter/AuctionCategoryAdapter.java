package com.storelogflog.uk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.bean.AuctionCategoryModel;
import com.storelogflog.uk.bean.countryBean.Country;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AuctionCategoryAdapter extends BaseAdapter {

    Context context;
    List<AuctionCategoryModel.Auction> categoryModelList;

    public AuctionCategoryAdapter( Context context,List<AuctionCategoryModel.Auction> countryList) {

        this.context = context;
        this.categoryModelList=countryList;
    }

    @Override
    public int getCount() {
        return categoryModelList.size();
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

        view = layoutInflater.inflate(R.layout.category_item, null);

        TextView txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_item_name.setText(categoryModelList.get(i).getAuctionCategoryName());

        return view;
    }
}

