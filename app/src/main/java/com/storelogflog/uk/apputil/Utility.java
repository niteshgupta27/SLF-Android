package com.storelogflog.uk.apputil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.storelogflog.uk.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utility {
    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM");
    public static final SimpleDateFormat outputDate = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat noramlDate = new SimpleDateFormat("MM-dd-yyyy");
    public static final SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm a");
    public static final SimpleDateFormat outputDate2 = new SimpleDateFormat("dd MMM yyyy");


    public static Dialog dialog;

    public static void dividerOnTabs(View root, Context mContex) {
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(mContex.getResources().getColor(R.color.colorAccent));
            drawable.setSize(1, 2);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }

    public static boolean setError(AppCompatEditText edt, String errormsg) {
        edt.setError(errormsg);
        edt.requestFocus();
        return false;
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getTimeAgoString(String dateStr) {

        try {
            Date date = inputFormat.parse(dateStr);
            String niceDateStr = (String) DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
            return niceDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;


    }

    public static void loadImage(Activity homeActivity, String path, ImageView imageView) {
        try {
            Glide.with(homeActivity)
                    .load(path)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static String getBirthDate(String rowDate) {
        String date = "";
        try {
            Date parseDate = inputFormat.parse(rowDate);
            date = noramlDate.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }

    public static String getDate(String rowDate) {
        String date = "";
        try {
            Date parseDate = inputFormat.parse(rowDate);
            date = outputDate.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }

    public static String getDateWithMonthName(String rowDate) {
        String date = "";
        try {
            Date parseDate = inputFormat.parse(rowDate);
            date = outputDate2.format(parseDate);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }

    public static String getTime(String rowDate) {
        String date = "";
        try {
            Date parseDate = inputFormat.parse(rowDate);
            date = outputTime.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }


    public static String getFromatedDate(String rowDate) {
        String date = "";
        try {
            Date parseDate = inputFormat.parse(rowDate);
            date = outputDate.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return date;
    }


    public static void getDate(final FragmentActivity activity, final TextView textView) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = "" + dayOfMonth + "-" + (month + 1) + "-" + year;
                        textView.setText(selectedDate);
                      /*  String selectedDate="" + dayOfMonth + "-" + month+ "-" + year;
                        if (Utility.isFutureDateSelected(selectedDate)) {
                            textView.setText("");
                            DialogWindow.showToast(activity, "Select current or past date");
                        } else
                        {

                        }*/

                    }
                },
                year,
                month,
                day);
        datePickerDialog.show();

    }

    public static String removeDoubleQouts(String title) {
        if (title == null)
            return "";
        return title.replaceAll("^\"|\"$", "");

    }

    public static boolean isFutureDateSelected(String selectedDate) {
        boolean isFutureDate = false;
        try {
            Date parse = noramlDate.parse(selectedDate);
            Date currentDate = Calendar.getInstance().getTime();
            if (parse.compareTo(currentDate) > 0)
                isFutureDate = true;
            else isFutureDate = false;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isFutureDate;
    }


    public static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;

    }


    public static boolean isDateOutdated(String dateF, String dateS) {
        boolean isOutdated = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
            Date date = null;
            Date date2 = null;
            date = sdf.parse(dateF);
            date2 = sdf.parse(dateS);

            if (date.after(date2))
                isOutdated = false;
            else isOutdated = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isOutdated;

    }


    public static String returnErrorMsg(VolleyError error, Context context)
    {
        String errorMsg="Data not found!";

        if (error instanceof TimeoutError) {
            //  Toast.makeText(context, "Timeout!",Toast.LENGTH_LONG).show();
            errorMsg="Timeout error!";
        }
        if (error instanceof NoConnectionError) {
            //  Toast.makeText(context, "Timeout!",Toast.LENGTH_LONG).show();
            errorMsg="No connection error!";
        }
        else if (error instanceof AuthFailureError) {
            errorMsg="Authentication error!";
        } else if (error instanceof ServerError) {
            errorMsg="Server Error!";
        } else if (error instanceof NetworkError) {
            errorMsg="Network Error!";
        } /*else if (error instanceof ParseError) {
              //TODO
          }*/

        return errorMsg;
    }


    public  static boolean isInternetConnected(Context context)
    {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            }
            else
            {
                connected = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connected;
    }

    public static class BadDoubleDeserializer implements JsonDeserializer<Double> {

        @Override
        public Double deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            try {
                return Double.parseDouble(element.getAsString().replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new JsonParseException(e);
            }
        }

    }


    public static final TypeAdapter<Number> UNRELIABLE_INTEGER = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            JsonToken jsonToken = in.peek();
            switch (jsonToken) {
                case NUMBER:
                case STRING:
                    String s = in.nextString();
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException ignored) {
                    }
                    try {
                        return (int)Double.parseDouble(s);
                    } catch (NumberFormatException ignored) {
                    }
                    return null;
                case NULL:
                    in.nextNull();
                    return null;
                case BOOLEAN:
                    in.nextBoolean();
                    return null;
                default:
                    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
            }
        }
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };
    public static final TypeAdapterFactory UNRELIABLE_INTEGER_FACTORY = TypeAdapters.newFactory(int.class, Integer.class, UNRELIABLE_INTEGER);


    public static String getJwtToken(String payload) {

        //The JWT signature algorithm we will be using to sign the token
        //We will sign our JWT with our ApiKey secret
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY.getBytes());

        return builder.compact();
    }

    public static String decoded(String encryptedText)  {

        String plainText = null;
        try {

            byte[] decordedValue = Base64.decode(encryptedText, Base64.DEFAULT);
            plainText = new String(decordedValue, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("D: " + plainText);
        return plainText;
    }


    public static void commonMsgDialog(final Context context, String message,boolean isDismiss,View.OnClickListener onClickListener)
    {
         dialog  = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.common_confirm_popup);
        dialog.setCancelable(true);
        TextView title=(TextView)dialog.findViewById(R.id.txt_title);
        title.setText(message);
        Button yes = (Button) dialog.findViewById(R.id.sign_out_1);


        yes.setOnClickListener(onClickListener);

        if(isDismiss)
        {
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
        }


        dialog.show();

    }



}
