package com.btd.billonair;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ernrico on 25/05/2016.
 */

public class DBOperations {
    private SQLiteDatabase mDb;
    private DBHelper dbHelper;
    // private static String DB_PATH = null;
    //private static String DB_PATH = "/com.diocanesons.degabevetroppo/databases/";
    private static final String DB_NAME = "DBoffline.db";//"lesson02DB.db";
    private static final int DB_VERSION = 1;

    private static DBOperations instance = null;

    private DBOperations(Context ctx) {
        dbHelper = new DBHelper(ctx, DB_NAME, null, DB_VERSION);
    }

    public static DBOperations getInstance(Context ctx) {
        if (instance == null) {
            instance = new DBOperations(ctx);
            // DB_PATH =
            // ctx.getFilesDir().getPath()+"data/android.lesson02/databases/";
        }
        return instance;
    }
}
