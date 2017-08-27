package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Lucas Tomasi on 09/04/17.
 */

public class Session {
    private static Session session;
    private SharedPreferences preferences;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Session getInstance(Context context) {
        if (session == null) {
            session = new Session(context);
        }
        return session;
    }

    public void remove(String key) {
        preferences.edit().remove(key).commit();
    }

    public void clear() {
        preferences.edit().clear().commit();

        // não exibe WelcomeActivity
        session.setFirstTimeLaunch(false);
    }

    public void put(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public void put(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public void put(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public void put(String key, long value) {
        preferences.edit().putLong(key, value).commit();
    }

    public void put(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, true);
    }

    public Boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

    public Integer getInt(String key) {
        return getInt(key, 0);
    }

    public Integer getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float def) {
        return preferences.getFloat(key, def);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        try {
            return preferences.getLong(key, def);
        } catch (Exception e) {
            return 0;
        }

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        put(IS_FIRST_TIME_LAUNCH, isFirstTime);
    }

    public boolean isFirstTimeLaunch() {
        return getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}