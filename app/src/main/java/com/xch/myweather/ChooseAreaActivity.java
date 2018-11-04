package com.xch.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xch.myweather.db.entity.City;
import com.xch.myweather.db.entity.County;
import com.xch.myweather.db.DbUtil;
import com.xch.myweather.db.entity.Province;
import com.xch.myweather.http.HttpUtil;
import com.xch.myweather.http.JsonUtil;
import com.xch.myweather.http.URLConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: ChooseAreaActivity</p>
 * <p>Description: ChooseAreaActivity</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-14
 */
public class ChooseAreaActivity extends AppCompatActivity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDataList = new ArrayList<>();
    private int mCurrentLevel;
    private Province mSelectedProvince;
    private City mSelectedCity;
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);
        setContentView(mListView);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCurrentLevel == LEVEL_PROVINCE) {
                    mSelectedProvince = mProvinceList.get(position);
                    queryCities();
                } else if (mCurrentLevel == LEVEL_CITY) {
                    mSelectedCity = mCityList.get(position);
                    queryCounties();
                } else if (mCurrentLevel == LEVEL_COUNTY) {
                    String weatherId = mCountyList.get(position).getWeatherId();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra(WeatherActivity.EXTRA_WEATHER_ID, weatherId);
                    intent.putExtra(WeatherActivity.EXTRA_NEED_REFRESH, true);
                    startActivity(intent);
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces() {
        mProvinceList = DbUtil.queryProvinces(this);
        if (mProvinceList.size() > 0) {
            freshProvinceData(mProvinceList);
        } else {
            queryFromServer(URLConfig.PROVINCE_URL, LEVEL_PROVINCE);
        }
    }

    private void queryCities() {
        mCityList = DbUtil.queryCities(this, mSelectedProvince.getId());
        if (mCityList.size() > 0) {
            freshCityData(mCityList);
        } else {
            int provinceCode = mSelectedProvince.getProvinceCode();
            String url = URLConfig.getCityUrl(provinceCode);
            queryFromServer(url, LEVEL_CITY);
        }
    }

    private void queryCounties() {
        mCountyList = DbUtil.queryCounties(this, mSelectedCity.getId());
        if (mCountyList.size() > 0) {
            freshCountyData(mCountyList);
        } else {
            int provinceCode = mSelectedProvince.getProvinceCode();
            int cityCode = mSelectedCity.getCityCode();
            String url = URLConfig.getCountyUrl(provinceCode, cityCode);
            queryFromServer(url, LEVEL_COUNTY);
        }
    }

    private void queryFromServer(String url, final int type) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                switch (type) {
                    case LEVEL_PROVINCE:
                        mProvinceList = JsonUtil.handleProvinceResponse(responseText);
                        DbUtil.insertProvinces(ChooseAreaActivity.this, mProvinceList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryProvinces();
                            }
                        });
                        break;
                    case LEVEL_CITY:
                        mCityList = JsonUtil.handleCityResponse(responseText, mSelectedProvince.getId());
                        DbUtil.insertCities(ChooseAreaActivity.this, mCityList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryCities();
                            }
                        });
                        break;
                    case LEVEL_COUNTY:
                        mCountyList = JsonUtil.handleCountyResponse(responseText, mSelectedCity.getId());
                        DbUtil.insertCounties(ChooseAreaActivity.this, mCountyList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryCounties();
                            }
                        });
                        break;
                }
            }
        });
    }

    private void freshProvinceData(List<Province> provinceList) {
        mDataList.clear();
        for (Province province : provinceList) {
            mDataList.add(province.getProvinceName());
        }
        mAdapter.notifyDataSetChanged();
        mCurrentLevel = LEVEL_PROVINCE;
    }

    private void freshCityData(List<City> cityList) {
        mDataList.clear();
        for (City city : cityList) {
            mDataList.add(city.getCityName());
        }
        mAdapter.notifyDataSetChanged();
        mCurrentLevel = LEVEL_CITY;
    }

    private void freshCountyData(List<County> countyList) {
        mDataList.clear();
        for (County county : countyList) {
            mDataList.add(county.getCountyName());
        }
        mAdapter.notifyDataSetChanged();
        mCurrentLevel = LEVEL_COUNTY;
    }

    @Override
    public void onBackPressed() {
        if (mCurrentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (mCurrentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            super.onBackPressed();
        }
    }
}
