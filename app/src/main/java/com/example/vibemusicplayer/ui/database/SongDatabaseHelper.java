package com.example.vibemusicplayer.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDatabaseHelper extends SQLiteOpenHelper {
    // 数据库名称和版本号
    private static final String DATABASE_NAME = "favorite_song_database";
    private static final int DATABASE_VERSION = 1;

    // 表名和字段名
    public static final String TABLE_FAVORITE_SONG = "favorite_song";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_URI = "uri";

    // 创建数据库表的 SQL 语句
    private static final String SQL_CREATE_TABLE_FAVORITE_SONG = "CREATE TABLE " + TABLE_FAVORITE_SONG + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ARTIST + " TEXT, " +
            COLUMN_ALBUM + " TEXT, " +
            COLUMN_DURATION + " TEXT, " +
            COLUMN_URI + " TEXT)";

    public SongDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_SONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_SONG);
        onCreate(db);
    }
}
