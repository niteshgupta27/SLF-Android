package com.storelogflog.activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.FirebaseApp;

import com.google.firebase.messaging.FirebaseMessaging;
import com.storelogflog.R;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.PrefKeys;
import com.storelogflog.apputil.PreferenceManger;
import com.stripe.android.PaymentConfiguration;


public class SplashActivity extends AppCompatActivity {
    
    private  final String TAG = getClass().getSimpleName();
    
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;

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

       
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
    
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(
                            (com.google.android.gms.tasks.OnCompleteListener<String>) task -> {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                        
                                // Get new FCM registration token
                                String FirebaseToken = task.getResult();
                                Log.e("newToken", FirebaseToken);
                                PreferenceManger.getPreferenceManger().setString(PrefKeys.FCMTOKEN,FirebaseToken);
                                // Log and toast
                              
                                
                            });
           
    
        }
        ChangScreen();
    
        
    
    }

    private void ChangScreen() {

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


    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                Log.e("download status","FLEXIBLE");
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.e("download status2","DOWNLOADED");
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, SplashActivity.FLEXIBLE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Update success", Toast.LENGTH_LONG).show();
                ChangScreen();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)

                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                        Log.e("download status","completeUpdate");
                        ChangScreen();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeInstallStateUpdateListener();
    }

}
//https://www.section.io/engineering-education/android-application-in-app-update-using-android-studio/


