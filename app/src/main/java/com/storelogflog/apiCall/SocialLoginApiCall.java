package com.storelogflog.apiCall;
/**
 * Created by Vijendra Vishwakarma
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storelogflog.apputil.Constants;
import com.storelogflog.apputil.Logger;
import com.storelogflog.apputil.Utility;

import java.util.HashMap;
import java.util.Map;

public class SocialLoginApiCall {
    String TAG = this.getClass().getSimpleName();
    Context mContext;
    VolleyApiResponseString volleyApiResponseString;

    public SocialLoginApiCall(Context mContext, VolleyApiResponseString volleyApiResponseString, String token, int serverCode) {
        this.mContext = mContext;
        this.volleyApiResponseString=volleyApiResponseString;
        callApi(token,serverCode);
    }

    private void callApi(final String token, final int serverCode) {

        Logger.debug(TAG,""+Constants.BASE_URL+Constants.API_SOCIAL_LOGIN);
        Logger.debug(TAG,"Request_Token="+token);

        StringRequest apiRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL+Constants.API_SOCIAL_LOGIN, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                Log.e("Social_Response", response);
                Logger.debug(TAG,"Response_Token="+response);
                volleyApiResponseString.onAPiResponseSuccess(response,serverCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Social_Response",  Utility.returnErrorMsg(error,mContext));

                Logger.debug(TAG,"Response_Token="+ Utility.returnErrorMsg(error,mContext));
                volleyApiResponseString.onAPiResponseError(error,serverCode);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("data", token);
                Log.e("token",token);
                return params;
            }
        };
        apiRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(apiRequest);

    }

}
