package com.nfs.democours.dao;

import com.nfs.democours.beans.Quote;
import com.nfs.democours.helper.DBHelper;
import com.nfs.democours.helper.TagLog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class BaseDaoSqlite {

    protected DBHelper dbHelper;
    protected SQLiteDatabase db;

    public BaseDaoSqlite(Context context){
        dbHelper = new DBHelper(context);
    }

    protected SQLiteDatabase getDB(){
        if(db == null || !db.isOpen()){
            //Log.d(TagLog.sql, "create db \n" + Quote.DML_CREATE);
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }
}
