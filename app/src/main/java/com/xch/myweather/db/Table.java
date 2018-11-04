package com.xch.myweather.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: Table</p>
 * <p>Description: Table</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-13
 */
public interface Table {
    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    Cursor query(int matchCode, Uri uri, String[] protection, String selection, String[] selectionArgs, String sortOrder);

    Uri insert(int matchCode, Uri uri, ContentValues contentValues);

    int bulkInsert(int matchCode, Uri uri, ContentValues[] values);

    int update(int matchCode, Uri uri, ContentValues contentValues, String selection, String[] selectionArgs);

    int delete(int matchCode, Uri uri, String selection, String[] selectionArgs);

    String getType(int matchCode, Uri uri);

    boolean respond(int matchCode);
}
