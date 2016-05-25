package com.btd.billonair;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ernrico on 25/05/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    private static final String CONTO_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + DBMetaData.TABELLA_CONTO
            + " ("
            + DBMetaData.NOME_CONTO+ " text  primary key ,"
            + DBMetaData.SALDO_CONTO + " real not null, "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(CONTO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}
