package com.iptv.iptv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iptv.iptv2.models.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iptv_channels.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_CHANNELS = "channels";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TVG_ID = "tvgId";
    private static final String COLUMN_TVG_NAME = "tvgName";
    private static final String COLUMN_TVG_TYPE = "tvgType";
    private static final String COLUMN_GROUP_TITLE = "groupTitle";
    private static final String COLUMN_TVG_LOGO = "tvgLogo";

    private static ChannelDao instance;

    private ChannelDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ChannelDao getInstance(Context context) {
        if (instance == null) {
            instance = new ChannelDao(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHANNELS_TABLE = "CREATE TABLE " + TABLE_CHANNELS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_TVG_ID + " TEXT,"
                + COLUMN_TVG_NAME + " TEXT,"
                + COLUMN_TVG_TYPE + " TEXT,"
                + COLUMN_GROUP_TITLE + " TEXT,"
                + COLUMN_TVG_LOGO + " TEXT" + ")";
        db.execSQL(CREATE_CHANNELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        onCreate(db);
    }

    public void insertChannel(Channel channel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, channel.getName());
        values.put(COLUMN_URL, channel.getUrl());
        values.put(COLUMN_TVG_ID, channel.getTvgId());
        values.put(COLUMN_TVG_NAME, channel.getTvgName());
        values.put(COLUMN_TVG_TYPE, channel.getTvgType());
        values.put(COLUMN_GROUP_TITLE, channel.getGroupTitle());
        values.put(COLUMN_TVG_LOGO, channel.getTvgLogo());

        db.insert(TABLE_CHANNELS, null, values);
        db.close();
    }

    public void clearChannels() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHANNELS, null, null);
        db.close();
    }

    public List<Channel> getAllChannels() {
        List<Channel> channels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHANNELS, null);

        if (cursor.moveToFirst()) {
            do {
                Channel channel = new Channel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                channels.add(channel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return channels;
    }
}
