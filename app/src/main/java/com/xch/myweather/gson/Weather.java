package com.xch.myweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Weather</p>
 * <p>Description: Weather</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class Weather {
    public String status;
    public Basic basic;
    public Now now;
    public AQI aqi;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecast;
}
