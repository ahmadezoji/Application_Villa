package com.apsent.villapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Utils {
    public static final String PFREFRENCE_USER_LOGIN = "user";
    public static final String PFREFRENCE_USER_LOGIN_KEY = "user_phone";

    public static void writePreferences(Context context,String sharedName,String key,String value) {
        SharedPreferences prefs = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);

        editor.apply();
    }
    public static String readPrefrences(Context context,String sharedName,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return prefs.getString(key,"");
    }
}
