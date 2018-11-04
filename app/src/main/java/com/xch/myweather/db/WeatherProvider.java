package com.xch.myweather.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.xch.myweather.db.table.CityTable;
import com.xch.myweather.db.table.CountyTable;
import com.xch.myweather.db.table.ProvinceTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p>Project: MyWeather</p>
 * <p>Title: WeatherProvider</p>
 * <p>Description: WeatherProvider</p>
 * <p>Copyright (c) 2018 www.oppo.com Inc. All rights reserved.</p>
 * <p>Company: OPPO</p>
 *
 * @author Chenhong.Xiong
 * @since 2018-10-14
 */
public class WeatherProvider extends ContentProvider {

    private static String AUTHORITY;

    public static String getAuthority(Context context) {
        if (AUTHORITY == null) {
            AUTHORITY = context.getPackageName();
        }
        return AUTHORITY;
    }

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final Map<String, Integer> sUriMatchMap = new HashMap<>();

    public static void addUriMatch(String path, int code) {
        sUriMatchMap.put(path, code);
    }

    private static int BASE_MATCH_CODE = 0;

    public static int getBaseMatchCode() {
        BASE_MATCH_CODE += 100;
        return BASE_MATCH_CODE;
    }

    private static List<Table> sTables = new ArrayList<>();

    public static void addTable(Table table) {
        if (table != null) {
            sTables.add(table);
        }
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        addTable(new ProvinceTable(databaseHelper));
        addTable(new CityTable(databaseHelper));
        addTable(new CountyTable(databaseHelper));
        databaseHelper.addTables(sTables);
        String authority = getAuthority(getContext());
        for (Map.Entry<String, Integer> entry : sUriMatchMap.entrySet()) {
            MATCHER.addURI(authority, entry.getKey(), entry.getValue());
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.query(matchCode, uri, projection, selection, selectionArgs, sortOrder);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.getType(matchCode, uri);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.insert(matchCode, uri, values);
            }
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.bulkInsert(matchCode, uri, values);
            }
        }
        return super.bulkInsert(uri, values);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.delete(matchCode, uri, selection, selectionArgs);
            }
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int matchCode = MATCHER.match(uri);
        for (Table table : sTables) {
            if (table.respond(matchCode)) {
                return table.update(matchCode, uri, values, selection, selectionArgs);
            }
        }
        return 0;
    }
}
