package com.xch.myweather.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: AbstractTable</p>
 * <p>Description: AbstractTable</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public class AbstractTable implements Table {
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public Cursor query(int matchCode, Uri uri, String[] protection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(int matchCode, Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int bulkInsert(int matchCode, Uri uri, ContentValues[] values) {
        return 0;
    }

    @Override
    public int update(int matchCode, Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(int matchCode, Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(int matchCode, Uri uri) {
        return null;
    }

    @Override
    public boolean respond(int matchCode) {
        return false;
    }
}
