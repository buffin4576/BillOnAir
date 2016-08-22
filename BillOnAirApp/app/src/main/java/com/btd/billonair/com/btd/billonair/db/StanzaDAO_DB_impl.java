package com.btd.billonair.com.btd.billonair.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.Conto;
import com.btd.billonair.MyApplication;
import com.btd.billonair.Stanza;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buffin on 24/06/2016.
 */
public class StanzaDAO_DB_impl implements StanzaDAO{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {"idStanza","username","nome"};

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
    public Stanza insertStanza(Stanza stanza) {
        return null;
    }

    @Override
    public void deleteStanza(Stanza stanza) {

    }

    @Override
    public List<Stanza> getAllStanze(String username) throws SQLException {
        List<Stanza> stanze = new ArrayList<>();
        String[] params = {username};
        Cursor cursor = database.query("stanze", allColumns, "username=?",params,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Stanza stanza = cursorToStanza(cursor);
            stanze.add(stanza);
            cursor.moveToNext();
        }
        cursor.close();
        return stanze;
    }

    @Override
    public Stanza getStanzaById(int id) throws SQLException {
        String[] params = {id+""};
        Cursor cursor = database.query("stanze", allColumns, "idStanza=?",params,null,null,null);
        cursor.moveToFirst();
        Stanza stanza = cursorToStanza(cursor);

        cursor.close();
        return stanza;
    }

    private Stanza cursorToStanza(Cursor cursor) throws SQLException {
        int idStanza = Integer.parseInt(cursor.getString(0));
        String username = cursor.getString(1);
        String nome = cursor.getString(2);

        return new Stanza(nome,username,idStanza,false);
    }
}
