package com.xch.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Now</p>
 * <p>Description: Now</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class Now {
    @SerializedName("cond_txt")
    public String weatherDesc;

    @SerializedName("tmp")
    public String temperature;
}
