package com.storelogflog.uk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.bean.storageBean.Storage;

public class ThankYouFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llContinue;
    private AppCompatTextView txtUserMessage;
    private String from;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thank_you_req, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    //Initialisation the reference variables & getting  'from flag' and 'msg'
    @Override
    public void initViews(View view) {

        llContinue = view.findViewById(R.id.ll_continue);
        txtUserMessage = view.findViewById(R.id.txt_user_msg);

        ((HomeActivity) getActivity()).enableViews(false, "Thank you");

        if (getArguments() != null) {

            String message = getArguments().getString("message");
            from = getArguments().getString("from");
            txtUserMessage.setText("" + message);
        }
    }

    @Override
    public void initListeners() {

        llContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_continue:
                if (from.equals(Constants.FROM_CONTACT_STORAGE)) {
                    Common.loadFragment(getActivity(), new StorageListFragment(), false, null);

                } else if (from.equals(Constants.FROM_STORAGE_CLAIM)) {
                    Common.loadFragment(getActivity(), new DashBoardFragment(), false, null);

                } else if (from.equals(Constants.FROM_STORAGE_LEAD)) {
                    Common.loadFragment(getActivity(), new DashBoardFragment(), false, null);

                } else if (from.equals(Constants.FROM_PAYMENT_SCREEN)) {
                    Common.loadFragment(getActivity(), new StoreFragment(), false, null);

                } else if (from.equals("PaymentRenew")) {
                    Common.loadFragment(getActivity(), new DashBoardFragment(), false, null);

                } else if (from.equals(Constants.FROM_Add_STORAGE)) {
                    Fragment fragment = new StoreFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "store");
                    fragment.setArguments(bundle);

                    Common.loadFragment(getActivity(), fragment, false, null);


                } else if (from.equals("AddAuction")) {
                    Fragment fragment = new LogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "log");
                    bundle.putSerializable("storage", (Storage) getArguments().getSerializable("storage"));
                    fragment.setArguments(bundle);

                    Common.loadFragment(getActivity(), fragment, false, null);
                }

                break;
        }
    }
}
