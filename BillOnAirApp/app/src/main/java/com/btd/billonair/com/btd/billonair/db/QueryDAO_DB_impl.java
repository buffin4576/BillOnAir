package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

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
    private String[] allColumns = {"query","data"};

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
    public boolean insertQuery(String query) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("query",query);

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
    public ArrayList<String> getAllQueries(){
        ArrayList<String> queries = new ArrayList<>();

        Cursor cursor = database.query("queries",allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            queries.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        return queries;
    }

    @Override
    public void deleteAllQueries(){
        database.delete("queries",null,null);
    }
}
