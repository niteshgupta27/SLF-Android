package com.storelogflog.uk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;


public class FlogFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flog, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        ((HomeActivity)getActivity()).enableViews(false,"Auction List");


        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);


        tabLayout.addTab(tabLayout.newTab().setText("Active List"));
        tabLayout.addTab(tabLayout.newTab().setText("Archive List"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(),getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public void initListeners() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context context;
        int totalTabs;

        public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);

            this.context = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {

            Fragment fragment=null;

            switch (position) {

                case 0:
                    fragment=new ActiveListFragment();

                    break;
                case 1:
                    fragment=new ArchiveListFragment();
                    break;

                default:
                    return fragment;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

}
