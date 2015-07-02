package com.clover.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dan on 2015/6/29.
 */
public class SharePreferenceUtils {
    private  SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    private  String SHARED_KEY_LOGIN = "shared_key_login";
    private  String SHARED_KEY_Relationship = "shared_key_relation";

    public  SharePreferenceUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mSharedPreferences.edit();

    }

    public  boolean getBooleanLogin(){
        return mSharedPreferences.getBoolean(SHARED_KEY_LOGIN, false);
    }

    public  void setBooleanLogin(boolean islogin) {
        editor.putBoolean(SHARED_KEY_LOGIN, islogin);
    }


}
