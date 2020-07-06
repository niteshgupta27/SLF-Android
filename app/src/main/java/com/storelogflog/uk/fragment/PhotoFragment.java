package com.storelogflog.uk.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.storelogflog.uk.R;
import com.storelogflog.uk.StorageSelection.fragment.CardsFragment;
import com.storelogflog.uk.activity.HomeActivity;
import com.storelogflog.uk.activity.ProfileActivity;
import com.storelogflog.uk.adapter.ActiveListAdapter;
import com.storelogflog.uk.adapter.PhotoAdapter;
import com.storelogflog.uk.apiCall.DeleteItemPhotoApiCall;
import com.storelogflog.uk.apiCall.PhotoListApiCall;
import com.storelogflog.uk.apiCall.StorageClaimApiCall;
import com.storelogflog.uk.apiCall.ViewItemApiCall;
import com.storelogflog.uk.apiCall.VolleyApiResponseString;
import com.storelogflog.uk.apputil.Common;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Logger;
import com.storelogflog.uk.apputil.PrefKeys;
import com.storelogflog.uk.apputil.PreferenceManger;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.apputil.VolleyMultipartRequest;
import com.storelogflog.uk.bean.photoBean.PhotoListBean;
import com.storelogflog.uk.bean.storageBean.Storage;
import com.storelogflog.uk.callBackInterFace.DrawerLocker;
import com.storelogflog.uk.callBackInterFace.RemovePhoto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


public class PhotoFragment extends BaseFragment implements View.OnClickListener, VolleyApiResponseString {
    private static final String TAG = PhotoFragment.class.getSimpleName();
    private static final int RequestPermissionCode = 3, REQUEST_IMAGE_CAPTURE = 101, PICK_IMAGE_REQUEST = 1;
    private AppCompatTextView txtAddPhoto;
    private AppCompatTextView txtContinue;
    private RecyclerView rvPhotoList;
    private PhotoAdapter adapter;
    public static int PICK_IMAGE_CAMERA=1001;
    File photoFile;
    String storageId="";
    String itemid="";
    Storage storage;
    private FloatingActionButton fabAddPhoto;
    private AppCompatTextView txtErrorMsg;
    Context mContext;
    private String Buttonclick = "", value = "";
     AlertDialog alertDialog;
    private Uri imageUri;
    RelativeLayout rl_submit;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_photos, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {

        rvPhotoList=view.findViewById(R.id.rv_photo_list);
        fabAddPhoto=view.findViewById(R.id.fab_add_photo);
        txtErrorMsg=view.findViewById(R.id.txt_error_msg);
        rl_submit = view.findViewById(R.id.rl_submit);

        ((HomeActivity)getActivity()).enableViews(true,"Photo List");

                 mContext = getActivity();
        if (getArguments() != null) {

            storageId =  getArguments().getString("sotrageId");
            itemid =  getArguments().getString("itemId");
            storage = (Storage) getArguments().getSerializable("storage");
            Log.e("storageId",""+storageId);
            Log.e("itemid",""+itemid);
            callPhotoListApi();

        }

    }

    @Override
    public void initListeners() {

        fabAddPhoto.setOnClickListener(this);
        rl_submit.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.fab_add_photo:
               // requestCameraAndStoragePermission(PICK_IMAGE_CAMERA,getActivity());

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted

                    selectImage();
                } else {

                    if (Buttonclick.equals("")) {
                        Buttonclick = "1";
                        CheakPermissions();
                    } else if (Buttonclick.equals("1")) {
                        Buttonclick = "2";
                        AccessCamera();
                    } else if (Buttonclick.equals("2")) {
                        Buttonclick = "3";
                        AccessCamera();
                    } else {
                        AccessCamera();
                    }
                }
                break;

