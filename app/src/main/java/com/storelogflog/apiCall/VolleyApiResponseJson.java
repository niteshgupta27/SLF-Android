package com.storelogflog.apiCall;
/**
 * Created by Vijendra
 */


import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyApiResponseJson {

    void onAPiResponseSuccess(JSONObject response, int code);

    void onAPiResponseError(VolleyError error, int code);

}
