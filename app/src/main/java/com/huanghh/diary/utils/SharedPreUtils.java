package com.huanghh.diary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreUtils {
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPre;

    private static SharedPreUtils ourInstance = null;

    public static SharedPreUtils getInstance(String spName, Context context) {
        if (ourInstance == null) ourInstance = new SharedPreUtils(spName, context);
        return ourInstance;
    }

    @SuppressLint("CommitPrefEdits")
    private SharedPreUtils(String spName, Context context) {
        mSharedPre = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        mEditor = mSharedPre.edit();
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public float getFloat(String key) {
        return mSharedPre.getFloat(key, 0);
    }

    public int getInt(String key) {
        return mSharedPre.getInt(key, 0);
    }

    public String getString(String key) {
        return mSharedPre.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mSharedPre.getBoolean(key, false);
    }
}
