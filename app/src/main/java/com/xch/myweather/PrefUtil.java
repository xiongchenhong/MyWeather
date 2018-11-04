package com.xch.myweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: PrefUtil</p>
 * <p>Description: PrefUtil</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-21
 */
class PrefUtil {

    private static final String PREF_KEY_WEATHER = "pref_weather_info";
    private static final String PREF_KEY_IMG_URL = "pref_img_url";

    private static SharedPreferences mSharedPreference;

    static String getWeatherInfo(Context context) {
        return getPreference(context).getString(PREF_KEY_WEATHER, null);
    }

    static void setWeatherInfo(Context context, String weatherInfo) {
        getPreference(context).edit().putString(PREF_KEY_WEATHER, weatherInfo).apply();
    }

    static String getImgUrl(Context context) {
        return getPreference(context).getString(PREF_KEY_IMG_URL, null);
    }

    static void setImgUrl(Context context, String imgUrl) {
        getPreference(context).edit().putString(PREF_KEY_IMG_URL, imgUrl).apply();
    }

    private static SharedPreferences getPreference(Context context) {
        if (mSharedPreference == null) {
            mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mSharedPreference;
    }
}
