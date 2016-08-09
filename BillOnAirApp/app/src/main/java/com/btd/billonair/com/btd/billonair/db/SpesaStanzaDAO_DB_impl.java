package com.btd.billonair.com.btd.billonair.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.MyApplication;
import com.btd.billonair.SpesaStanza;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buffin on 09/08/2016.
 */
public class SpesaStanzaDAO_DB_impl implements SpesaStanzaDAO{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {"idSpesa","creditore","debitore","dovuto","data","importo","idStanza","nome"};

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
    public SpesaStanza insertSpesaStanza(SpesaStanza spesaConto) {
        return null;
    }

    @Override
    public void deleteSpesaStanza(SpesaStanza spesaConto) {

    }

    @Override
    public List<SpesaStanza> getAllSpesaStanza(int idStanza) {
        List<SpesaStanza> spese = new ArrayList<>();
        String[] params = {idStanza+""};
        Cursor cursor = database.query("spesestanza", allColumns, "idStanza=?",params,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            SpesaStanza spesa = cursorToSpesaStanza(cursor);
            spese.add(spesa);
            cursor.moveToNext();
        }
        cursor.close();
        return spese;
    }

    private SpesaStanza cursorToSpesaStanza(Cursor cursor)
    {
        int idSpesa = cursor.getInt(0);
        String creditore = cursor.getString(1);
        String debitore = cursor.getString(2);
        double dovuto = cursor.getDouble(3);
        String data = cursor.getString(4);
        double importo = cursor.getDouble(5);
        int idStanza = cursor.getInt(6);
        String nome = cursor.getString(7);

        return new SpesaStanza(idSpesa,creditore,debitore,nome,dovuto,data,idStanza,importo);
    }

}
