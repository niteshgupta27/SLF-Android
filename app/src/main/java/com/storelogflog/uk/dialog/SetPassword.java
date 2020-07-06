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
import com.storelogflog.uk.apputil.Validator;


public abstract class SetPassword extends Dialog implements View.OnClickListener {

    private  Context context;
    private AppCompatTextView txtSubmit;
    private AppCompatEditText editEmail;
    private AppCompatEditText editPassword;
    private AppCompatImageView imgCancel;
    private AppCompatTextView addCategory;


    public SetPassword(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.context=context;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_password,null);
        setContentView(view);
        imgCancel=view.findViewById(R.id.img_cancel);
        editEmail=view.findViewById(R.id.edit_email);
        editPassword=view.findViewById(R.id.edit_password);
        txtSubmit=view.findViewById(R.id.txt_submit);
        txtSubmit.setOnClickListener(this);
        imgCancel.setOnClickListener(this);


        Window window = getWindow();
        window.setGravity(Gravity.CENTER);

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);
        getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);



    }

    public SetPassword(@NonNull Context context,String email)
    {
        this(context);

        editEmail.setText(""+email);

        show();
    }



    public abstract void  onClickSubmit(String email,String password);


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_submit:
                if (editEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(context,"Email can't be blank",Toast.LENGTH_SHORT).show();
                }
                else if(!Validator.isEmailValid(editEmail.getText().toString()))
                {

                    Toast.makeText(context,"Invalid email",Toast.LENGTH_SHORT).show();
                }
                else if(editPassword.getText().toString().isEmpty())
                {

                    Toast.makeText(context,"Password can't be blank",Toast.LENGTH_SHORT).show();

                }
                else if(editPassword.getText().toString().length()<6)
                {
                    Toast.makeText(context,"Password can't be less than 6 digits",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    onClickSubmit(editEmail.getText().toString(),editPassword.getText().toString());
                    dismiss();
                }


                break;

            case R.id.img_cancel:
                dismiss();
        }
    }
}
