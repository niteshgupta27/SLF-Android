package com.storelogflog.uk.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.storelogflog.uk.activity.BaseActivity;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.listeners.OnBackHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public abstract class BaseFragment extends Fragment implements OnBackHandler {

    String TAG = this.getClass().getSimpleName();
    public abstract void initViews(View view);

    public abstract void initListeners();

    public  static  AppCompatActivity onBackHandler;
    public ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackHandler)
            onBackHandler = (AppCompatActivity) context;


    }

    @Override
    public void onBackClick() {
        if (onBackHandler != null)
            onBackHandler.onBackPressed();
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

    public String getStringFromJsonObj(JSONObject mJsonObject, String key) {
        String response = null;
        try {
            if (mJsonObject != null) {
                if (mJsonObject.has(key)) {
                    response = mJsonObject.getString(key);
                } else {
                    Logger.error(TAG, "Unable to find string obj key " + key + " in jsonObj " + mJsonObject.toString());
                }
            } else {
                Logger.error(TAG, "JsonObject is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    public int getIntFromJsonObj(JSONObject mJsonObject, String key) {
        int response = -1;
        try {
            if (mJsonObject != null) {
                if (mJsonObject.has(key)) {
                    response = mJsonObject.getInt(key);
                } else {
                    Logger.error(TAG, "Unable to find string obj key " + key + " in jsonObj " + mJsonObject.toString());
                }
            } else {
                Logger.error(TAG, "JsonObject is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    boolean showErrorMsg(EditText editText, String msg)
    {
        editText.setError(""+msg);
        editText.requestFocus();
        return false;
    }

    public void showToast(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }



    public  void  requestCameraAndStoragePermission(final int code, final Activity activity) {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            takePictureIntent(code,activity);

                        }else if(report.isAnyPermissionPermanentlyDenied()){
                            showSettingsDialog(activity);
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void takePictureIntent(int code,Activity activity)  {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, code);
        }
    }


    private void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }


}
