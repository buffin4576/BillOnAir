package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.btd.billonair.MyApplication;
import com.btd.billonair.SpesaConto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buffin on 24/06/2016.
 */
public class SpesaContoDAO_DB_impl implements SpesaContoDAO{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {"idSC","nomeSpesa","costo","data","nomeConto"};

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
    public boolean insertSpesa(SpesaConto spesaConto) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("nomeSpesa",spesaConto.getNomeSpesa());
        insertValues.put("costo",spesaConto.getCosto());
        insertValues.put("data",spesaConto.getData());
        insertValues.put("nomeConto",spesaConto.getNomeConto());

        long i = database.insert("speseconti",null,insertValues);
        if(i!=-1)
            return true;

        return false;
    }

    @Override
    public boolean deleteSpesa(SpesaConto spesaConto) {
        String[] params = {spesaConto.getId()+""};
        int i = database.delete("speseconti","idSC=?",params);
        if(i>0)
            return true;

        return false;
    }

    @Override
    public List<SpesaConto> getAllSpese() {
        List<SpesaConto> spese = new ArrayList<SpesaConto>();
        Cursor cursor = database.query("speseconti",allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            SpesaConto spesa = cursorToSpesaConto(cursor);
            spese.add(spesa);
            cursor.moveToNext();
        }
        cursor.close();
        return spese;
    }

    @Override
    public List<SpesaConto> getAllSpeseByConto(String nomeConto) {
        List<SpesaConto> spese = new ArrayList<SpesaConto>();
        String[] params = {nomeConto};
        Cursor cursor = database.query("speseconti",allColumns,"nomeConto=?",params,null,null,null);
        Log.w("MyDebug","nomeconto: "+nomeConto);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            SpesaConto spesa = cursorToSpesaConto(cursor);
            spese.add(spesa);
            cursor.moveToNext();
        }
        cursor.close();
        return spese;
    }

    private SpesaConto cursorToSpesaConto(Cursor cursor)
    {
        int idSC = Integer.parseInt(cursor.getString(0));
        String nomeSpesa = cursor.getString(1);
        double costo = Double.parseDouble(cursor.getString(2));

        String data = cursor.getString(3); //da verificare il tipo datetime

        String nomeConto = cursor.getString(4);
        return new SpesaConto(idSC, nomeSpesa, costo, data, nomeConto);
    }

    @Override
    public SpesaConto getSpesaById(int id){
        SpesaConto spesa = null;
        String[] params = {id+""};
        Cursor cursor = database.query("speseconti", allColumns,"idSC=?",params,null,null,null);
        cursor.moveToFirst();
        spesa = cursorToSpesaConto(cursor);
        return spesa;
    }
}
