package com.storelogflog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.R;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.offerListBean.Offer;

import java.util.List;

public class ViewOfferAdapter extends RecyclerView.Adapter<ViewOfferAdapter.ActiveListHolder> {

    FragmentActivity activity;
    List<Offer>offerList;
    public int pos= -1;
    int id=-1;
    String from=null;


    public ViewOfferAdapter(FragmentActivity activity, List<Offer>offerList,String from) {
        this.activity = activity;
        this.offerList=offerList;
        this.from=from;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=null;
        if (from.equals("Active"))
        {
             view= LayoutInflater.from(activity).inflate(R.layout.item_view_offers,parent,false);

        }
        else if (from.equals("Archive"))
        {
             view= LayoutInflater.from(activity).inflate(R.layout.item_accepted_view_offers,parent,false);

        }

        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ActiveListHolder holder, final int position) {


        final Offer offer=offerList.get(position);

        if (from.equals("Active"))
        {

            holder.radioButton.setText(""+offer.getName());
            holder.txtDateTime.setText(""+offer.getDate());
            holder.txtOffer.setText("Offer: "+ Constants.pound+offer.getPrice());

            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pos=position;
                    id=offer.getID();
                    notifyDataSetChanged();
                }
            });


            if(position==pos)
            {
                holder.radioButton.setChecked(true);

            }
            else
            {
                holder.radioButton.setChecked(false);
            }


        }
        else if (from.equals("Archive"))
        {
            holder.txtAuctionHouse.setText(""+offer.getAuctionHouseName());
            holder.txtEmail.setText(""+offer.getEmail());
            holder.txtPhone.setText(""+offer.getPhone());
        }






    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class ActiveListHolder extends RecyclerView.ViewHolder
    {
       private AppCompatTextView txtOffer;
       private AppCompatTextView txtDateTime;
       private RadioButton radioButton;
       private LinearLayout llTop;
       private AppCompatTextView txtAuctionHouse;
       private AppCompatTextView txtEmail;
       private AppCompatTextView txtPhone;



        public ActiveListHolder(@NonNull View itemView) {
            super(itemView);

            if (from.equals("Active"))
            {
                radioButton=itemView.findViewById(R.id.radio_button);
                txtDateTime=itemView.findViewById(R.id.txt_date_time);
                txtOffer=itemView.findViewById(R.id.txt_offer);
                llTop=itemView.findViewById(R.id.ll_top);
            }
            else if (from.equals("Archive"))
            {
                txtAuctionHouse=itemView.findViewById(R.id.txt_auction_house);
                txtPhone=itemView.findViewById(R.id.txt_phone);
                txtEmail=itemView.findViewById(R.id.txt_email);
            }


        }
    }

    public  int getSelectedOffer()
    {
        return id;
    }
}
