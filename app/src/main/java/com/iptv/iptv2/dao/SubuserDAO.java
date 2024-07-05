package com.iptv.iptv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iptv.iptv2.models.Subuser;

import java.util.ArrayList;
import java.util.List;

public class SubuserDAO extends SQLiteOpenHelper {

    private static final String TAG = "SubuserDAO";
    private static final String DATABASE_NAME = "iptv.db";
    private static final int DATABASE_VERSION = 5; // Incremented version

    private static final String TABLE_SUBUSERS = "subusers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE_URL = "imageUrl"; // New column

    private static SubuserDAO instance;

    public static synchronized SubuserDAO getInstance(Context context) {
        if (instance == null) {
            instance = new SubuserDAO(context.getApplicationContext());
            SQLiteDatabase db = instance.getWritableDatabase();
            if (!isTableExists(db, TABLE_SUBUSERS)) {
                instance.onCreate(db);
            }
        }
        return instance;
    }

    private SubuserDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "SubuserDAO instance created");
        Log.i(TAG, "Database path: " + context.getDatabasePath(DATABASE_NAME).getAbsolutePath());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database");
        String CREATE_SUBUSERS_TABLE = "CREATE TABLE " + TABLE_SUBUSERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_IMAGE_URL + " TEXT" + ")"; // Updated schema
        db.execSQL(CREATE_SUBUSERS_TABLE);
        Log.i(TAG, "Subusers table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + TABLE_SUBUSERS + " ADD COLUMN " + COLUMN_IMAGE_URL + " TEXT");
        }
    }

    private static boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void addSubuser(Subuser subuser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, subuser.getName());
        values.put(COLUMN_IMAGE_URL, subuser.getImageUrl()); // Updated
        db.insert(TABLE_SUBUSERS, null, values);
        db.close();
    }

    public List<Subuser> getAllSubusers() {
        List<Subuser> subusers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUBUSERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Subuser subuser = new Subuser(cursor.getInt(0), cursor.getString(1), cursor.getString(2)); // Updated
                subusers.add(subuser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return subusers;
    }

    public void deleteSubuser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBUSERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
