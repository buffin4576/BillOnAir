package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.btd.billonair.Conto;
import com.btd.billonair.MyApplication;
import com.btd.billonair.Query;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public boolean insertConto(Conto conto) {

        ContentValues insertValues = new ContentValues();
        insertValues.put("nomeConto",conto.getNomeConto());
        insertValues.put("saldo",conto.getSaldo());
        insertValues.put("colore",conto.getColore());

        String sql = "INSERT INTO conti (nomeConto, saldo, colore) VALUES ('"+conto.getNomeConto()+"',"+conto.getSaldo()+",'"+conto.getColore()+"')";
        try {
            boolean on = Query.SendQuery(sql);
            if(!on)
                Query.AddQuery(sql);
        }
        catch (Exception e){
            try {
                Query.AddQuery(sql);
            }
            catch (Exception e1){}
        }

        Log.w("SQL",sql);

        long i = database.insert("conti",null,insertValues);
        if(i!=-1)
            return true;

        return false;
    }

    @Override
    public boolean updateConto(Conto conto, String vecchioNomeConto) {

        ContentValues insertValues = new ContentValues();
        insertValues.put("nomeConto",conto.getNomeConto());
        insertValues.put("saldo",conto.getSaldo());
        insertValues.put("colore",conto.getColore());

        String[] params = {vecchioNomeConto};

        int i = database.update("conti",insertValues,"nomeConto=?",params);
        if(i>0)
            return true;

        return false;
    }

    @Override
    public boolean deleteConto(Conto conto) {
        String[] params = {conto.getNomeConto()};
        int i = database.delete("conti","nomeConto=?",params);
        if(i>0)
            return true;

        return false;
    }

    @Override
    public List<Conto> getAllConti() throws SQLException {

        try {
            //Query.SetLastUpdate("2016-08-18 11:24:15");
            Query.GetAndExecAllQueries();
            Log.w("ONLINE","Done");
        }
        catch (ExecutionException e){} catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("conti");
        String sql = queryBuilder.buildQuery(allColumns,null,null,null,null,null);
        Log.w("SQL",sql);

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

    private Conto cursorToConto(Cursor cursor) throws SQLException {
        String nomeConto = cursor.getString(0);
        double saldo = Double.parseDouble(cursor.getString(1));
        String colore = cursor.getString(2);
        return new Conto(nomeConto,saldo,colore);
    }

    @Override
    public Conto getContoByName(String nomeConto) throws SQLException {
        Conto conto = null;
        String[] params = {nomeConto};
        Cursor cursor = database.query("conti", allColumns,"nomeConto=?",params,null,null,null);
        cursor.moveToFirst();
        conto = cursorToConto(cursor);
        return conto;
    }
}
