package com.storelogflog.adapter;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.apiCall.AddCategoryApiCall;
import com.storelogflog.apiCall.EditCategoryApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.categoryBean.Category;
import com.storelogflog.bean.photoBean.Photo;
import com.storelogflog.dialog.AddCategory;
import com.storelogflog.dialog.EditCategory;
import com.storelogflog.fragment.AddItemFragment;
import com.storelogflog.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.storelogflog.apputil.ServerCode.Edit_CATEGORY_CODE;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ActiveListHolder> implements VolleyApiResponseString {
    String TAG = this.getClass().getSimpleName();
    FragmentActivity activity;
    private List<Category>categoryList;
    private ArrayList<String>categoryIdList;
    public ProgressDialog progressDialog;
    RecyclerView.ViewHolder viewHolder;
    AddItemFragment addItemFragment;

    public CategoryItemAdapter(FragmentActivity activity,List<Category>categoryList) {
        this.activity = activity;
        this.categoryList=categoryList;
        categoryIdList=new ArrayList<>();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);

        addItemFragment =  AddItemFragment.instance;
    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_category,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveListHolder holder, int position) {

        viewHolder = holder;

        final Category category=categoryList.get(position);
        holder.cbCategory.setText(category.getName());

                if (category.getType().equals("C")){
                    holder.edit_Category.setVisibility(View.VISIBLE);
                }else {
                    holder.edit_Category.setVisibility(View.GONE);
                }


                holder.edit_Category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new EditCategory(activity,category.getName()) {
                            @Override
                            public void onClickSubmit(String categoryName) {


                                callEditCategory(categoryName,category.getID());

                            }
                        };
                    }
                });



        holder.cbCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                  categoryIdList.add(category.getID());
                }
                else
                {
                    categoryIdList.remove(category.getID());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {

        switch (code){
            case Edit_CATEGORY_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("edit_category_response",jsonObject.toString());
                           int result = Common.getIntFromJsonObj(jsonObject, "result");
                            String msg = Common.getStringFromJsonObj(jsonObject, "Message");
                            if (result == 1) {


                                     addItemFragment.callGetCategoryList();

                            } else {
                                Toast.makeText(activity, msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    @Override
    public void onAPiResponseError(VolleyError error, int code) {
        switch (code){
            case Edit_CATEGORY_CODE:
                hideLoading();
                break;
        }

    }

    public class ActiveListHolder extends RecyclerView.ViewHolder
    {
          private AppCompatCheckBox cbCategory;
          private RelativeLayout edit_Category;

        public ActiveListHolder(@NonNull View itemView) {
            super(itemView);
            cbCategory = itemView.findViewById(R.id.cb_category);
            edit_Category = itemView.findViewById(R.id.edit_Category);


        }
    }

    public ArrayList<String>getSelectedCatId()
    {
        return categoryIdList;
    }


    void callEditCategory(String catName, String id) {
        if (Utility.isInternetConnected(activity)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("category_id", id);
                jsonObjectPayload.put("category_name", catName);
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                showLoading("Loading...");
                new EditCategoryApiCall(activity, this, token, Edit_CATEGORY_CODE);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(activity,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }


    public void showLoading(String message) {
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
