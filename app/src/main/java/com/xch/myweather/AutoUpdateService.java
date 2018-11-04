package com.xch.myweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.xch.myweather.gson.Weather;
import com.xch.myweather.http.HttpUtil;
import com.xch.myweather.http.JsonUtil;
import com.xch.myweather.http.URLConfig;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: AutoUpdateService</p>
 * <p>Description: AutoUpdateService</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-27
 */
public class AutoUpdateService extends Service {
    public static final long UPDATE_INTERVAL_TIME = 8 * 60 * 60 * 100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updatePic();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + UPDATE_INTERVAL_TIME, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        String weatherInfo = PrefUtil.getWeatherInfo(this);
        if (weatherInfo != null) {
            Weather weather = JsonUtil.handleWeatherResponse(weatherInfo);
            String weatherId = weather.basic.weatherId;
            String url = URLConfig.getWeatherUrl(weatherId);
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather1 = JsonUtil.handleWeatherResponse(responseText);
                    if (weather1 != null && "ok".equals(weather1.status)) {
                        PrefUtil.setWeatherInfo(AutoUpdateService.this, responseText);
                    }
                }
            });
        }
    }

    private void updatePic() {
        HttpUtil.sendOkHttpRequest(URLConfig.BING_PIC_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                PrefUtil.setImgUrl(AutoUpdateService.this, bingPic);
            }
        });
    }
}
