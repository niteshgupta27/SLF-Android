package com.storelogflog.uk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.activeListBean.ActiveAuction;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.fragment.ViewOffersFragment;
import com.storelogflog.uk.apputil.Common;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveListAdapter extends RecyclerView.Adapter<ActiveListAdapter.ActiveListHolder> implements Filterable {

    FragmentActivity activity;
    List<ActiveAuction>activeAuctionList;
    List<ActiveAuction>activeAuctionListFiltered;
    private Fragment fragment;
    private Bundle bundle;

    public ActiveListAdapter(FragmentActivity activity,List<ActiveAuction>activeAuctionList) {
        this.activity = activity;
        this.activeAuctionList=activeAuctionList;
        this.activeAuctionListFiltered=activeAuctionList;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_actvie_list,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveListHolder holder, int position) {

        final ActiveAuction activeAuction=activeAuctionListFiltered.get(position);
        holder.txtItemName.setText(""+activeAuction.getName());
        holder.txtDescription.setText(""+activeAuction.getDesp());
        holder.txtOffers.setText(activeAuction.getTotalOffers()+" Offers");
        holder.txtHighestOfferValue.setText(activeAuction.getTotalOffers()+"");
        holder.txtStorageName.setText(""+activeAuction.getStorageName());


        if (activeAuction.getStatus().equals("W"))
        {
             holder.txtStatusValue.setText("Waiting for offer");
             holder.llOffersChild.setVisibility(View.GONE);
             holder.llStatus.setVisibility(View.VISIBLE);
             holder.txtViewOffer2.setVisibility(View.GONE);
        }
        else if(activeAuction.getStatus().equals("P"))
        {
            holder.txtStatusValue.setText("Pending for sale");
            holder.llOffersChild.setVisibility(View.GONE);
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.txtViewOffer2.setVisibility(View.GONE);
        }
        else if (activeAuction.getStatus().equals("O"))
        {
            holder.llOffersChild.setVisibility(View.VISIBLE);
            holder.llStatus.setVisibility(View.GONE);
        }

        Utility.loadImage(activeAuction.getImage(),holder.imgItem);


        holder.txtViewOffers.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  fragment=new ViewOffersFragment();
                  bundle=new Bundle();
                  bundle.putString("auctionId",""+activeAuction.getID());
                  bundle.putString("auctionName",""+activeAuction.getName());
                  bundle.putString("From","Active");

                  fragment.setArguments(bundle);
                  Common.loadFragment(activity,fragment,true,null);
              }
          });


    }

    @Override
    public int getItemCount() {
        return activeAuctionListFiltered.size();
    }

    public class ActiveListHolder extends RecyclerView.ViewHolder
    {
        LinearLayout llOffersChild,llStatus;
        AppCompatTextView txtViewOffers;
        private AppCompatTextView txtItemName;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtOffers;
        private AppCompatTextView txtHighestOfferValue;
        private AppCompatTextView txtViewOffer;
        private AppCompatTextView txtViewOffer2;
        private AppCompatTextView txtStatusValue;
        private AppCompatTextView txtStorageName;
        private CircleImageView imgItem;

        public ActiveListHolder(@NonNull View itemView) {
            super(itemView);
            llOffersChild=itemView.findViewById(R.id.ll_offers_child);
            llStatus=itemView.findViewById(R.id.ll_status);
            txtViewOffers=itemView.findViewById(R.id.txt_view_offers);
            txtItemName=itemView.findViewById(R.id.txt_item_name);
            txtDescription=itemView.findViewById(R.id.txt_description);
            txtOffers=itemView.findViewById(R.id.txt_offers);
            txtHighestOfferValue=itemView.findViewById(R.id.txt_highest_offers_value);
            txtViewOffer=itemView.findViewById(R.id.txt_view_offers);
            imgItem =itemView.findViewById(R.id.img_item);
            txtStatusValue =itemView.findViewById(R.id.txt_status_value);
            txtStorageName =itemView.findViewById(R.id.txt_storage_name);
            txtViewOffer2 =itemView.findViewById(R.id.txt_view_offers2);

        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    activeAuctionListFiltered = activeAuctionList;
                } else {
                    ArrayList<ActiveAuction> filteredAuctionList = new ArrayList<>();


                    for (ActiveAuction model : activeAuctionList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredAuctionList.add(model);
                        }
                    }
                    activeAuctionListFiltered = filteredAuctionList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = activeAuctionListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                activeAuctionListFiltered = (ArrayList<ActiveAuction>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
