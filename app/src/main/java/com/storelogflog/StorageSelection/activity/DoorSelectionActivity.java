package com.storelogflog.StorageSelection.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.storelogflog.R;
import com.storelogflog.activity.BaseActivity;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;

public class DoorSelectionActivity extends BaseActivity {


    Context mContext;
    ImageView single_door_check, double_door_check, single_garage_check, double_garage_check,
            single_door_img, double_door_img, single_garage_img, double_garage_img;
    RelativeLayout single_door_relative, double_door_relative, single_garage_relative, double_garage_relative;
    TextView next_btn;
    LinearLayout toolbar_back;
    String value ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_selection);

        mContext = this;
      initViews();
       initListeners();

    }

    @Override
    public void initViews() {
        single_door_check = findViewById(R.id.single_door_check);
        double_door_check = findViewById(R.id.double_door_check);
        single_garage_check = findViewById(R.id.single_garage_check);
        double_garage_check = findViewById(R.id.double_garage_check);

        single_door_relative =findViewById(R.id.single_door_relative);
        double_door_relative = findViewById(R.id.double_door_relative);
        single_garage_relative = findViewById(R.id.single_garage_relative);
        double_garage_relative = findViewById(R.id.double_garage_relative);

        single_door_img = findViewById(R.id.single_door_img);
        double_door_img = findViewById(R.id.double_door_img);
        single_garage_img = findViewById(R.id.single_garage_img);
        double_garage_img = findViewById(R.id.double_garage_img);

        next_btn = findViewById(R.id.next_btn);
        toolbar_back = findViewById(R.id.toolbar_back);
    }

    @Override
    public void initListeners() {

        single_door_relative.setOnClickListener(this);
        double_door_relative.setOnClickListener(this);
        single_garage_relative.setOnClickListener(this);
        double_garage_relative.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.single_door_relative:

                value = "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection,getResources().getString(R.string.single_door));


                single_door_relative.setBackgroundResource(R.drawable.green_back);
                double_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                single_garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_garage_relative.setBackgroundResource(R.drawable.background_edit_square);


                single_door_check.setVisibility(View.VISIBLE);
                double_door_check.setVisibility(View.GONE);
                single_garage_check.setVisibility(View.GONE);
                double_garage_check.setVisibility(View.GONE);

                single_door_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                double_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                single_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.double_door_relative:

                value = "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection,getResources().getString(R.string.double_door));


                single_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_door_relative.setBackgroundResource(R.drawable.green_back);
                single_garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_garage_relative.setBackgroundResource(R.drawable.background_edit_square);

                single_door_check.setVisibility(View.GONE);
                double_door_check.setVisibility(View.VISIBLE);
                single_garage_check.setVisibility(View.GONE);
                double_garage_check.setVisibility(View.GONE);

                single_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_door_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                single_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                break;


            case R.id.single_garage_relative:

                value = "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection,getResources().getString(R.string.single_garage));

                single_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                single_garage_relative.setBackgroundResource(R.drawable.green_back);
                double_garage_relative.setBackgroundResource(R.drawable.background_edit_square);

                single_door_check.setVisibility(View.GONE);
                double_door_check.setVisibility(View.GONE);
                single_garage_check.setVisibility(View.VISIBLE);
                double_garage_check.setVisibility(View.GONE);


                single_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                single_garage_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                double_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);


                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;



            case R.id.double_garage_relative:

                value = "1";
                PreferenceManger.getPreferenceManger().setString(PrefKeys.DoorSelection,getResources().getString(R.string.double_garage));

                single_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_door_relative.setBackgroundResource(R.drawable.background_edit_square);
                single_garage_relative.setBackgroundResource(R.drawable.background_edit_square);
                double_garage_relative.setBackgroundResource(R.drawable.green_back);

                single_door_check.setVisibility(View.GONE);
                double_door_check.setVisibility(View.GONE);
                single_garage_check.setVisibility(View.GONE);
                double_garage_check.setVisibility(View.VISIBLE);
                single_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_door_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                single_garage_img.setColorFilter(getResources().getColor(R.color.img_color), android.graphics.PorterDuff.Mode.SRC_IN);
                double_garage_img.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

                next_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.next_btn:

                    if (!value.equals("")){

                        Intent intent = new Intent(mContext,ConfirmationActivity.class);
                        startActivity(intent);


                }else {
                        Toast.makeText(mContext,"Please select your storage door first!",Toast.LENGTH_SHORT).show();
                    }
                break;

            case R.id.toolbar_back:
                onBackPressed();
                break;
        }

    }


}
