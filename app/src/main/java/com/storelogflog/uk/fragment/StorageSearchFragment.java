package com.storelogflog.uk.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;



import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apputil.Common;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class StorageSearchFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatEditText editSearchYard;
    private AppCompatImageView imgSearchYard;
    private AppCompatTextView txtViewAllStorage;
    private Fragment fragment;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storage_search, container, false);
        initViews(view);
        initListeners();


        return view;
    }


    @Override
    public void initViews(View view) {

        editSearchYard = view.findViewById(R.id.edit_search_yard);
        imgSearchYard = view.findViewById(R.id.img_search_yard);
        txtViewAllStorage = view.findViewById(R.id.txt_all_storage);



    }

    @Override
    public void onResume() {
        super.onResume();

        if (editSearchYard!=null)
        {
            editSearchYard.setText("");
        }
    }

    @Override
    public void initListeners() {

        imgSearchYard.setOnClickListener(this);
        txtViewAllStorage.setOnClickListener(this);


        editSearchYard.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    performSearch();
                    fragment = new SearchStorageListFragment();
                    bundle = new Bundle();
                    bundle.putString("searchText", editSearchYard.getText().toString());
                    fragment.setArguments(bundle);
                    Common.loadFragment(getActivity(), fragment, true, null);
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch() {
        editSearchYard.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(editSearchYard.getWindowToken(), 0);
        //...perform search
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_search_yard:

                fragment = new SearchStorageListFragment();
                bundle = new Bundle();
                bundle.putString("searchText", editSearchYard.getText().toString());
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
                break;
            case R.id.txt_all_storage:

                fragment = new SearchStorageListFragment();
                bundle = new Bundle();
                bundle.putString("searchText", "");
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(), fragment, true, null);
                break;

        }

    }
}
