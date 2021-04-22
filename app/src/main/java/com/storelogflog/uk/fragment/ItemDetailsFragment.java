package com.storelogflog.uk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.storelogflog.uk.R;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.apiCall.DeleteItemtApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.itemListBean.Item;
import com.storelogflog.uk.bean.itemListBean.Photo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ItemDetailsFragment extends BaseFragment implements VolleyApiResponseString {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private CustomPagerAdapter customPagerAdapter;
    private int[] layouts;
    private TextView[] dots;
    int[] colorsActive;
    int[] colorsInactive;
    private Item item;
    private AppCompatTextView txtItemDescription;
    private AppCompatTextView txtValue;
    private AppCompatTextView txtDimensions;
    private AppCompatTextView txtCategory;
    private AppCompatTextView location1;
    private AppCompatTextView location2;
    private AppCompatTextView location3;
    private AppCompatTextView location4;
    private AppCompatTextView location5;
    private AppCompatTextView location6;
    private AppCompatTextView location7;
    private AppCompatTextView location8;
    private AppCompatImageView imgPlaceHolder;
    private FrameLayout flBanner;
    private Fragment fragment;
    private Bundle bundle;
    private AppCompatTextView txtQuantityValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        viewPager=view.findViewById(R.id.view_pager);
        dotsLayout=view.findViewById(R.id.ll_dots);
        txtItemDescription=view.findViewById(R.id.txt_description);
        txtValue=view.findViewById(R.id.txt_value);
        txtDimensions=view.findViewById(R.id.txt_dimensions);
        txtCategory=view.findViewById(R.id.txt_category);
        imgPlaceHolder=view.findViewById(R.id.img_place_holder);
        flBanner=view.findViewById(R.id.fl_banner);
        txtQuantityValue=view.findViewById(R.id.txt_qty_value);

        location1 =view.findViewById(R.id.txt_location1);
        location2 =view.findViewById(R.id.txt_location2);
        location3 =view.findViewById(R.id.txt_location3);
        location4 =view.findViewById(R.id.txt_location4);
        location5 =view.findViewById(R.id.txt_location5);
        location6 =view.findViewById(R.id.txt_location6);
        location7 =view.findViewById(R.id.txt_location7);
        location8 =view.findViewById(R.id.txt_location8);

        hideShow();


        if(getArguments()!=null)
        {
            item= (Item) getArguments().getSerializable("item");

            if(item!=null)
            {
                ((HomeActivity)getActivity()).enableViews(true,item.getName());

                txtItemDescription.setText(""+item.getDesp());
                txtValue.setText(Constants.pound+""+item.getValue());
                txtQuantityValue.setText(""+item.getQty());

                if(item.getUnit().equals("F"))
                {
                    txtDimensions.setText(item.getLength()+"(L)*"+item.getWidth()+"(B)*"+item.getHeight()+"(H) Feet");
                }
                else if (item.getUnit().equals("I"))
                {
                    txtDimensions.setText(item.getLength()+"(L)*"+item.getWidth()+"(B)*"+item.getHeight()+"(H) Inches");

                }

                if(item.getCategoryList()!=null && item.getCategoryList().size()>0)
                {

                    String categories=",";
                    for(int i=0;i<item.getCategoryList().size();i++)
                    {

                        if(i==0)
                        {
                            categories=item.getCategoryList().get(0).getName();
                        }
                        else
                        {
                            categories=categories+item.getCategoryList().get(0).getName();
                        }
                    }

                    txtCategory.setText(""+categories);

                }

                setLocation(item.getLocation());



                if(item.getPhotos()!=null && item.getPhotos().size()>0)
                {

                    flBanner.setVisibility(View.VISIBLE);
                    imgPlaceHolder.setVisibility(View.GONE);

                    layouts= new int[item.getPhotos().size()];
                    dots= new TextView[item.getPhotos().size()];
                    colorsActive= new int[item.getPhotos().size()];
                    colorsInactive= new int[item.getPhotos().size()];

                    for (int i = 0; i < item.getPhotos().size(); i++) {
                        colorsActive[i] =getResources ().getColor(R.color.dot_active);
                        colorsInactive[i] = getResources ().getColor(R.color.dot_inactive);
                        layouts[i] = R.layout.item_banner_image;
                    }


                    customPagerAdapter = new CustomPagerAdapter(item.getPhotos());
                    viewPager.setAdapter(customPagerAdapter);
                    addBottomDots(0);
                }
                else
                {
                    imgPlaceHolder.setVisibility(View.VISIBLE);
                    flBanner.setVisibility(View.GONE);
                }


            }




        }

    }

    void setLocation(int locationNo)
    {
        if(locationNo==1)
        {
            location1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        else if(locationNo==2)
        {
            location2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==3)
        {
            location3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==4)
        {
            location4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==5)
        {
            location5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==6)
        {
            location6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==7)
        {
            location7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        else if(locationNo==8)
        {
            location8.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
    }

    @Override
    public void initListeners() {

        HomeActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick();
            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                addBottomDots(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {

        try {
            int[] colorsActive = getResources ().getIntArray (R.array.array_dot_active);
            int[] colorsInactive = getResources ().getIntArray (R.array.array_dot_inactive );

            dots = new TextView[layouts.length];


            dotsLayout.removeAllViews ();
            for (int i = 0; i < layouts.length; i++) {
                dots[i] = new TextView (getActivity());
                dots[i].setText ( Html.fromHtml ( "&#8226;" ) );
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorsInactive[currentPage] );
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams ( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                layoutParams.setMargins ( 6, 0, 6, 0 );
                dotsLayout.addView ( dots[i], layoutParams );
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor (colorsActive[currentPage]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideShow()
    {
        HomeActivity.txtToolBarTitle.setVisibility(View.VISIBLE);

        HomeActivity.imgBack.setVisibility(View.VISIBLE);
        HomeActivity.imgMenu.setVisibility(View.GONE);
        HomeActivity.imgSearch.setVisibility(View.GONE);
    }


    void callDeleteItemApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("item",item.getID());

                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new DeleteItemtApiCall(getContext(),this,token, Constants.DELETE_ITEM_CODE);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showToast(getActivity(),"No Internet Connection");
        }
    }


    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code)
        {
            case Constants.DELETE_ITEM_CODE:
                hideLoading();

                if(response!=null)
                {
                    String payload[]=response.split("\\.");
                    if (payload[1]!=null)
                    {
                        response=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"Message");
                            if(result==1)
                            {
                                showToast(getActivity(),message);
                                fragment=new LogFragment();
                                Common.loadFragment(getActivity(),fragment,false,null);

                            }
                            else
                            {
                                showToast(getActivity(),message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
        }


    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {

        switch (code)
        {
            case Constants.DELETE_ITEM_CODE:
                hideLoading();
                break;
        }
    }


    public class CustomPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private List<Photo>photoList;

        public CustomPagerAdapter(List<Photo>photoList) {

            this.photoList=photoList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getActivity().getSystemService ( Context.LAYOUT_INFLATER_SERVICE );

            View view = layoutInflater.inflate (layouts[position], container, false );
            container.addView ( view );
            AppCompatImageView imgBanner=view.findViewById(R.id.img_banner);
            Photo photo=photoList.get(position);
            Utility.loadImage(getActivity(), photo.getURL(),imgBanner);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView ( view );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menu.clear();

        getActivity().getMenuInflater().inflate(R.menu.top_menu,menu);

        MenuItem actionDone=menu.findItem(R.id.action_done);
        actionDone.setVisible(false);

        MenuItem actionSearch=menu.findItem(R.id.action_search);
        actionSearch.setVisible(false);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.action_edit_photo:
                fragment=new PhotoFragment();
                bundle=new Bundle();
                bundle.putString("sotrageId",""+item.getStorageID());
                bundle.putString("itemId",""+item.getID());
                fragment.setArguments(bundle);
                Common.loadFragment(getActivity(),fragment,true,null);
                break;
            case R.id.action_delete_item:
                callDeleteItemApi();
                break;
            case R.id.action_edit_item:

                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
