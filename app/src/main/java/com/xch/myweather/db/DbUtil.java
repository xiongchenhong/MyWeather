package com.xch.myweather.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xch.myweather.db.entity.City;
import com.xch.myweather.db.entity.County;
import com.xch.myweather.db.entity.Province;
import com.xch.myweather.db.table.CityTable;
import com.xch.myweather.db.table.CountyTable;
import com.xch.myweather.db.table.ProvinceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: DbUtil</p>
 * <p>Description: DbUtil</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-14
 */
public class DbUtil {

    public static List<Province> queryProvinces(Context context) {
        List<Province> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        try (Cursor cursor = resolver.query(ProvinceTable.getContentUri(context), null, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(ProvinceTable.Columns._ID));
                    int provinceCode = cursor.getInt(cursor.getColumnIndex(ProvinceTable.Columns.PROVINCE_CODE));
                    String provinceName = cursor.getString(cursor.getColumnIndex(ProvinceTable.Columns.PROVINCE_NAME));
                    Province province = new Province(id, provinceCode, provinceName);
                    list.add(province);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insertProvinces(Context context, List<Province> provinces) {
        ContentResolver resolver = context.getContentResolver();
        List<ContentValues> valuesList = new ArrayList<>();
        for (Province province : provinces) {
            ContentValues values = new ContentValues();
            values.put(ProvinceTable.Columns.PROVINCE_CODE, province.getProvinceCode());
            values.put(ProvinceTable.Columns.PROVINCE_NAME, province.getProvinceName());
            valuesList.add(values);
        }
        resolver.bulkInsert(ProvinceTable.getContentUri(context), valuesList.toArray(new ContentValues[0]));
    }

    public static List<City> queryCities(Context context, int provinceId) {
        List<City> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        try (Cursor cursor = resolver.query(CityTable.getContentUri(context), null,
                CityTable.Columns.PROVINCE_ID + " = ?", new String[]{String.valueOf(provinceId)}, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(CityTable.Columns._ID));
                    int cityCode = cursor.getInt(cursor.getColumnIndex(CityTable.Columns.CITY_CODE));
                    String cityName = cursor.getString(cursor.getColumnIndex(CityTable.Columns.CITY_NAME));
                    City city = new City(id, provinceId, cityCode, cityName);
                    list.add(city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insertCities(Context context, List<City> cities) {
        ContentResolver resolver = context.getContentResolver();
        List<ContentValues> valuesList = new ArrayList<>();
        for (City city : cities) {
            ContentValues values = new ContentValues();
            values.put(CityTable.Columns.CITY_CODE, city.getCityCode());
            values.put(CityTable.Columns.CITY_NAME, city.getCityName());
            values.put(CityTable.Columns.PROVINCE_ID, city.getProvinceId());
            valuesList.add(values);
        }
        resolver.bulkInsert(CityTable.getContentUri(context), valuesList.toArray(new ContentValues[0]));
    }

    public static List<County> queryCounties(Context context, int cityId) {
        List<County> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        try (Cursor cursor = resolver.query(CountyTable.getContentUri(context), null,
                CountyTable.Columns.CITY_ID + " = ?", new String[]{String.valueOf(cityId)}, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(CountyTable.Columns._ID));
                    String weatherId = cursor.getString(cursor.getColumnIndex(CountyTable.Columns.WEATHER_ID));
                    String countyName = cursor.getString(cursor.getColumnIndex(CountyTable.Columns.COUNTY_NAME));
                    County county = new County(id, cityId, countyName, weatherId);
                    list.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insertCounties(Context context, List<County> counties) {
        ContentResolver resolver = context.getContentResolver();
        List<ContentValues> valuesList = new ArrayList<>();
        for (County county : counties) {
            ContentValues values = new ContentValues();
            values.put(CountyTable.Columns.COUNTY_NAME, county.getCountyName());
            values.put(CountyTable.Columns.WEATHER_ID, county.getWeatherId());
            values.put(CountyTable.Columns.CITY_ID, county.getCityId());
            valuesList.add(values);
        }
        resolver.bulkInsert(CountyTable.getContentUri(context), valuesList.toArray(new ContentValues[0]));
    }
}
