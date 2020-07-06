package com.storelogflog.uk.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.NotificationDetailsActivity;
import com.storelogflog.uk.activity.PaymentActivity;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.notificationBean.Notification;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.bean.subscriptionBean.Subscripiton;
import com.storelogflog.uk.callBackInterFace.RenewSubscription;
import com.stripe.model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.NotificationHolder> implements Filterable {

    FragmentActivity activity;
    List<Subscripiton>subscripitonList;
    List<Subscripiton>subscripitonListFiltered;
    RenewSubscription renewSubscription;

    public SubscriptionAdapter(FragmentActivity activity, List<Subscripiton>subscripitonList,RenewSubscription renewSubscription) {

        this.activity = activity;
        this.subscripitonList=subscripitonList;
        this.subscripitonListFiltered=subscripitonList;
        this.renewSubscription=renewSubscription;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_subscription,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

        final Subscripiton subscripiton=subscripitonListFiltered.get(position);
        holder.txtTitle.setText(""+subscripiton.getStorage());
        holder.txtDescription.setText(""+subscripiton.getDesp());
        holder.txtStartDate.setText(""+subscripiton.getStartDate());
        holder.txtEndDate.setText(""+subscripiton.getEndDate());
        holder.txtRemainingValue.setText(""+subscripiton.getRemaing()+" days");
        holder.txtPriceValue.setText(Constants.pound +""+subscripiton.getAmount());

        if (subscripiton.getRemaing()==0)
        {
            holder.txtRenew.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.txtRenew.setVisibility(View.GONE);
        }



        holder.txtRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                renewSubscription.renewClick(subscripiton);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subscripitonListFiltered.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder
    {

        private RelativeLayout rlTop;
        private AppCompatTextView txtTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtStartDate;
        private AppCompatTextView txtEndDate;
        private AppCompatTextView txtRenew;
        private AppCompatTextView txtRemainingValue;
        private AppCompatTextView txtPriceValue;


        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            rlTop= itemView.findViewById(R.id.rl_top);
            txtTitle= itemView.findViewById(R.id.txt_title);
            txtDescription= itemView.findViewById(R.id.txt_des);
            txtStartDate= itemView.findViewById(R.id.txt_start_date_value);
            txtEndDate= itemView.findViewById(R.id.txt_end_date_value);
            txtRenew= itemView.findViewById(R.id.txt_renew);
            txtRemainingValue= itemView.findViewById(R.id.txt_remaining_value);
            txtPriceValue= itemView.findViewById(R.id.txt_price_value);


        }
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    subscripitonListFiltered = subscripitonList;
                } else {
                    ArrayList<Subscripiton> filteredSubscripitonList = new ArrayList<>();


                    for (Subscripiton model : subscripitonList) {
                        if (model.getStorage().toLowerCase().contains(charString.toLowerCase())) {
                            filteredSubscripitonList.add(model);
                        }
                    }
                    subscripitonListFiltered = filteredSubscripitonList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = subscripitonListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                subscripitonListFiltered = (ArrayList<Subscripiton>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
