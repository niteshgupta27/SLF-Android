package com.storelogflog.uk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.searchStorageBean.SearchStorage;


import com.storelogflog.uk.fragment.StorageClaimFragment;

import java.util.ArrayList;
import java.util.List;

public class LocalStorageSearchAdapter extends RecyclerView.Adapter<LocalStorageSearchAdapter.StorageYardSearchHolder>  implements Filterable {

    FragmentActivity activity;
    List<SearchStorage> storageList;
    List<SearchStorage> storageListFiltered;

    public LocalStorageSearchAdapter(FragmentActivity activity,List<SearchStorage> storageList) {
        this.activity = activity;
        this.storageList=storageList;
        this.storageListFiltered=storageList;
    }

    @NonNull
    @Override
    public StorageYardSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_storage_yards_view_more,parent,false);
        return new StorageYardSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageYardSearchHolder holder, int position) {

        final SearchStorage storage=storageListFiltered.get(position);
        holder.txtStorageName.setText(""+storage.getName());
        holder.txtAddress1.setText(""+storage.getAddress1());
        holder.txtAddress2.setText(""+storage.getAddress2());
        holder.txtCity.setText(""+storage.getCity());
        holder.txtDescription.setText(""+storage.getShortDesp());

        Utility.loadImage(activity, storage.getImage(),holder.imgStorage);

        holder.cardViewTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new StorageClaimFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("storage",storage);
                fragment.setArguments(bundle);
                Common.loadFragment(activity,fragment,true,null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return storageListFiltered.size();
    }

    public class StorageYardSearchHolder extends RecyclerView.ViewHolder
    {
        private CardView cardViewTop;
        private AppCompatTextView txtStorageName;
        private AppCompatTextView txtAddress1;
        private AppCompatTextView txtAddress2;
        private AppCompatTextView txtCity;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtViewMore;
        private AppCompatImageView imgStorage;

        public StorageYardSearchHolder(@NonNull View itemView) {
            super(itemView);
            cardViewTop=itemView.findViewById(R.id.cardView);
            txtStorageName=itemView.findViewById(R.id.txt_storage_name);
            txtAddress1=itemView.findViewById(R.id.txt_address1);
            txtAddress2=itemView.findViewById(R.id.txt_address2);
            txtCity=itemView.findViewById(R.id.txt_city);
            txtDescription=itemView.findViewById(R.id.txt_des);
            txtViewMore=itemView.findViewById(R.id.txt_view_more);
            imgStorage=itemView.findViewById(R.id.img_storage);
        }
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    storageListFiltered = storageList;
                } else {
                    ArrayList<SearchStorage> filteredStorageList = new ArrayList<>();


                    for (SearchStorage model : storageList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredStorageList.add(model);
                        }
                    }
                    storageListFiltered = filteredStorageList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = storageListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                storageListFiltered = (ArrayList<SearchStorage>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
