package com.btd.billonair.com.btd.billonair.db;

/**
 * Created by Buffin on 24/06/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{

    public static String TABLE_CONTI = "";
    public static String TABLE_SPESECONTI = "";

    private static final String DATABASE_NAME = "billonair.db";
    private static final int DATABASE_VERSION = 1;

    private static String DATABASE_CREATE= "";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TABLE_CONTI="create table conti("
                +"nomeConto text primary key,"
                +"saldo decimal(10,2) not null default 0,"
                +"colore text not null default 'fff'"
                +" );";
        TABLE_SPESECONTI="create table speseconti("
                +"idSC integer primary key autoincrement,"
                +"nomeSpesa text not null default 'spesa',"
                +"costo decimal(10,2) not null default 0,"
                +"data datetime default CURRENT_TIMESTAMP,"
                +"nomeConto text,"
                +"foreign key(nomeConto) references conti(nomeConto) on delete cascade on update cascade"
                +");";
        //PRAGMA foreign_keys = ON; da settare ogni volata che si fa la connessione
        DATABASE_CREATE=TABLE_CONTI+" "+TABLE_SPESECONTI;
        db.execSQL(DATABASE_CREATE);
        String myinsert= "insert into conti (nomeConto, colore) values('testConto','112233')";
        db.execSQL(myinsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTI);
        onCreate(db);
    }
}
