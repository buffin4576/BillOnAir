package com.btd.billonair.com.btd.billonair.db;

import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.MyApplication;
import com.btd.billonair.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Buffin on 10/08/2016.
 */
public interface QueryDAO {
    public void open() throws SQLException;
    public void close();

    public boolean insertQuery(String query, String owner);
    public void deleteQueriesOlderThanDate(String data);

    public void deleteAllQueries(String owner);

    public ArrayList<String> getAllQueries(String owner);
    public ArrayList<Query> getAllQueriesFromData(String data);
    public void execQueries(ArrayList<String> queries);

    public void deleteAllOnline();
}
