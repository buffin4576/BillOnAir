package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.btd.billonair.MyApplication;
import com.btd.billonair.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Buffin on 10/08/2016.
 */
public class QueryDAO_DB_impl implements QueryDAO{

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {"query","data","owner"};

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
    public boolean insertQuery(String query, String owner) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("query",query);
        insertValues.put("owner",owner);

        long i = database.insert("queries",null,insertValues);
        if(i!=-1)
            return true;

        return false;
    }

    @Override
    public void deleteQueriesOlderThanDate(String data) {

    }

    @Override
    public ArrayList<Query> getAllQueriesFromData(String data) {
        return null;
    }

    @Override
    public void execQueries(ArrayList<String> queries){
        for(String q:queries){
            try {
                database.execSQL(q);
            }
            catch (Exception e){}
        }
    }

    @Override
    public ArrayList<String> getAllQueries(String owner){
        ArrayList<String> queries = new ArrayList<>();
        String[] params = {owner};

        Cursor cursor = database.query("queries",allColumns,"owner=?",params,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            queries.add(cursor.getString(0));
            Log.w("QUERIES",cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        return queries;
    }

    @Override
    public void deleteAllQueries(String owner){
        String[] params = {owner};
        database.delete("queries","owner=?", params);
    }

    public void deleteAllOnline(){
        String[] params = {"offline"};
        database.delete("conti","owner<>?",params);
        database.delete("speseconti","owner<>?",params);
        //database.delete("stanze","owner<>?",params);
        //database.delete("spesestanza","owner<>?",params);
    }
}
