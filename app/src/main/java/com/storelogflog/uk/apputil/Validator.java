package com.storelogflog.uk.apputil;

import android.content.Context;


import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.regex.Pattern;

public class Validator {

    private static final String MOBILE_NO_PATTERN = "^[+]?[0-9]{10,15}$";

    public static boolean isEmailValid(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    /*public static boolean isValidMobileNo(final String hex) {
        return hex.matches(MOBILE_NO_PATTERN);
    }*/

    public static boolean isValidMobileNo(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 10 || phone.length() > 20) {
                // if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    public static boolean isUrlValid(Context context, String url, String error_msg) {

        if (URLUtil.isValidUrl(url)) {

            return true;
        } else return false;
    }

    public static boolean isFiledValidated(EditText... editTexts) {
        boolean b = true;
        int position=0;
        for (EditText et : editTexts) {
            if (TextUtils.isEmpty(et.getText().toString())) {

                et.setError("Enter " +et.getHint().toString().toLowerCase());
                if (position==0)
                {
                    position++;
                    et.requestFocus();
                }
                b = false;
            }
        }

        return b;
    }

    public static boolean isTextVaild(Context context, String text, String error_msg) {
        if (text.equals("") || text.equals(null) || text.equals("null") || text.equals("[]")) {
            Toast.makeText(context, "Please enter " + error_msg, Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public static boolean isDateValid(Context context, String date, String error_msg) {

        return false;

    }

    public static boolean isAppCompatEditText(String text, String error_msg, AppCompatEditText editText) {
        if (text.equals("") || text.equals(null) || text.equals("null")) {
            editText.setError("Enter " + error_msg);
            return false;
        } else return true;
    }

    public static boolean isFieldContainsSpace(String text, String error_msg, EditText editText) {
        if (text != null && text.contains(" ")) {
            editText.setError(error_msg);
            return false;
        }

        return true;
    }

    public static boolean isAppCompatText(String text, String error_msg, AppCompatTextView editText) {
        if (text.equals("") || text.equals(null) || text.equals("null")) {
            editText.setError("Enter " + error_msg);
            return false;
        } else return true;
    }

}
