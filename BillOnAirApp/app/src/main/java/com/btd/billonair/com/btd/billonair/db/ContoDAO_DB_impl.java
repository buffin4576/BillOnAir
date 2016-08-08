package com.btd.billonair.com.btd.billonair.db;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.Conto;
import com.btd.billonair.MyApplication;
import com.btd.billonair.SpesaConto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buffin on 24/06/2016.
 */
public class ContoDAO_DB_impl implements ContoDAO{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {"nomeConto","saldo","colore"};

    @Override
    public void open() throws SQLException {
        if(dbHelper==null)
            dbHelper = new MySQLiteHelper(MyApplication.getAppContext());
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Conto insertConto(Conto conto) {
        return null;
    }

    @Override
    public void deleteConto(Conto conto) {

    }

    @Override
    public List<Conto> getAllConti() {
        List<Conto> conti = new ArrayList<Conto>();
        Cursor cursor = database.query("conti",allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Conto conto = cursorToConto(cursor);
            conti.add(conto);
            cursor.moveToNext();
        }
        cursor.close();
        return conti;
    }

    private Conto cursorToConto(Cursor cursor)
    {
        String nomeConto = cursor.getString(0);
        double saldo = Double.parseDouble(cursor.getString(1));
        String colore = cursor.getString(2);
        return new Conto(nomeConto,saldo,colore);
    }

    @Override
    public Conto getContoByName(String nomeConto){
        Conto conto = null;
        String[] params = {nomeConto};
        Cursor cursor = database.query("conti", allColumns,"nomeConto=?",params,null,null,null);
        cursor.moveToFirst();
        conto = cursorToConto(cursor);
        return conto;
    }
}
