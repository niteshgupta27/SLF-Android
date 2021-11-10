package com.storelogflog.apputil;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.storelogflog.R;
import com.storelogflog.activity.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public  class Common {
    String TAG = this.getClass().getSimpleName();
    public static String HOME_FRAGMENT= "home";
    public static String DASHBOARD_FRAGMENT= "dashboard";
    public static String ACTIVE_LIST_FRAGMENT= "activeList";
    public static String ADD_AUCTION_ITEM_FRAGMENT= "addAuctionItem";
    public static String ADD_STORAGE_FRAGMENT= "addStorage";
    public static String ARCHIVE_LIST_FRAGMENT= "archiveList";
    public static String BOB_STORAGE_DEATAILS_FRAGMENT= "storageDetails";
    public static String CONTACT_STORAGE_FRAGMENT= "contactStorage";
    public static String CONTACT_US_FRAGMENT= "contactUs";
    public static String FLOG_FRAGMENT= "flog";
    public static String ITEM_DETAILS_FRAGMENT= "itemDetails";
    public static String ITEM_LIST_FRAGMENT= "itemList";
    public static String ITEM_NAME_FRAGMENT= "itemName";
    public static String LOG_FRAGMENT= "log";
    public static String STORAGE_FRAGMENT= "storage";
    public static String STORAGE_YARD_FRAGMENT= "storageYard";
    public static String STORAGE_YARD_VIEW_MORE_FRAGMENT= "storageYardViewMore";
    public static String STORE_FRAGMENT= "store";
    public static String THANK_YOU_FRAGMENT= "thamkYou";
    public static String UNIT_FRAGMENT= "unit";

    public static  void loadFragment(FragmentActivity activty, Fragment fragment, boolean backstack,String tagName) {
        // load fragment0.....

        FragmentTransaction transaction = activty.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);

        if(backstack)
        {
            transaction.addToBackStack(tagName);
        }
        transaction.commit();
    }


    public void removeFragments(FragmentActivity activty) {
        activty.getSupportFragmentManager().popBackStack("F", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public static void removeAllFragment(FragmentActivity activty,String tagName)
    {
        FragmentManager fm = activty.getSupportFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); i++) {
           /* if (!fm.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagName)) {
                fm.popBackStack();
            }*/
            fm.popBackStack();

        }
    }


    public static String getStringFromJsonObj(JSONObject mJsonObject, String key) {
        String response = null;
        try {
            if (mJsonObject != null) {
                if (mJsonObject.has(key)) {
                    response = mJsonObject.getString(key);
                } else {
                    Logger.error("Common_class", "Unable to find string obj key " + key + " in jsonObj " + mJsonObject.toString());
                }
            } else {
                Logger.error("Common_class", "JsonObject is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static int getIntFromJsonObj(JSONObject mJsonObject, String key) {
        int response = -1;
        try {
            if (mJsonObject != null) {
                if (mJsonObject.has(key)) {
                    response = mJsonObject.getInt(key);
                } else {
                    Logger.error("Common_class", "Unable to find string obj key " + key + " in jsonObj " + mJsonObject.toString());
                }
            } else {
                Logger.error("Common_class", "JsonObject is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


}
