package com.xch.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Basic</p>
 * <p>Description: Basic</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
