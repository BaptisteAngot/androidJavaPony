package com.nfs.democours.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nfs.democours.beans.Pony;

public class DBHelper  extends SQLiteOpenHelper {

    //private static final String DB_NAME = "quote.db";
    private static final String DB_NAME = "pony.db";
    private static final int DB_VERSION = 2;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TagLog.sql, "on create du DBHelper" + Pony.DML_CREATE);
        db.execSQL(Pony.DML_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Pony.TABLE);
    }
}
