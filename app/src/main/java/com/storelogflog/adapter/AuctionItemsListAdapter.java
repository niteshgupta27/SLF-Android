package com.storelogflog.adapter;

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

import com.storelogflog.R;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.itemListBean.Auction;
import com.storelogflog.fragment.ViewOffersFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuctionItemsListAdapter extends RecyclerView.Adapter<AuctionItemsListAdapter.ActiveListHolder> implements Filterable {

    FragmentActivity activity;
    List<Auction>auctionList;
    List<Auction>auctionListFiltered;
    private Fragment fragment;
    private Bundle bundle;


    public AuctionItemsListAdapter(FragmentActivity activity,List<Auction>auctionList) {
        this.activity = activity;
        this.auctionList=auctionList;
        this.auctionListFiltered=auctionList;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_actvie_list,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveListHolder holder, int position) {

         final Auction auction=auctionListFiltered.get(position);
         holder.txtItemName.setText(""+auction.getName());
         holder.txtDescription.setText(""+auction.getDesp());
         holder.txtOffers.setText(auction.getTotalOffers()+" Offers");
         holder.txtHighestOfferValue.setText(auction.getTotalOffers()+"");

         holder.txtStorageName.setVisibility(View.GONE);


         if (auction.getStatus().equals("W"))
        {
            holder.txtStatusValue.setText("Waiting for offer");
            holder.llOffersChild.setVisibility(View.GONE);
            holder.txtViewOffer2.setVisibility(View.GONE);
            holder.llStatus.setVisibility(View.VISIBLE);
        }
        else if(auction.getStatus().equals("P"))
        {
            holder.txtStatusValue.setText("Pending for sale");
            holder.llOffersChild.setVisibility(View.GONE);
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.txtViewOffer2.setVisibility(View.GONE);
        }
        else if (auction.getStatus().equals("O"))
        {
            holder.llOffersChild.setVisibility(View.VISIBLE);
            holder.llStatus.setVisibility(View.GONE);
        }
        else if (auction.getStatus().equals("N"))
        {
            holder.txtStatusValue.setText("Not Sold");
            holder.llOffersChild.setVisibility(View.GONE);
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.txtViewOffer2.setVisibility(View.VISIBLE);
        }
        else if (auction.getStatus().equals("S"))
        { holder.txtStatusValue.setText("Sold");
            holder.llOffersChild.setVisibility(View.GONE);
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.txtViewOffer2.setVisibility(View.VISIBLE);
        }


        Utility.loadImage(activity, auction.getImage(),holder.imgItem);

          holder.txtViewOffers.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  fragment=new ViewOffersFragment();
                  bundle=new Bundle();
                  bundle.putString("auctionId",""+auction.getID());
                  bundle.putString("auctionName",""+auction.getName());
                  bundle.putString("From","Active");
                  fragment.setArguments(bundle);
                  Common.loadFragment(activity,fragment,true,Common.ITEM_NAME_FRAGMENT);
              }
          });


        holder.txtViewOffer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new ViewOffersFragment();
                bundle=new Bundle();
                bundle.putString("auctionId",""+auction.getID());
                bundle.putString("auctionName",""+auction.getName());
                bundle.putString("From","Archive");
                fragment.setArguments(bundle);
                Common.loadFragment(activity,fragment,true,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return auctionListFiltered.size();
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
          private CircleImageView imgItem;
          private AppCompatTextView txtStatusValue;
          private AppCompatTextView txtStorageName;

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
                    auctionListFiltered = auctionList;
                } else {
                    ArrayList<Auction> filteredAuctionList = new ArrayList<>();


                    for (Auction model : auctionList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredAuctionList.add(model);
                        }
                    }
                    auctionListFiltered = filteredAuctionList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = auctionListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                auctionListFiltered = (ArrayList<Auction>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
