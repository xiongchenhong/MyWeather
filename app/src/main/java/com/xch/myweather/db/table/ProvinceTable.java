package com.xch.myweather.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.xch.myweather.db.AbstractTable;
import com.xch.myweather.db.DatabaseHelper;
import com.xch.myweather.db.WeatherProvider;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: ProvinceTable</p>
 * <p>Description: ProvinceTable</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class ProvinceTable extends AbstractTable {

    public interface Columns extends BaseColumns {
        String PROVINCE_CODE = "province_code";
        String PROVINCE_NAME = "province_name";
    }

    private static final String TABLE_NAME = "province";

    private static final String CREATE_TABLE_PROVINCE =
            "create table if not exists " + TABLE_NAME +
                    "(" +
                    Columns._ID + " integer primary key autoincrement," +
                    Columns.PROVINCE_CODE + " integer," +
                    Columns.PROVINCE_NAME + " text not null" +
                    ")";

    private static final int MATCH_BASE = 0;
    private static final int MATCH_SIZE = 1;
    private static final int BASE_MATCH_CODE = WeatherProvider.getBaseMatchCode();

    static {
        WeatherProvider.addUriMatch(TABLE_NAME, BASE_MATCH_CODE + MATCH_BASE);
    }

    public static Uri getContentUri(Context context) {
        return Uri.parse("content://" + WeatherProvider.getAuthority(context) + "/" + TABLE_NAME);
    }

    private DatabaseHelper mDatabaseHelper;

    public ProvinceTable(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROVINCE);
    }

    @Override
    public Cursor query(int matchCode, Uri uri, String[] protection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matchCode - BASE_MATCH_CODE) {
            case MATCH_BASE:
                return mDatabaseHelper.getReadableDatabase().query(TABLE_NAME, protection, selection, selectionArgs, null, null, sortOrder);
        }
        return super.query(matchCode, uri, protection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int bulkInsert(int matchCode, Uri uri, ContentValues[] values) {
        switch (matchCode - BASE_MATCH_CODE) {
            case MATCH_BASE:
                SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        db.insertWithOnConflict(TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                    }
                    db.setTransactionSuccessful();
                    return values.length;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
        }
        return super.bulkInsert(matchCode, uri, values);
    }

    @Override
    public boolean respond(int matchCode) {
        return matchCode >= BASE_MATCH_CODE && matchCode < BASE_MATCH_CODE + MATCH_SIZE;
    }
}
