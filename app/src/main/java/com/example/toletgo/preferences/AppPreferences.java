package com.example.toletgo.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    public static void setSecondTimeLogin(Context context, boolean data){
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_second_time",data);
        editor.apply();
    }
    public static boolean getSecondTimeLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_second_time",false);
    }

    public static void setVideoTagAndValue(Context context, String videoTag, boolean videoWatch){
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(videoTag,videoWatch);
        editor.apply();
    }

    public static boolean getVideoWatchUsingVideoTag(Context context,String videoTag){
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(videoTag,false);
    }

}
