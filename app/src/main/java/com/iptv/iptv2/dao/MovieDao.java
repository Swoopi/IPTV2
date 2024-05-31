package com.iptv.iptv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iptv.iptv2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_MOVIES = "movies";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TVG_ID = "tvgId";
    private static final String COLUMN_TVG_NAME = "tvgName";
    private static final String COLUMN_TVG_TYPE = "tvgType";
    private static final String COLUMN_GROUP_TITLE = "groupTitle";
    private static final String COLUMN_TVG_LOGO = "tvgLogo";
    private static final String COLUMN_REGION = "region"; // New column

    private static MovieDao instance;
    private SQLiteDatabase db;

    private MovieDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MovieDao getInstance(Context context) {
        if (instance == null) {
            instance = new MovieDao(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_TVG_ID + " TEXT,"
                + COLUMN_TVG_NAME + " TEXT,"
                + COLUMN_TVG_TYPE + " TEXT,"
                + COLUMN_GROUP_TITLE + " TEXT,"
                + COLUMN_TVG_LOGO + " TEXT,"
                + COLUMN_REGION + " TEXT" + ")"; // Add region column
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_MOVIES + " ADD COLUMN " + COLUMN_REGION + " TEXT");
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

    public void insertMovie(Movie movie) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, movie.getName());
        values.put(COLUMN_URL, movie.getUrl());
        values.put(COLUMN_TVG_ID, movie.getTvgId());
        values.put(COLUMN_TVG_NAME, movie.getTvgName());
        values.put(COLUMN_TVG_TYPE, movie.getTvgType());
        values.put(COLUMN_GROUP_TITLE, movie.getGroupTitle());
        values.put(COLUMN_TVG_LOGO, movie.getTvgLogo());
        values.put(COLUMN_REGION, movie.getRegion()); // Insert region
        db.insert(TABLE_MOVIES, null, values);
        close();
    }

    public void clearMovies() {
        open();
        db.delete(TABLE_MOVIES, null, null);
        close();
    }

    public List<Movie> getAllMovies() {
        open();
        List<Movie> movies = new ArrayList<>();
        String[] columns = {
                COLUMN_ID, COLUMN_NAME, COLUMN_URL, COLUMN_TVG_ID,
                COLUMN_TVG_NAME, COLUMN_TVG_TYPE, COLUMN_GROUP_TITLE,
                COLUMN_TVG_LOGO, COLUMN_REGION
        };
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GROUP_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TVG_LOGO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REGION)) // Get region
                );
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return movies;
    }
}
