package com.xch.myweather.db.entity;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Province</p>
 * <p>Description: Province</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class Province {

    private int id;
    private int provinceCode;
    private String provinceName;

    public Province() {
    }

    public Province(int id, int provinceCode, String provinceName) {
        this.id = id;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceCode=" + provinceCode +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
