package com.storelogflog.adapter;

import android.content.Context;
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

import com.storelogflog.activity.VideoActivity;
import com.storelogflog.bean.Tutorial;


import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialHolder> {

    Context Mcontext;
    List<Tutorial> notificationList;

    public TutorialAdapter(Context activity, List<Tutorial>notificationList) {

        this.Mcontext = activity;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public TutorialAdapter.TutorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_tutorial,parent,false);
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
                Intent intent = new Intent(Mcontext,VideoActivity.class);
                intent.putExtra("urlvideo",notification.getvideo());
                Mcontext.startActivity(intent);
            }
        });
        holder.mpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = notification.getpdf();
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(url), "application/pdf");
                Mcontext.startActivity(intent);
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
