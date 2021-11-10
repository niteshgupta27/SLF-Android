package com.storelogflog.apiCall;
/**
 * Created by Vijendra
 */

import com.android.volley.VolleyError;

public interface VolleyApiResponseString {

     void onAPiResponseSuccess(String response, int code);

     void onAPiResponseError(VolleyError error, int code);

}
