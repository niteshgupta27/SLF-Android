package com.storelogflog.uk.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.photoBean.Photo;
import com.storelogflog.uk.callBackInterFace.RemovePhoto;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ActiveListHolder> {

    FragmentActivity activity;
    List<Photo>photoList;
    RemovePhoto removePhoto;

    public PhotoAdapter(FragmentActivity activity,List<Photo>photoList,RemovePhoto removePhoto) {
        this.activity = activity;
        this.photoList=photoList;
        this.removePhoto=removePhoto;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_photo,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveListHolder holder, int position) {

        final Photo photo=photoList.get(position);
        Utility.loadImage(photo.getURL(),holder.imgPhoto);

        holder.imgRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Do you want delete?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                removePhoto.deletePhotoListner(photo.getID());
                                dialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ActiveListHolder extends RecyclerView.ViewHolder
    {

        private AppCompatImageView imgPhoto;
        private AppCompatImageView imgRemovePhoto;
        private LinearLayout llTop;

        public ActiveListHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto=itemView.findViewById(R.id.img_photo);
            imgRemovePhoto=itemView.findViewById(R.id.img_remove_photo);
            llTop=itemView.findViewById(R.id.ll_top);

        }
    }
}
