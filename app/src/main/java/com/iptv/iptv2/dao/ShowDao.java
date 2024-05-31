package com.iptv.iptv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iptv.iptv2.models.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shows.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SHOWS = "shows";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TVG_ID = "tvgId";
    private static final String COLUMN_TVG_NAME = "tvgName";
    private static final String COLUMN_TVG_TYPE = "tvgType";
    private static final String COLUMN_GROUP_TITLE = "groupTitle";
    private static final String COLUMN_TVG_LOGO = "tvgLogo";
    private static final String COLUMN_REGION = "region"; // New column

    private static ShowDao instance;
    private SQLiteDatabase db;

    private ShowDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ShowDao getInstance(Context context) {
        if (instance == null) {
            instance = new ShowDao(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOWS_TABLE = "CREATE TABLE " + TABLE_SHOWS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_TVG_ID + " TEXT,"
                + COLUMN_TVG_NAME + " TEXT,"
                + COLUMN_TVG_TYPE + " TEXT,"
                + COLUMN_GROUP_TITLE + " TEXT,"
                + COLUMN_TVG_LOGO + " TEXT,"
                + COLUMN_REGION + " TEXT" + ")"; // Add region column
        db.execSQL(CREATE_SHOWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_SHOWS + " ADD COLUMN " + COLUMN_REGION + " TEXT");
        }
    }

    public void open() {
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void insertShow(Show show) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, show.getName());
        values.put(COLUMN_URL, show.getUrl());
        values.put(COLUMN_TVG_ID, show.getTvgId());
        values.put(COLUMN_TVG_NAME, show.getTvgName());
        values.put(COLUMN_TVG_TYPE, show.getTvgType());
        values.put(COLUMN_GROUP_TITLE, show.getGroupTitle());
        values.put(COLUMN_TVG_LOGO, show.getTvgLogo());
        values.put(COLUMN_REGION, show.getRegion()); // Insert region
        db.insert(TABLE_SHOWS, null, values);
        close();
    }

    public void clearShows() {
        open();
        db.delete(TABLE_SHOWS, null, null);
        close();
    }

    public List<Show> getAllShows() {
        open();
        List<Show> shows = new ArrayList<>();
        String[] columns = {
                COLUMN_ID, COLUMN_NAME, COLUMN_URL, COLUMN_TVG_ID,
                COLUMN_TVG_NAME, COLUMN_TVG_TYPE, COLUMN_GROUP_TITLE,
                COLUMN_TVG_LOGO, COLUMN_REGION
        };
        Cursor cursor = db.query(TABLE_SHOWS, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Show show = new Show(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GROUP_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_LOGO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REGION)) // Get region
                );
                shows.add(show);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return shows;
    }

    public Show getShow(String name) {
        open();
        Cursor cursor = db.query(TABLE_SHOWS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_URL, COLUMN_TVG_ID, COLUMN_TVG_NAME, COLUMN_TVG_TYPE, COLUMN_GROUP_TITLE, COLUMN_TVG_LOGO, COLUMN_REGION},
                COLUMN_NAME + "=?",
                new String[]{name}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Show show = new Show(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GROUP_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_LOGO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REGION)) // Get region
            );
            cursor.close();
            return show;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
}
