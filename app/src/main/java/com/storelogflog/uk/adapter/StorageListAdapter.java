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
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.fragment.StorageDetailsFragment;
import com.storelogflog.uk.apputil.Common;


import java.util.ArrayList;
import java.util.List;

public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageYardsHolder> implements Filterable {

    FragmentActivity activity;
    List<Storage>storageList;
    List<Storage> storageListFiltered;

    public StorageListAdapter(FragmentActivity activity, List<Storage> storageList) {
        this.activity = activity;
        this.storageList=storageList;
        this.storageListFiltered=storageList;
    }

    @NonNull
    @Override
    public StorageYardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_storage_yards,parent,false);
        return new StorageYardsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageYardsHolder holder, int position) {

        final Storage storage=storageListFiltered.get(position);
        holder.txtTitle.setText(""+storage.getName());
        holder.txtDescription.setText(""+storage.getShortDesp());
        holder.txtAvailabilityValue.setText(""+storage.getAvailability());
        holder.txtPriceValue.setText(storage.getPricing());

        Utility.loadImage(activity, storage.getImage(),holder.imgStorage);



        holder.cardViewTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           Fragment     fragment = new StorageDetailsFragment();
              Bundle  bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                fragment.setArguments(bundle);
                Common.loadFragment(activity, fragment, true, null);

            }
        });
    }

    @Override
    public int getItemCount() {
        return storageListFiltered.size();
    }

    public class StorageYardsHolder extends RecyclerView.ViewHolder
    {
        private CardView cardViewTop;
        private AppCompatTextView txtTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtAvailabilityValue;
        private AppCompatTextView txtPriceValue;
        private AppCompatImageView imgStorage;

        public StorageYardsHolder(@NonNull View itemView) {
            super(itemView);
            cardViewTop=itemView.findViewById(R.id.cardView);
            txtTitle=itemView.findViewById(R.id.txt_title);
            txtDescription=itemView.findViewById(R.id.txt_des);
            txtAvailabilityValue=itemView.findViewById(R.id.txt_availability_value);
            txtPriceValue=itemView.findViewById(R.id.txt_price_value);
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
                    ArrayList<Storage> filteredStorageList = new ArrayList<>();


                    for (Storage model : storageList) {
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

                storageListFiltered = (ArrayList<Storage>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
