package com.storelogflog.uk.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

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
