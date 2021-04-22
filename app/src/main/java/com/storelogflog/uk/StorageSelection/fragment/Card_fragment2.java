package com.storelogflog.uk.StorageSelection.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.fragment.AddItemFragment;

public class Card_fragment2 extends Fragment {

    View view;
    Context mContext;
    ImageView img_splash;
    private Storage storage;
    String AddedItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_card_fragment2, container, false);

        img_splash = view.findViewById(R.id.img_splash);


        if (getArguments() != null) {
            storage = (Storage) getArguments().getSerializable("storage");
            AddedItems = getArguments().getString("AddedItems");
            if (storage != null) {
                ((HomeActivity) getActivity()).enableViews(true, storage.getName());

            }


        }


        img_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Fragment  fragment = new AddItemFragment();
              Bundle  bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putString("AddedItems", AddedItems);
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, false, null);

            }
        });


        return view;
    }
}
