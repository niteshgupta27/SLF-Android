package com.storelogflog.uk.StorageSelection.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.activity.ShanpeInfoActivity;
import com.storelogflog.uk.StorageSelection.model.CardViewModel;
import com.storelogflog.uk.StorageSelection.model.StorageShapeModel;
import com.storelogflog.uk.adapter.CategryListAdapter;
import com.storelogflog.uk.apputil.ScreenshotUtils;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<CardViewModel.Item.Photo> Image_list;
    RequestOptions options;

    int pagNo = 0;
    ArrayList<StorageShapeModel.Storage.ShapsList> shape_name_list;

    public ImageAdapter(Context context, List<CardViewModel.Item.Photo> arPhoto_) {
        this.context = context;
        Image_list = arPhoto_;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Image_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.card_img_item, container, false);

        options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .fitCenter()
                .placeholder(R.drawable.card_images)
                .error(R.drawable.card_images);

        ImageView card_img = itemView.findViewById(R.id.card_img);

        Glide.with(context).load(Image_list.get(position).getURL()).apply(options).into(card_img);

        Log.e("Image_list", String.valueOf(Image_list.get(position).getURL()));


        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }



}
