package com.storelogflog.uk.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.NotificationDetailsActivity;
import com.storelogflog.uk.bean.notificationBean.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationHolder> {

    FragmentActivity activity;
    List<Notification>notificationList;

    public NotificationsAdapter(FragmentActivity activity,List<Notification>notificationList) {

        this.activity = activity;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_notifications,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

        final Notification notification=notificationList.get(position);
        holder.txtTitle.setText(""+notification.getTitle());
        holder.txtDescription.setText(""+notification.getDesp());
        holder.txtDateTime.setText(""+notification.getDate());


        holder.rlTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, NotificationDetailsActivity.class)
                .putExtra("notification",notification));
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder
    {

        private RelativeLayout rlTop;
        private AppCompatTextView txtTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtDateTime;


        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            rlTop= itemView.findViewById(R.id.rl_top);
            txtTitle= itemView.findViewById(R.id.txt_title);
            txtDescription= itemView.findViewById(R.id.txt_description);
            txtDateTime= itemView.findViewById(R.id.txt_date_time);


        }
    }
}
