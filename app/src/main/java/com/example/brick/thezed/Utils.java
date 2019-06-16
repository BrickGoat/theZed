package com.example.brick.thezed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.Switch;

import static android.content.Context.MODE_PRIVATE;

public class Utils
{
    private static boolean sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_BLACK = 1;
    public boolean switchValue;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, boolean theme)
    {
        sTheme = theme;
        SharedPreferences.Editor editor = activity.getApplicationContext().getSharedPreferences("switch", MODE_PRIVATE).edit();
        editor.putBoolean("switchkey", theme);
        editor.apply();
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static boolean onActivityCreateSetTheme(Activity activity)
    {
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("switch", MODE_PRIVATE);
        boolean silent = settings.getBoolean("switchkey", true);
        sTheme = silent;
        if (sTheme){
            activity.setTheme(R.style.AppTheme);
        }else {activity.setTheme(R.style.AppThemeDark);}
        return silent;
    }
    public void setSwitchValue(boolean x){
        switchValue = x;
    }
}
