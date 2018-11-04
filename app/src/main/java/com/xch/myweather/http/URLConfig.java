package com.xch.myweather.http;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: URLConfig</p>
 * <p>Description: URLConfig</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-11-04
 */
public class URLConfig {

    private static final String KEY = "656edf94b72c4c03a31a8771afad0d01";

    private static final String BASE_URL = "http://guolin.tech/api/";

    public static final String BING_PIC_URL = BASE_URL + "bing_pic";

    private static final String WEATHER_BASE_URL = BASE_URL + "weather";

    public static final String PROVINCE_URL = BASE_URL + "china";

    public static String getCityUrl(int provinceCode) {
        return PROVINCE_URL + "/" + provinceCode;
    }

    public static String getCountyUrl(int provinceCode, int cityCode) {
        return getCityUrl(provinceCode) + "/" + cityCode;
    }

    public static String getWeatherUrl(String weatherId) {
        return WEATHER_BASE_URL + "?cityid=" + weatherId + "&key=" + KEY;
    }
}
