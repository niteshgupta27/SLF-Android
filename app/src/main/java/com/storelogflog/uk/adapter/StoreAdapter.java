package com.storelogflog.uk.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.fragment.CardsFragment;
import com.storelogflog.uk.StorageSelection.fragment.ManageShelftFragment;
import com.storelogflog.uk.StorageSelection.fragment.RelocateFragment;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.fragment.ChatingFragment;
import com.storelogflog.uk.fragment.LogFragment;
import com.storelogflog.uk.fragment.StorageDetailsFragment;

import java.util.ArrayList;
import java.util.List;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder> implements Filterable {

    private FragmentActivity activity;
    public static int STORE = 1;
    public static int LOG = 2;
    public static int FLOG = 3;
    private int from = -1;
    Fragment fragment;
    Bundle bundle;
    List<Storage> storageList;
    List<Storage> storageListFiltered;
    String itemId;


    public StoreAdapter(FragmentActivity activity, List<Storage> storageList, int from, String itemId) {
        this.activity = activity;
        this.from = from;
        this.storageList = storageList;
        this.storageListFiltered = storageList;
        this.itemId = itemId;
    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_storage_yards_comment, parent, false);
        return new StoreHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final StoreHolder holder, int position) {

        final Storage storage = storageListFiltered.get(position);
        holder.txtStorageTitle.setText("" + storage.getName());
        holder.txtDescription.setText("" + storage.getShortDesp());
        holder.txtUnitValue.setText("" + storage.getUnits());


         if (storage.getStorageType()!=null) {
             if (storage.getStorageType().equals("1")) {
                 holder.imgStorage.setImageResource(R.drawable.container);
                 holder.txtUnitValue.setVisibility(View.VISIBLE);
             }else if (storage.getStorageType().equals("2")){
                 holder.imgStorage.setImageResource(R.drawable.garage_green);
             }else if (storage.getStorageType().equals("3")){
                 holder.imgStorage.setImageResource(R.drawable.shed_green);
             }else if (storage.getStorageType().equals("4")){
                 holder.imgStorage.setImageResource(R.drawable.loft_green);
             }else if (storage.getStorageType().equals("5")){
                 holder.imgStorage.setImageResource(R.drawable.other_green);
             }
             else if (storage.getStorageType().equals("6")){
                 holder.txtUnitValue.setVisibility(View.VISIBLE);
                 if (storage.getDoorColor()!=null) {
                     if (storage.getDoorColor().equals("1")) {
                         holder.imgStorage.setImageResource(R.drawable.white_main_door);
                     }else if (storage.getDoorColor().equals("2")){
                         holder.imgStorage.setImageResource(R.drawable.blue_main_door);
                     }else if (storage.getDoorColor().equals("3")){
                         holder.imgStorage.setImageResource(R.drawable.red_main_door);
                     }else if (storage.getDoorColor().equals("4")){
                         holder.imgStorage.setImageResource(R.drawable.green_main_door);
                     }else if (storage.getDoorColor().equals("5")){
                         holder.imgStorage.setImageResource(R.drawable.yellow_main_door);
                     }
                 }

             }
         }

        if (from == STORE) {
            if (storage.getStorageOwner().equals("O")) {
                holder.imgChat.setVisibility(View.VISIBLE);
            }else {
                holder.imgChat.setVisibility(View.GONE);
            }

            if (!itemId.equals("")) {
                holder.imgLog.setVisibility(View.GONE);
                holder.imgChat.setVisibility(View.GONE);
                holder.img_card.setVisibility(View.GONE);
            }

        } else if (from == LOG) {
            holder.llLogFlogChat.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemId.equals("")) {

                    if (from == STORE) {
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.UNITID, storage.getUnitID());
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.STORAGEID, storage.getID() + "");
                        PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_STORAGE_BEAN, storage);

                        fragment = new StorageDetailsFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(activity, fragment, true, null);


                    } else if (from == LOG) {

                        PreferenceManger.getPreferenceManger().setString(PrefKeys.UNITID, storage.getUnitID());
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.STORAGEID, storage.getID() + "");
                        PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_STORAGE_BEAN, storage);
                        fragment = new LogFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(activity, fragment, true, null);
                    }
                }else {
                    fragment = new RelocateFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("storage", storage);
                    bundle.putString(Constants.ItemID,itemId);
                    fragment.setArguments(bundle);
                    Common.loadFragment(activity, fragment, true, null);
                }
            }
        });

        holder.imgLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new CardsFragment();
                bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                fragment.setArguments(bundle);
                Common.loadFragment(activity, fragment, true, null);

            }
        });


        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new ChatingFragment();
                bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                fragment.setArguments(bundle);
                Common.loadFragment(activity, fragment, true, null);
            }
        });


        holder.img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if (itemId.equals("")) {
                     fragment = new ManageShelftFragment();
                     bundle = new Bundle();
                     bundle.putSerializable("storage", storage);
                     fragment.setArguments(bundle);
                     Common.loadFragment(activity, fragment, true, null);
                 }else {
                     fragment = new RelocateFragment();
                     bundle = new Bundle();
                     bundle.putSerializable("storage", storage);
                     bundle.putString(Constants.ItemID,itemId);
                     fragment.setArguments(bundle);
                     Common.loadFragment(activity, fragment, true, null);
                 }
            }
        });

    }

    @Override
    public int getItemCount() {
        return storageListFiltered.size();
    }

    public class StoreHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private LinearLayout llLogFlogChat;
        private AppCompatImageView imgStorage;
        private RelativeLayout imgLog;
        private AppCompatTextView txtStorageTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtUnitValue;
        private RelativeLayout imgChat,img_card;

        public StoreHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            llLogFlogChat = itemView.findViewById(R.id.ll_chat_log_flog);
            txtDescription = itemView.findViewById(R.id.txt_des);
            imgStorage = itemView.findViewById(R.id.img_storage);
            txtStorageTitle = itemView.findViewById(R.id.txt_title);
            txtUnitValue = itemView.findViewById(R.id.txt_unit_value);
            imgLog = itemView.findViewById(R.id.img_log);
            imgChat = itemView.findViewById(R.id.img_chat);
            img_card = itemView.findViewById(R.id.img_card);

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
