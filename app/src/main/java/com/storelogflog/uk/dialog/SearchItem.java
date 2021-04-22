package com.storelogflog.uk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.storelogflog.uk.R;


public abstract class SearchItem extends Dialog implements View.OnClickListener {

    private  Context context;
    private AppCompatTextView txtSubmit,txt_title;
    private AppCompatEditText editCategory;
    private AppCompatImageView imgCancel;
    private AppCompatTextView addCategory;


    public SearchItem(@NonNull Context context, String name) {
        super(context, R.style.CustomDialog);
        this.context=context;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_categoty,null);
        setContentView(view);
        txt_title = view.findViewById(R.id.txt_title);
        imgCancel=view.findViewById(R.id.img_cancel);
        editCategory=view.findViewById(R.id.edit_category);
        txtSubmit=view.findViewById(R.id.txt_submit);
        txtSubmit.setOnClickListener(this);
        imgCancel.setOnClickListener(this);

        txt_title.setText(name);
        editCategory.setHint("Enter card name");
        txtSubmit.setText(context.getResources().getString(R.string.submit));


        Window window = getWindow();
        window.setGravity(Gravity.CENTER);

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);
        getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);




        show();
    }





    public abstract void  onClickSubmit(String categoryName);


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_submit:
                if (editCategory.getText().toString().isEmpty())
                {
                    Toast.makeText(context,"Please enter card name",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    onClickSubmit(editCategory.getText().toString());
                    dismiss();
                }


                break;

            case R.id.img_cancel:
                dismiss();
        }
    }
}
