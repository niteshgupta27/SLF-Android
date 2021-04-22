package com.storelogflog.uk.activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.stripe.android.PaymentConfiguration;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_live_zGBP0ZoG0tg6J0focqhzxHnm008rainrKD"
        );

        FirebaseApp.initializeApp(this);
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String FirebaseToken = instanceIdResult.getToken();
                Log.e("newToken", FirebaseToken);
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (PreferenceManger.getPreferenceManger().getBoolean(PrefKeys.ISLOGIN))
               {
                   startActivity(new Intent(SplashActivity.this, HomeActivity.class)
                           .putExtra("From", Constants.FROM_SPLASH_SCREEN));
               }
               else
               {
                   startActivity(new Intent(SplashActivity.this, LoginActivity.class));
               }

                finish();

            }
        }, 1000);
    }
}
