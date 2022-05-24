package com.nfs.democours.dao;

import android.content.Context;

public class DaoFactory {

    public static PonyDao ponyDao;

    private DaoFactory(){}

    public static PonyDao getPonyDao(Context context) {
        if(ponyDao == null){
            ponyDao = new SqlitePonyDao(context);
        }
        return ponyDao;
    }
}
