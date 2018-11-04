package com.xch.myweather.db.entity;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: City</p>
 * <p>Description: City</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class City {

    private int id;
    private int provinceId;
    private int cityCode;
    private String cityName;

    public City() {
    }

    public City(int id, int provinceId, int cityCode, String cityName) {
        this.id = id;
        this.provinceId = provinceId;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
