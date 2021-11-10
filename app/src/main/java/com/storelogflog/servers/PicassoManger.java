package com.storelogflog.servers;

import android.content.Context;

import com.squareup.picasso.Picasso;

public class PicassoManger {
    private  static Picasso mInstance = null;
    public static Picasso getPicassoInstance() {
        return mInstance;
    }

    public static void initPicassoInstance(Context mcontex) {
        if (mInstance == null)
            mInstance=PicassoTrustAll.getInstance(mcontex);
    }
}
