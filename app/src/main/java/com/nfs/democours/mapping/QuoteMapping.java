package com.nfs.democours.mapping;

public interface QuoteMapping extends BaseMapping{

    public static final String STR_QUOTE = "str_quote";
    public static final String NOTATION = "notation";

    public static final String TABLE = "quotes";

    public static final String DML_CREATE = "CREATE TABLE "+ TABLE +
         "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        STR_QUOTE + " TEXT NOT NULL, " +
            NOTATION + " INTEGER NOT NULL);";
}
