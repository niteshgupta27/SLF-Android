package com.storelogflog.uk;

import android.app.Application;

import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.servers.PicassoManger;
import com.stripe.android.PaymentConfiguration;

public class StoreLogFlogApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManger.initPreference(getApplicationContext());
        PicassoManger.initPicassoInstance(getApplicationContext());
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_live_zGBP0ZoG0tg6J0focqhzxHnm008rainrKD"
        );





    }
}
