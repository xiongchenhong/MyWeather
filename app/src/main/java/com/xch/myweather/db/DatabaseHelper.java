package com.xch.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: DatabaseHelper</p>
 * <p>Description: DatabaseHelper</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;

    private Context mContext;
    private List<Table> mTables;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        mTables = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Table table : mTables) {
            table.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table table : mTables) {
            table.onUpgrade(db, oldVersion, newVersion);
        }
    }

    public Context getContext() {
        return mContext;
    }

    void addTables(List<Table> tables) {
        mTables.addAll(tables);
    }
}
