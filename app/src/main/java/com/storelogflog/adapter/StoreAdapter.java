package com.storelogflog.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.activity.AddStorageActivity;
import com.storelogflog.StorageSelection.fragment.CardsFragment;
import com.storelogflog.StorageSelection.fragment.ManageShelftFragment;
import com.storelogflog.StorageSelection.fragment.RelocateFragment;
import com.storelogflog.activity.PaymentActivity;
import com.storelogflog.apiCall.DeleteStorageApiCall;
import com.storelogflog.apiCall.PricingApiCall;
import com.storelogflog.apiCall.VolleyApiResponseString;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.storelogflog.apputil.Utility;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.dialog.SearchItem;
import com.storelogflog.fragment.ChatingFragment;
import com.storelogflog.fragment.LogFragment;
import com.storelogflog.fragment.StorageDetailsFragment;
import com.storelogflog.fragment.StoreFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.storelogflog.apputil.Common.getIntFromJsonObj;
import static com.storelogflog.apputil.Constants.pound;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder> implements Filterable, VolleyApiResponseString {
    public static int STORE = 1;
    public static int LOG = 2;
    public static int FLOG = 3;
    public ProgressDialog progressDialog;
    String TAG = this.getClass().getSimpleName();
    Fragment fragment;
    Bundle bundle;
    List<Storage> storageList;
    List<Storage> storageListFiltered;
    String itemId;
    StoreFragment StoreFragment;
    AlertDialog alertDialog;
    int pondValue = 0;
    Storage storage1;
    private FragmentActivity activity;
    private int from = -1;


    public StoreAdapter(FragmentActivity activity, List<Storage> storageList, int from, String itemId) {
        this.activity = activity;
        this.from = from;
        this.storageList = storageList;
        this.storageListFiltered = storageList;
        this.itemId = itemId;

        StoreFragment = StoreFragment.instance;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);


    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_storage_yards_comment, parent, false);
        return new StoreHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final StoreHolder holder, int position) {

       final Storage storage = storageListFiltered.get(position);
        holder.txtStorageTitle.setText("" + storage.getName());
        holder.txtDescription.setText("" + storage.getShortDesp());
        if (storage.getUnits()!=null && !TextUtils.isEmpty(storage.getUnits())){
            holder.txtUnitValue.setText("" + storage.getUnits());
            holder.txtUnitValue.setVisibility(View.VISIBLE);
            holder.txt_unit_label.setVisibility(View.VISIBLE);
        }else {
            holder.txtUnitValue.setVisibility(View.GONE);
            holder.txt_unit_label.setVisibility(View.GONE);
        }


//        if (storage.getUnitValue()!=null && !TextUtils.isEmpty(storage.getUnitValue())){
//
//            holder.total_unit_value.setText(storage.getUnitValue());
//
//            holder.total_unit_value.setVisibility(View.VISIBLE);
//        }else {
//            holder.total_unit_label.setVisibility(View.GONE);
//            holder.total_unit_value.setVisibility(View.VISIBLE);
//        }


        if (storage.getStorageType() != null) {
            if (storage.getStorageType().equals("1")) {
                holder.imgStorage.setImageResource(R.drawable.container);
                holder.txtUnitValue.setVisibility(View.VISIBLE);
            } else if (storage.getStorageType().equals("2")) {
                holder.imgStorage.setImageResource(R.drawable.garage_green);
            } else if (storage.getStorageType().equals("3")) {
                holder.imgStorage.setImageResource(R.drawable.shed_green);
            } else if (storage.getStorageType().equals("4")) {
                holder.imgStorage.setImageResource(R.drawable.loft_green);
            } else if (storage.getStorageType().equals("5")) {
                holder.imgStorage.setImageResource(R.drawable.other_green);
            } else if (storage.getStorageType().equals("6")) {
                holder.txtUnitValue.setVisibility(View.VISIBLE);
                if (storage.getDoorColor() != null) {
                    if (storage.getDoorColor().equals("1")) {
                        holder.imgStorage.setImageResource(R.drawable.white_main_door);
                    } else if (storage.getDoorColor().equals("2")) {
                        holder.imgStorage.setImageResource(R.drawable.blue_main_door);
                    } else if (storage.getDoorColor().equals("3")) {
                        holder.imgStorage.setImageResource(R.drawable.red_main_door);
                    } else if (storage.getDoorColor().equals("4")) {
                        holder.imgStorage.setImageResource(R.drawable.green_main_door);
                    } else if (storage.getDoorColor().equals("5")) {
                        holder.imgStorage.setImageResource(R.drawable.yellow_main_door);
                    }
                }

            }
        }

        if (from == STORE) {
            holder.total_unit_label.setText("Total Value: "+ pound);
            holder.total_unit_label.setVisibility(View.VISIBLE);
            holder.total_unit_value.setText(storage.getUnitValue());

            holder.total_unit_value.setVisibility(View.VISIBLE);

            if (storage.getStorageOwner().equals("O")) {
                holder.imgChat.setVisibility(View.VISIBLE);
                holder.img_delete.setVisibility(View.GONE);
            } else if (storage.getStorageOwner().equals("C")) {
                holder.imgChat.setVisibility(View.GONE);

                if (storage.getCheckType().equals("F")) {
                    holder.delete_relative.setVisibility(View.VISIBLE);
                    holder.img_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.delete_relative.setVisibility(View.VISIBLE);
                    holder.img_delete.setVisibility(View.GONE);
                }

            }

            if (!itemId.equals("")) {
                holder.imgLog.setVisibility(View.GONE);
                holder.imgChat.setVisibility(View.GONE);
                holder.img_card.setVisibility(View.GONE);
                holder.delete_relative.setVisibility(View.GONE);
                holder.img_delete.setVisibility(View.GONE);
                holder.img_search.setVisibility(View.GONE);
            }

        } else if (from == LOG) {
            holder.llLogFlogChat.setVisibility(View.GONE);
            holder.img_search.setVisibility(View.GONE);
            holder.total_unit_label.setVisibility(View.GONE);
            holder.total_unit_value.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemId.equals("")) {

                    if (from == STORE) {

                        if (!storage.getSubscribtion().equals("e")) {
                            if (storage.getStorageOwner().equals("O")) {

                                PreferenceManger.getPreferenceManger().setString(PrefKeys.UNITID, storage.getUnitID());
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.STORAGEID, storage.getID() + "");
                                PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_STORAGE_BEAN, storage);

                                fragment = new StorageDetailsFragment();
                                bundle = new Bundle();
                                bundle.putSerializable("storage", storage);
                                fragment.setArguments(bundle);
                                Common.loadFragment(activity, fragment, true, null);

                            } else if (storage.getStorageOwner().equals("C")) {

                                fragment = new CardsFragment();
                                bundle = new Bundle();
                                bundle.putSerializable("storage", storage);
                                fragment.setArguments(bundle);
                                Common.loadFragment(activity, fragment, true, null);


                            }
                        } else {
                            CheckStatus(storage);
                        }
                    } else if (from == LOG) {

                        PreferenceManger.getPreferenceManger().setString(PrefKeys.UNITID, storage.getUnitID());
                        PreferenceManger.getPreferenceManger().setString(PrefKeys.STORAGEID, storage.getID() + "");
                        PreferenceManger.getPreferenceManger().setObject(PrefKeys.USER_STORAGE_BEAN, storage);
                        fragment = new LogFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(activity, fragment, true, null);
                    }
                } else {


                    fragment = new RelocateFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("storage", storage);
                    bundle.putString(Constants.ItemID, itemId);
                    fragment.setArguments(bundle);
                    Common.loadFragment(activity, fragment, true, null);
                }
            }
        });

        holder.imgLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Subscribtion",storage.getSubscribtion());

                if (!storage.getSubscribtion().equals("e")) {
                    fragment = new CardsFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("storage", storage);
                    fragment.setArguments(bundle);
                    Common.loadFragment(activity, fragment, true, null);

                    Log.e("Name",storage.getName());
                } else {
                    CheckStatus(storage);
                }
            }
        });


        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storage.getSubscribtion().equals("e")) {
                    fragment = new ChatingFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("storage", storage);
                    fragment.setArguments(bundle);
                    Common.loadFragment(activity, fragment, true, null);

                } else {
                    CheckStatus(storage);
                }
            }
        });


        holder.img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemId.equals("")) {

                    if (!storage.getSubscribtion().equals("e")) {

                        fragment = new ManageShelftFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("storage", storage);
                        fragment.setArguments(bundle);
                        Common.loadFragment(activity, fragment, true, null);

                    } else {
                        CheckStatus(storage);
                    }

                } else {
                    fragment = new RelocateFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("storage", storage);
                    bundle.putString(Constants.ItemID, itemId);
                    fragment.setArguments(bundle);
                    Common.loadFragment(activity, fragment, true, null);
                }

            }
        });

        holder.delete_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!storage.getSubscribtion().equals("e")) {

                    Intent intent = new Intent(activity, AddStorageActivity.class);
                    intent.putExtra("storage", storage);
                    intent.putExtra("Edit", "Edit Storage");
                    activity.startActivity(intent);

                } else {
                    CheckStatus(storage);
                }

            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDeleteStorage(storage.getID());
            }
        });


        holder.img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchItem(activity, storage.getName()) {
                    @Override
                    public void onClickSubmit(String itemname) {

                        if (!storage.getSubscribtion().equals("e")) {

                            fragment = new CardsFragment();
                            bundle = new Bundle();
                            bundle.putSerializable("storage", storage);
                            bundle.putString("search", itemname);
                            fragment.setArguments(bundle);
                            Common.loadFragment(activity, fragment, true, null);
                        } else {
                            CheckStatus(storage);
                        }

                    }
                };
            }
        });

    }


    private void callDeleteStorage(Integer id) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Do you want delete?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        DeleteStorage(id);
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    private void DeleteStorage(Integer id) {
        if (Utility.isInternetConnected(activity)) {
            try {
                JSONObject jsonObjectPayload = new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("storage_id", String.valueOf(id));

                Logger.debug(TAG, jsonObjectPayload.toString());
                String token = Utility.getJwtToken(jsonObjectPayload.toString());
                new DeleteStorageApiCall(activity, StoreAdapter.this, token, Constants.Delete_storage);
                showLoading("Loading...");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return storageListFiltered.size();
    }

    @Override
    public void onAPiResponseSuccess(String response, int code) {
        switch (code) {
            case Constants.Delete_storage:
                hideLoading();
                if (response != null) {
                    String[] payload = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        Log.e("payload", String.valueOf(payload[1]));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            Log.e("response", response);
                            int result = getIntFromJsonObj(jsonObject, "result");

                            if (result == 1) {

                                StoreFragment.getUserStorageList();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error========>" + e.toString());
                        }
                    }
                }
                break;

            case Constants.PRICING_CODE:
                hideLoading();
                if (response != null) {
                    String payload[] = response.split("\\.");
                    if (payload[1] != null) {
                        response = Utility.decoded(payload[1]);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Logger.debug(TAG, "" + jsonObject.toString());
                            int result = getIntFromJsonObj(jsonObject, "result");
                            if (result == 1) {

                                pondValue = getIntFromJsonObj(jsonObject, "NewStorage");
                                Log.e("pondValue", String.valueOf(pondValue));


                                activity.startActivity(new Intent(activity, PaymentActivity.class).
                                        putExtra("amount", String.valueOf(pondValue)).
                                        putExtra("storage", storage1).
                                        putExtra("FROM", "StorageRenew"));

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

        switch (code) {
            case Constants.Delete_storage:
                hideLoading();

        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    storageListFiltered = storageList;
                } else {
                    ArrayList<Storage> filteredStorageList = new ArrayList<>();


                    for (Storage model : storageList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredStorageList.add(model);
                        }
                    }
                    storageListFiltered = filteredStorageList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = storageListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                storageListFiltered = (ArrayList<Storage>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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

    private void CheckStatus(Storage storage) {

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cam_permission,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        TextView facebook_txt = layout.findViewById(R.id.facebook_txt);
        TextView allow_txt = layout.findViewById(R.id.allow_txt);
        TextView Cancle = layout.findViewById(R.id.btn_cancle);
        TextView btn_Ok = layout.findViewById(R.id.btn_OK);

        Cancle.setText(activity.getResources().getString(R.string.Cancel));
        btn_Ok.setText(activity.getResources().getString(R.string.continued));
        facebook_txt.setText(activity.getResources().getString(R.string.app_name));
        allow_txt.setText(activity.getResources().getString(R.string.subscription_expiered));

             storage1 = storage;
        alertDialog.show();

        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                callPricingApi();
            }
        });
    }

    void callPricingApi() {
        if (Utility.isInternetConnected(activity)) {
            try {
                new PricingApiCall(activity, this, null, Constants.PRICING_CODE);
                showLoading("Loading...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public class StoreHolder extends RecyclerView.ViewHolder {

        ImageView img_search;
        private CardView cardView;
        private LinearLayout llLogFlogChat;
        private AppCompatImageView imgStorage, img_delete;
        private AppCompatTextView txtStorageTitle;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txt_unit_label,txtUnitValue,total_unit_label,total_unit_value;
        private RelativeLayout imgChat, img_card, imgLog, delete_relative;

        public StoreHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            llLogFlogChat = itemView.findViewById(R.id.ll_chat_log_flog);
            txtDescription = itemView.findViewById(R.id.txt_des);
            imgStorage = itemView.findViewById(R.id.img_storage);
            txtStorageTitle = itemView.findViewById(R.id.txt_title);
            txt_unit_label = itemView.findViewById(R.id.txt_unit_label);
            txtUnitValue = itemView.findViewById(R.id.txt_unit_value);
            imgLog = itemView.findViewById(R.id.img_log);
            imgChat = itemView.findViewById(R.id.img_chat);
            img_card = itemView.findViewById(R.id.img_card);
            delete_relative = itemView.findViewById(R.id.delete_relative);
            img_delete = itemView.findViewById(R.id.img_delete);
            img_search = itemView.findViewById(R.id.img_search);
            total_unit_label = itemView.findViewById(R.id.total_unit_label);
            total_unit_value = itemView.findViewById(R.id.total_unit_value);
        }
    }


}
