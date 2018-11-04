package com.xch.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Suggestion</p>
 * <p>Description: Suggestion</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-20
 */
public class Suggestion {
    @SerializedName("comf")
    public Comfort mComfort;

    public Sport sport;

    @SerializedName("cw")
    public CarWash carWash;

    public class Comfort {
        public String txt;
    }

    public class Sport {
        public String txt;
    }

    public class CarWash {
        public String txt;
    }
}
