package com.storelogflog.uk.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.model.CardViewModel;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.itemListBean.Item;
import com.storelogflog.uk.fragment.AddAuctionItemFragment;
import com.storelogflog.uk.fragment.ItemDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategryListAdapter extends RecyclerView.Adapter<CategryListAdapter.ItemListHolder>  {

    Context mContext;
    List<CardViewModel.Item.Category>itemList;
    List<CardViewModel.Item.Category>itemListFiltered;

    public CategryListAdapter(Context activity, List<CardViewModel.Item.Category> itemList) {
        this.mContext = activity;
        this.itemList=itemList;
        this.itemListFiltered=itemList;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.category_item,parent,false);
        return new ItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListHolder holder, int position) {

        final CardViewModel.Item.Category item = itemListFiltered.get(position);
        holder.txtItemName.setText(item.getName());


    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();
    }

    public class ItemListHolder extends RecyclerView.ViewHolder
    {

         private AppCompatTextView txtItemName;

        public ItemListHolder(@NonNull View itemView) {
            super(itemView);

            txtItemName =itemView.findViewById(R.id.txt_item_name);

        }
    }



}
