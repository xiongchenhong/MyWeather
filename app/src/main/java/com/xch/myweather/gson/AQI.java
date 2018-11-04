package com.xch.myweather.gson;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: AQI</p>
 * <p>Description: AQI</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class AQI {

    public City city;

    public class City {
        public String aqi;
        public String pm25;
        public String qlty;
    }
}
