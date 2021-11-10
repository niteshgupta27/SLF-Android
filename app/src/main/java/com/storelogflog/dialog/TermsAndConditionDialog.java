package com.storelogflog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.storelogflog.R;


public abstract class TermsAndConditionDialog extends Dialog implements View.OnClickListener {

    private  Context context;
    private AppCompatTextView txtIAgree;

    public TermsAndConditionDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.context=context;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_terms_and_condition,null);
        setContentView(view);
        txtIAgree=view.findViewById(R.id.txt_i_agree);
        txtIAgree.setOnClickListener(this);


        Window window = getWindow();
        window.setGravity(Gravity.CENTER);

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);
        getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
       // window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);




        show();
    }


    public abstract void  onClickIAgree();


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_i_agree:
                onClickIAgree();
                dismiss();
                break;
        }
    }
}