            case    R.id.rl_submit:
            Fragment fragment = new CardsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("position",getArguments().getString("position"));
            bundle.putSerializable("storage", storage);
            fragment.setArguments(bundle);
            Common.loadFragment(getActivity(), fragment, false, null);
            break;

        }
    }


    private void selectImage() {

        LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.edit_cam,
                null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);


        CardView Take_Photo = layout.findViewById(R.id.Take_Photo);
        CardView Gallary = layout.findViewById(R.id.Gallary);
        CardView cancle = layout.findViewById(R.id.cancle);

        Take_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraIntent();

                alertDialog.dismiss();
            }
        });

        Gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
                alertDialog.dismiss();
            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
    }

    private void cameraIntent() {


        ContentValues values = new ContentValues();
        imageUri = mContext.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private void AccessCamera() {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cam_permission,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);


        TextView Cancle = layout.findViewById(R.id.btn_cancle);
        TextView btn_Ok = layout.findViewById(R.id.btn_OK);


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
                CheakPermissions();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            try {
                Bitmap  imageBitmap = MediaStore.Images.Media.getBitmap(
                        mContext.getContentResolver(), imageUri);

                    Uri tempUri = getImageUri(getActivity(), imageBitmap);
                    String imagepath = getPath(getActivity(), tempUri);
                    photoFile = new File(imagepath);
                    callAddItemPhotoApi(imageBitmap);



                } catch (Exception e) {
                    e.printStackTrace();
                }



        }else  if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {


             Bitmap   bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), filePath);
                Uri tempUri = getImageUri(getActivity(), bitmap);
                String imagepath = getPath(getActivity(), tempUri);
                photoFile = new File(imagepath);
                callAddItemPhotoApi(bitmap);

            }catch (Exception e){
                e.printStackTrace();
            }


            }
    }


    void callAddItemPhotoApi(Bitmap bitmap)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("storage",""+storageId);
                jsonObjectPayload.put("item",""+itemid);


                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                uploadImageToServer(bitmap,token);
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


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "KW", null);
        return Uri.parse(path);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private void uploadImageToServer(final Bitmap bitmap, final String token) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.BASE_URL+Constants.API_add_item_photos, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                hideLoading();
                String resultResponse = new String(response.data);

                if(resultResponse!=null)
                {
                    String payload[]=resultResponse.split("\\.");
                    if (payload[1]!=null)
                    {
                        resultResponse=Utility.decoded( payload[1]);
                        try {
                            JSONObject jsonObject=new JSONObject(resultResponse);
                            Logger.debug(TAG,""+jsonObject.toString());
                            int result=getIntFromJsonObj(jsonObject,"result");
                            String message=getStringFromJsonObj(jsonObject,"Message");
                            if(result==1)
                            {
                               showToast(getContext(),message);
                               callPhotoListApi();
                            }
                            else
                            {
                                showToast(getContext(),message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast(getActivity(),"Error: "+ error.getMessage());
                Log.e(TAG, "error"+ error.getMessage());

                hideLoading();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
              //  params.put("cAuthKey",login.getAuthkey());
                //Log.e(TAG, "params"+ params);
                return params;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("data",token);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> data = new HashMap<>();
                long image_name = System.currentTimeMillis();
                data.put("image", new VolleyMultipartRequest.DataPart(image_name + ".jpeg", getFileDataFromDrawable(bitmap)));
                return data;

            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 0));
        Volley.newRequestQueue(getActivity()).add(multipartRequest);


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    void callPhotoListApi()
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("item",""+itemid);


                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new PhotoListApiCall(getContext(),this,token, Constants.PHOTO_LIST_CODE);
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

    void callDeletePhotoApi(int photoId)
    {
        if(Utility.isInternetConnected(getActivity()))
        {
            try {
                JSONObject jsonObjectPayload=new JSONObject();
                jsonObjectPayload.put("apikey", PreferenceManger.getPreferenceManger().getString(PrefKeys.APIKEY));
                jsonObjectPayload.put("photo",photoId);

                Logger.debug(TAG,jsonObjectPayload.toString());
                String token=Utility.getJwtToken(jsonObjectPayload.toString());
                new DeleteItemPhotoApiCall(getContext(),this,token, Constants.DELETE_ITEM_PHOTO_CODE);
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
            case Constants.PHOTO_LIST_CODE:
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
                           // String message=getStringFromJsonObj(jsonObject,"message");
                            if(result==1)
                            {
                                PhotoListBean photoListBean=new Gson().fromJson(response.toString(),PhotoListBean.class);
                                if(photoListBean!=null && photoListBean.getPhotos() !=null && photoListBean.getPhotos().size()>0)
                                {

                                    rvPhotoList.setVisibility(View.VISIBLE);
                                    txtErrorMsg.setVisibility(View.GONE);

                                    adapter = new PhotoAdapter(getActivity(), photoListBean.getPhotos(), new RemovePhoto() {
                                        @Override
                                        public void deletePhotoListner(int photoId) {

                                              callDeletePhotoApi(photoId);
                                        }
                                    });
                                    rvPhotoList.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                                    rvPhotoList.setAdapter(adapter);

                                    rvPhotoList.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    rvPhotoList.setVisibility(View.GONE);
                                    txtErrorMsg.setVisibility(View.VISIBLE);
                                }

                            }
                            else
                            {
                                rvPhotoList.setVisibility(View.GONE);
                                txtErrorMsg.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            rvPhotoList.setVisibility(View.GONE);
                            txtErrorMsg.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        rvPhotoList.setVisibility(View.GONE);
                        txtErrorMsg.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    rvPhotoList.setVisibility(View.GONE);
                    txtErrorMsg.setVisibility(View.VISIBLE);
                }

                break;

            case Constants.DELETE_ITEM_PHOTO_CODE:
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
                              callPhotoListApi();
                            }
                            else
                            {
                                Utility.commonMsgDialog(getContext(), "" + message, true, null);
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
            case Constants.PHOTO_LIST_CODE:
                hideLoading();
                rvPhotoList.setVisibility(View.GONE);
                txtErrorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText(""+Utility.returnErrorMsg(error,getActivity()));
                break;

            case Constants.DELETE_ITEM_PHOTO_CODE:
                hideLoading();
                break;
        }
    }

 /*   @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.top_menu,menu);

        MenuItem actionEditItem=menu.findItem(R.id.action_edit_item);
        actionEditItem.setVisible(false);

        MenuItem actionEditPhoto=menu.findItem(R.id.action_edit_photo);
        actionEditPhoto.setVisible(false);

        MenuItem actionDeletePhoto=menu.findItem(R.id.action_delete_item);
        actionDeletePhoto.setVisible(false);

        MenuItem actionSearch=menu.findItem(R.id.action_search);
        actionSearch.setVisible(false);



        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {



        return super.onOptionsItemSelected(menuItem);
    }*/

    private void CheakPermissions() {
        if (checkPermission()) {
        } else {
            requestPermission();
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {
                        CAMERA,
                        WRITE_EXTERNAL_STORAGE


                }, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {


                    boolean ACCESSCAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (ACCESSCAMERA && WRITE_EXTERNAL_STORAGE) {
                        /*Snackbar.make(findViewById(R.id.SubmitFormRelatie), "Permission Allow", Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();*/

                    } else {
                       /* Snackbar.make(findViewById(R.id.SubmitFormRelatie), "Permission Denied", Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();*/
                    }
                }

                break;
        }
    }

    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(mContext, CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
