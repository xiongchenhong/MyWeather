package com.xch.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xch.myweather.gson.Forecast;
import com.xch.myweather.gson.Weather;
import com.xch.myweather.http.HttpUtil;
import com.xch.myweather.http.JsonUtil;
import com.xch.myweather.http.URLConfig;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: WeatherActivity</p>
 * <p>Description: WeatherActivity</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class WeatherActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER_ID = "weather_id";
    public static final String EXTRA_NEED_REFRESH = "refresh";

    private ScrollView mWeatherLayout;
    private TextView mTitleCity;
    private TextView mtiTleUpdateTime;
    private TextView mDegree;
    private TextView mWeatherDesc;
    private LinearLayout mForecastLayout;
    private TextView mAQI;
    private TextView mPM25;
    private TextView mComfort;
    private TextView mCarWash;
    private TextView mSport;
    private ImageView mBgImg;
    private SwipeRefreshLayout mRefreshLayout;
    private String mWeatherId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();

        String imgUrl = PrefUtil.getImgUrl(this);
        if (imgUrl != null) {
            Glide.with(this).load(imgUrl).into(mBgImg);
        } else {
            loadBg();
        }

        Intent intent = getIntent();
        showWeather(intent);

        Intent serviceIntent = new Intent(this, AutoUpdateService.class);
        startService(serviceIntent);
    }

    private void showWeather(Intent intent) {
        boolean needRefresh = intent.getBooleanExtra(EXTRA_NEED_REFRESH, false);

        String weatherInfo = PrefUtil.getWeatherInfo(this);
        if (weatherInfo != null && !needRefresh) {
            Weather weather = JsonUtil.handleWeatherResponse(weatherInfo);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            mWeatherId = intent.getStringExtra(EXTRA_WEATHER_ID);
            mWeatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showWeather(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mWeatherLayout = findViewById(R.id.weather_layout);
        mTitleCity = findViewById(R.id.title_city);
        mtiTleUpdateTime = findViewById(R.id.title_update_time);
        mDegree = findViewById(R.id.degree_text);
        mWeatherDesc = findViewById(R.id.weather_info_text);
        mForecastLayout = findViewById(R.id.forecast_layout);
        mAQI = findViewById(R.id.aqi_text);
        mPM25 = findViewById(R.id.pm25_text);
        mComfort = findViewById(R.id.comfort_text);
        mCarWash = findViewById(R.id.car_wash_text);
        mSport = findViewById(R.id.sport_text);
        mBgImg = findViewById(R.id.bing_bg);
        mRefreshLayout = findViewById(R.id.swipe_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
    }

    private void requestWeather(String weatherId) {
        String url = URLConfig.getWeatherUrl(weatherId);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final Weather weather = JsonUtil.handleWeatherResponse(responseText);
                if (weather != null && weather.status.equals("ok")) {
                    PrefUtil.setWeatherInfo(WeatherActivity.this, responseText);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeatherInfo(weather);
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime;
        String degree = weather.now.temperature + "℃";
        String weatherDesc = weather.now.weatherDesc;
        mTitleCity.setText(cityName);
        mtiTleUpdateTime.setText(updateTime);
        mDegree.setText(degree);
        mWeatherDesc.setText(weatherDesc);

        mForecastLayout.removeAllViews();

        for (Forecast forecast : weather.forecast) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, mForecastLayout, false);
            TextView date = view.findViewById(R.id.date_text);
            TextView info = view.findViewById(R.id.info_text);
            TextView max = view.findViewById(R.id.max_text);
            TextView min = view.findViewById(R.id.min_text);
            date.setText(forecast.date);
            info.setText(forecast.weatherDesc.desc);
            max.setText(forecast.temperature.max);
            min.setText(forecast.temperature.min);
            mForecastLayout.addView(view);
        }

        if (weather.aqi != null) {
            mAQI.setText(weather.aqi.city.aqi);
            mPM25.setText(weather.aqi.city.pm25);
        }

        mComfort.setText(weather.suggestion.mComfort.txt);
        mCarWash.setText(weather.suggestion.carWash.txt);
        mSport.setText(weather.suggestion.sport.txt);

        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    private void loadBg() {
        HttpUtil.sendOkHttpRequest(URLConfig.BING_PIC_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicUrl = response.body().string();
                PrefUtil.setImgUrl(WeatherActivity.this, bingPicUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPicUrl).into(mBgImg);
                    }
                });
            }
        });
    }
}
