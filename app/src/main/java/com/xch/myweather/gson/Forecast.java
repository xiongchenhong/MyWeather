package com.xch.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Forecast</p>
 * <p>Description: Forecast</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class Forecast {
    public String date;

    @SerializedName("cond")
    public WeatherDesc weatherDesc;

    @SerializedName("tmp")
    public Temperature temperature;

    public class WeatherDesc {
        @SerializedName("txt_d")
        public String desc;
    }

    public class Temperature {
        public String max;
        public String min;
    }
}
