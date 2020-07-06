package com.storelogflog.uk.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public final class PreferenceManger {
    public static PreferenceManger preferenceManger;
    public static SharedPreferences sharedPreferences;

    private static Gson GSON = new Gson();

    public static PreferenceManger getPreferenceManger() {
        return preferenceManger;
    }

    public static void initPreference(Context context) {
        if (preferenceManger == null) {
            sharedPreferences = context.getSharedPreferences(PrefKeys.PREFERENCENAME, Context.MODE_PRIVATE);
            preferenceManger = new PreferenceManger();
        }
    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setBoolean(String key, boolean b) {
        sharedPreferences.edit().putBoolean(key, b).commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


    public void setObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("key is empty or null");
        }

        sharedPreferences.edit().putString(key, GSON.toJson(object)).commit();

    }


    public <T> T getObject(String key, Class<T> a) {
        String gson = sharedPreferences.getString(key, null);
        if (gson == null) {
            if (a == int.class) {
                return GSON.fromJson("0", a);
            } else if (a == long.class) {
                return GSON.fromJson("0", a);
            } else if (a == double.class) {
                return GSON.fromJson("0", a);
            } else if (a == boolean.class) {
                if (key == "firstTimeOpenObj")
                    return GSON.fromJson("true", a);
                return GSON.fromJson("false", a);
            } else {
                return null;
            }
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storage with key "
                        + key + " is instanceof other class");
            }
        }
    }



    public void resetPreference() {
        sharedPreferences.edit().clear().commit();
    }

    public void clearSession() {
        sharedPreferences.edit().clear().commit();
    }


}
