package com.xch.myweather.db.entity;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: County</p>
 * <p>Description: County</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class County {

    private int id;
    private int cityId;
    private String countyName;
    private String weatherId;

    public County() {
    }

    public County(int id, int cityId, String countyName, String weatherId) {
        this.id = id;
        this.cityId = cityId;
        this.countyName = countyName;
        this.weatherId = weatherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", countyName='" + countyName + '\'' +
                ", weatherId='" + weatherId + '\'' +
                '}';
    }
}
