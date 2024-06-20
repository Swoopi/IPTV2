package com.iptv.iptv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iptv.iptv2.models.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iptv.db";
    private static final int DATABASE_VERSION = 4; // Updated version
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SUBUSER_ID = "subuser_id";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_TYPE = "item_type";

    private static FavoriteDAO instance;

    public static synchronized FavoriteDAO getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteDAO(context.getApplicationContext());
        }
        return instance;
    }

    private FavoriteDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFavoritesTable(db);
    }

    private void createFavoritesTable(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SUBUSER_ID + " INTEGER,"
                + COLUMN_ITEM_ID + " INTEGER,"
                + COLUMN_ITEM_TYPE + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_SUBUSER_ID + ") REFERENCES subusers(id))";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            createFavoritesTable(db);
        }
    }

    public void addFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBUSER_ID, favorite.getSubuserId());
        values.put(COLUMN_ITEM_ID, favorite.getItemId());
        values.put(COLUMN_ITEM_TYPE, favorite.getItemType());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public List<Favorite> getFavoritesBySubuser(int subuserId) {
        List<Favorite> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_SUBUSER_ID + "=?", new String[]{String.valueOf(subuserId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUBUSER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_TYPE))
                );
                favorites.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favorites;
    }

    public void removeFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
