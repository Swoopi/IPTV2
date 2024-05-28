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

    private static final String DATABASE_NAME = "iptv_shows.db";
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

    private static ShowDao instance;

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
                + COLUMN_TVG_LOGO + " TEXT" + ")";
        db.execSQL(CREATE_SHOWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        onCreate(db);
    }

    public void insertShow(Show show) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, show.getName());
        values.put(COLUMN_URL, show.getUrl());
        values.put(COLUMN_TVG_ID, show.getTvgId());
        values.put(COLUMN_TVG_NAME, show.getTvgName());
        values.put(COLUMN_TVG_TYPE, show.getTvgType());
        values.put(COLUMN_GROUP_TITLE, show.getGroupTitle());
        values.put(COLUMN_TVG_LOGO, show.getTvgLogo());

        db.insert(TABLE_SHOWS, null, values);
        db.close();
    }

    public void clearShows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOWS, null, null);
        db.close();
    }

    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOWS, null);

        if (cursor.moveToFirst()) {
            do {
                Show show = new Show(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                shows.add(show);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return shows;
    }
}
