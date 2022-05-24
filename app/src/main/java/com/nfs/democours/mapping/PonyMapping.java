package com.nfs.democours.mapping;

public interface PonyMapping extends BaseMapping {
    public static final String NAME = "name";
    public static final String COULEUR = "couleur";
    public static final String AGE = "age";

    public static final String TABLE = "pony";

    public static final String DML_CREATE = "CREATE TABLE "+ TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            COULEUR + " TEXT NOT NULL, " +
            AGE + " INTEGER NOT NULL);";
}
