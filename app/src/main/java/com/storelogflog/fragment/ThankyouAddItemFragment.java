package com.storelogflog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.storelogflog.R;
import com.storelogflog.StorageSelection.fragment.CardsFragment;
import com.storelogflog.activity.HomeActivity;
import com.storelogflog.apputil.Common;
import com.storelogflog.bean.storageBean.Storage;

import java.util.Objects;


public class ThankyouAddItemFragment extends Fragment implements View.OnClickListener {

    View view;
    Context mContext;
    LinearLayout ll_view_edit_item, ll_view_item,ll_view_item2,ll_edit_item;
    AppCompatTextView txt_user_msg;
    Storage storage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thankyou_add_item, container, false);

        ((HomeActivity)getActivity()).enableViews(true,"");


        ll_view_edit_item = view.findViewById(R.id.ll_view_edit_item);
        ll_view_item = view.findViewById(R.id.ll_view_item);
        ll_view_item2  = view.findViewById(R.id.ll_view_item2);
        ll_edit_item = view.findViewById(R.id.ll_edit_item);
        txt_user_msg = view.findViewById(R.id.txt_user_msg);

        ll_view_item.setOnClickListener(this);
        ll_view_item2.setOnClickListener(this);
        ll_edit_item.setOnClickListener(this);

        if (getArguments() != null) {

            storage = (Storage) getArguments().getSerializable("storage");

            if (String.valueOf(getArguments().getString("Fragment")).equals("Photo Fragment")){
                ll_view_edit_item.setVisibility(View.GONE);
                ll_view_item.setVisibility(View.VISIBLE);
                ((HomeActivity)getActivity()).enableViews(true,"Add Item");

            }else {
                ll_view_edit_item.setVisibility(View.VISIBLE);
                ll_view_item.setVisibility(View.GONE);
                txt_user_msg.setText("Updated Item Successfully");
                ((HomeActivity)getActivity()).enableViews(true,"Update Item");

            }

        }


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ll_view_item:

            case R.id.ll_view_item2:
                Fragment fragment = new CardsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("position",getArguments().getString("position"));
                bundle.putSerializable("storage", storage);
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, false, null);

                 break;

            case R.id.ll_edit_item:
                ((HomeActivity) Objects.requireNonNull(getActivity())).EnableView(true);

                break;

        }
    }
}
