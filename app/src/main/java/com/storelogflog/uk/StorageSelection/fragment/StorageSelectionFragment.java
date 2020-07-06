package com.storelogflog.uk.StorageSelection.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.activity.GridCellActivity;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;


public class StorageSelectionFragment extends Fragment {

    View view;
    Context mContext;
    ImageView garage_check, shed_check, loft_check, other_check,
            garage_img, shed_img, loft_img, other_img;
    RelativeLayout garage_relative, shed_relative, loft_relative, other_relative;
    TextView next_btn;
    String value="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_storage_selection, container, false);

        ((HomeActivity) getActivity()).enableViews(true, "Storage Type Selection");


        garage_check = view.findViewById(R.id.garage_check);
        shed_check = view.findViewById(R.id.shed_check);
        loft_check = view.findViewById(R.id.loft_check);
        other_check = view.findViewById(R.id.other_check);

        garage_relative = view.findViewById(R.id.garage_relative);
        shed_relative = view.findViewById(R.id.shed_relative);
        loft_relative = view.findViewById(R.id.loft_relative);
        other_relative = view.findViewById(R.id.other_relative);

        garage_img = view.findViewById(R.id.garage_img);
        shed_img = view.findViewById(R.id.shed_img);
        loft_img = view.findViewById(R.id.loft_img);
        other_img = view.findViewById(R.id.other_img);

        next_btn= view.findViewById(R.id.next_btn);



        garage_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value= "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.StorageType,getResources().getString(R.string.garage));


                garage_relative.setBackgroundResource(R.drawable.green_back);
                shed_relative.setBackgroundResource(R.drawable.background_edit_square);
                loft_relative.setBackgroundResource(R.drawable.background_edit_square);
                other_relative.setBackgroundResource(R.drawable.background_edit_square);


                garage_check.setVisibility(View.VISIBLE);
                shed_check.setVisibility(View.GONE);
                loft_check.setVisibility(View.GONE);
                other_check.setVisibility(View.GONE);

                garage_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                shed_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                loft_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                other_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }
        });


        shed_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value= "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.StorageType,getResources().getString(R.string.shed));


                garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                shed_relative.setBackgroundResource(R.drawable.green_back);
                loft_relative.setBackgroundResource(R.drawable.background_edit_square);
                other_relative.setBackgroundResource(R.drawable.background_edit_square);

                garage_check.setVisibility(View.GONE);
                shed_check.setVisibility(View.VISIBLE);
                loft_check.setVisibility(View.GONE);
                other_check.setVisibility(View.GONE);

                garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                shed_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                loft_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                other_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


            }
        });


        loft_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value= "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.StorageType,getResources().getString(R.string.loft));

                garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                shed_relative.setBackgroundResource(R.drawable.background_edit_square);
                loft_relative.setBackgroundResource(R.drawable.green_back);
                other_relative.setBackgroundResource(R.drawable.background_edit_square);

                garage_check.setVisibility(View.GONE);
                shed_check.setVisibility(View.GONE);
                loft_check.setVisibility(View.VISIBLE);
                other_check.setVisibility(View.GONE);


                garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                shed_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                loft_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                other_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);


                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }
        });


        other_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                value= "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.StorageType,getResources().getString(R.string.other));

                garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                shed_relative.setBackgroundResource(R.drawable.background_edit_square);
                loft_relative.setBackgroundResource(R.drawable.background_edit_square);
                other_relative.setBackgroundResource(R.drawable.green_back);

                garage_check.setVisibility(View.GONE);
                shed_check.setVisibility(View.GONE);
                loft_check.setVisibility(View.GONE);
                other_check.setVisibility(View.VISIBLE);
                garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                shed_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                loft_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                other_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (!value.equals("")){

                          Intent intent = new Intent(mContext, GridCellActivity.class);
                          startActivity(intent);

                      }else {
                     Toast.makeText(mContext,"Please select your storage type first!",Toast.LENGTH_SHORT).show();
                 }

            }
        });

        return view;
    }
}
