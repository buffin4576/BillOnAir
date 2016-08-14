package com.btd.billonair.com.btd.billonair.db;

import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.MyApplication;
import com.btd.billonair.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Buffin on 10/08/2016.
 */
public class QueryDAO_DB_impl implements QueryDAO{
    /*
        ConnectionController connectionController;
        connectionController = new ConnectionController();
        JSONObject json;
        String resp = connectionController.execute("GET", "http://10.196.175.26:3000/api/query/register").get();
        json = new JSONObject(resp);
        String res = json.get("Message").toString();
     */

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
    public Query insertQuery(Query query) {
        return null;
    }

    @Override
    public void deleteQueriesOlderThanDate(String data) {

    }

    @Override
    public ArrayList<Query> getAllQueriesFromData(String data) {
        return null;
    }
}
