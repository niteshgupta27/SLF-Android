package com.storelogflog.uk.adapter;

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
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.activeListBean.ActiveAuction;
import com.storelogflog.uk.bean.itemListBean.Item;
import com.storelogflog.uk.fragment.AddAuctionItemFragment;
import com.storelogflog.uk.fragment.ItemDetailsFragment;
import com.storelogflog.uk.apputil.Common;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListHolder> implements Filterable {

    FragmentActivity activity;
    Fragment fragment;
    Bundle bundle;
    List<Item>itemList;
    List<Item>itemListFiltered;

    public ItemListAdapter(FragmentActivity activity,List<Item>itemList) {
        this.activity = activity;
        this.itemList=itemList;
        this.itemListFiltered=itemList;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_storage,parent,false);
        return new ItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListHolder holder, int position) {

        final Item item=itemListFiltered.get(position);
        holder.txtItemName.setText(""+item.getName());
        holder.txtDescription.setText(""+item.getDesp());
        holder.rlTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new ItemDetailsFragment();
                 bundle=new Bundle();
                bundle.putSerializable("item",item);
                fragment.setArguments(bundle);
                Common.loadFragment(activity,fragment,true,null);

            }
        });

        Utility.loadImage(item.getImage(),holder.imgItem);

        holder.imgAddAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new AddAuctionItemFragment();
                bundle=new Bundle();
                bundle.putSerializable("item",item);
                fragment.setArguments(bundle);
                Common.loadFragment(activity,fragment,true,null);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();
    }

    public class ItemListHolder extends RecyclerView.ViewHolder
    {
         private RelativeLayout rlTop;
         private AppCompatTextView txtItemName;
         private AppCompatTextView txtDescription;
         private AppCompatImageView imgAddAuction;
         private CircleImageView imgItem;

        public ItemListHolder(@NonNull View itemView) {
            super(itemView);

            rlTop =itemView.findViewById(R.id.rl_top);
            txtItemName =itemView.findViewById(R.id.txt_item_name);
            txtDescription =itemView.findViewById(R.id.txt_description);
            imgAddAuction =itemView.findViewById(R.id.img_hammer);
            imgItem =itemView.findViewById(R.id.img_item);
        }
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    itemListFiltered = itemList;
                } else {
                    ArrayList<Item> filteredItemList = new ArrayList<>();


                    for (Item model : itemList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredItemList.add(model);
                        }
                    }
                    itemListFiltered = filteredItemList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                itemListFiltered = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
