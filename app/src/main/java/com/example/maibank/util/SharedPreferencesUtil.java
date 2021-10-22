package com.example.maibank.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Util class for storing in the app preferences
 */
public class SharedPreferencesUtil {

    /**
     * Private constructor
     */
    private SharedPreferencesUtil(){
        //avoid instantiation
    }

    /**
     * Save a string in the preferences of the app
     * @param toSave The string to be saved
     * @param key The key used for storing. Must be unique
     * @param activity The current activity
     */
    public static void saveToPreferences(String toSave, String key, Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, toSave);
        editor.commit();
    }

    /**
     * Get a string from the app preferences
     * @param key The key to retrieve the string
     * @param activity The current activity
     * @return The string stored with the given key
     */
    public static String get(String key, Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
        return preferences.getString(key,null);
    }
}
