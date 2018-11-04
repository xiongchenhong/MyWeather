package com.xch.myweather.http;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: HttpUtil</p>
 * <p>Description: HttpUtil</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-14
 */
public class HttpUtil {

    private static final String TAG = "HttpUtil";

    public static void sendOkHttpRequest(String url, okhttp3.Callback callback) {
        Log.d(TAG, "sendOkHttpRequest: " + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
