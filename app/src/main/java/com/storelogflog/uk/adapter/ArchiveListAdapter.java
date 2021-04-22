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
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.ArchiveListBean.ArchiveListBean;
import com.storelogflog.uk.bean.activeListBean.ActiveAuction;
import com.storelogflog.uk.bean.activeListBean.ActiveListBean;
import com.storelogflog.uk.fragment.ViewOffersFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ActiveListHolder> implements Filterable {

    FragmentActivity activity;
    List<ArchiveListBean.Auction> activeAuctionList;
    private Fragment fragment;
    private Bundle bundle;
    List<ArchiveListBean.Auction>activeAuctionListFiltered;

    public ArchiveListAdapter(FragmentActivity activity, List<ArchiveListBean.Auction> activeAuctionList) {
        this.activity = activity;
        this.activeAuctionList=activeAuctionList;
        this.activeAuctionListFiltered=activeAuctionList;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_archive_list,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveListHolder holder, int position) {

        final ArchiveListBean.Auction activeAuction=activeAuctionListFiltered.get(position);
        holder.txtItemName.setText(""+activeAuction.getName());
        holder.txtDescription.setText(""+activeAuction.getDesp());
        holder.txtStorageName.setText(""+activeAuction.getStorageName());
    //    holder.txtOffers.setText(activeAuction.getTotalOffers()+" Offers");
       // holder.txtHighestOfferValue.setText(activeAuction.getTotalOffers()+"");


        if (activeAuction.getStatus().equals("S"))
        {
            holder.txtStatusValue.setText("Sold");
        }
        else
        {
            holder.txtStatusValue.setText("Not Sold");
        }

        Utility.loadImage(activity, String.valueOf(activeAuction.getImage()),holder.imgItem);

        holder.txtViewOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new ViewOffersFragment();
                bundle=new Bundle();
                bundle.putString("auctionId",""+activeAuction.getID());
                bundle.putString("auctionName",""+activeAuction.getName());
                bundle.putString("From","Archive");
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
            imgItem=itemView.findViewById(R.id.img_item);
            txtStatusValue=itemView.findViewById(R.id.txt_status_value);
            txtStorageName=itemView.findViewById(R.id.txt_storage_name);

            txtViewOffer.setVisibility(View.GONE);
            txtViewOffers.setVisibility(View.GONE);

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
                    ArrayList<ArchiveListBean.Auction> filteredAuctionList = new ArrayList<>();


                    for (ArchiveListBean.Auction model : activeAuctionList) {
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

                activeAuctionListFiltered = (ArrayList<ArchiveListBean.Auction>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
