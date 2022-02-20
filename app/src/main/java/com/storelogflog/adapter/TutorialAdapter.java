package com.storelogflog.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.R;
import com.storelogflog.activity.NotificationDetailsActivity;
import com.storelogflog.bean.Tutorial;


import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialHolder> {

    FragmentActivity activity;
    List<Tutorial> notificationList;

    public TutorialAdapter(FragmentActivity activity,List<Tutorial>notificationList) {

        this.activity = activity;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public TutorialAdapter.TutorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_tutorial,parent,false);
        return new TutorialAdapter.TutorialHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialAdapter.TutorialHolder holder, int position) {

        final Tutorial notification=notificationList.get(position);
        holder.txtTitle.setText(""+notification.getTitle());
        //holder.txtDescription.setText(""+notification.getDesp());
        //holder.txtDateTime.setText(""+notification.getDate());


//        holder.rlTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.startActivity(new Intent(activity, NotificationDetailsActivity.class)
//                        .putExtra("notification",notification));
//            }
//        });
        holder.mvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = notification.getvideo();
                if (!url.startsWith("https://") && !url.startsWith("http://")){
                    url = "http://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // Note the Chooser below. If no applications match,
                // Android displays a system message.So here there is no need for try-catch.
                activity.startActivity(Intent.createChooser(intent, "Browse with"));
            }
        });
        holder.mpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = notification.getpdf();
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(url), "application/pdf");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class TutorialHolder extends RecyclerView.ViewHolder
    {

        private RelativeLayout rlTop;
        private AppCompatTextView txtTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtDateTime;
        private AppCompatImageView mvideo;
        private AppCompatImageView mpdf;


        public TutorialHolder(@NonNull View itemView) {
            super(itemView);

            rlTop= itemView.findViewById(R.id.rl_top);
            txtTitle= itemView.findViewById(R.id.txt_title);
            txtDescription= itemView.findViewById(R.id.txt_description);
            txtDateTime= itemView.findViewById(R.id.txt_date_time);
            mvideo = itemView.findViewById(R.id.video);
            mpdf = itemView.findViewById(R.id.mpdf);


        }
    }
}
