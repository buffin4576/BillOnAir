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
    private String[] allColumns = {"nomeConto","saldo","colore","owner"};

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
        insertValues.put("owner",conto.getOwner());

        String sql = "INSERT INTO conti (nomeConto, saldo, colore, owner) VALUES ('"+conto.getNomeConto()+"',"+conto.getSaldo()+",'"+conto.getColore()+"', '"+conto.getOwner()+"')";
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

        String sql = "UPDATE conti SET nomeConto='"+conto.getNomeConto()+"' WHERE nomeConto='"+vecchioNomeConto+"' AND owner='"+conto.getOwner()+"'";
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


        ContentValues insertValues = new ContentValues();
        insertValues.put("nomeConto",conto.getNomeConto());
        insertValues.put("saldo",conto.getSaldo());
        insertValues.put("colore",conto.getColore());
        insertValues.put("owner",conto.getOwner());

        String[] params = {vecchioNomeConto,conto.getOwner()};

        int i = database.update("conti",insertValues,"nomeConto=? and owner=?",params);
        if(i>0)
            return true;

        return false;
    }

    @Override
    public boolean deleteConto(Conto conto) {

        String sql = "DELETE FROM conti WHERE nomeConto='"+conto.getNomeConto()+"' AND owner='"+conto.getOwner()+"'";
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

        String[] params = {conto.getNomeConto(), conto.getOwner()};
        int i = database.delete("conti","nomeConto=? and owner=?",params);
        if(i>0)
            return true;

        return false;
    }

    @Override
    public List<Conto> getAllConti(String owner) throws SQLException {

        try {
            Query.GetAndExecAllQueries();
        }
        catch (ExecutionException e){} catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("conti");
        String sql = queryBuilder.buildQuery(allColumns,null,null,null,null,null);*/

        List<Conto> conti = new ArrayList<Conto>();
        String[] params = {owner};
        Cursor cursor = database.query("conti",allColumns,"owner=?",params,null,null,null);
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
        String owner = cursor.getString(3);
        return new Conto(nomeConto,saldo,colore,owner);
    }

    @Override
    public Conto getContoByName(String nomeConto, String owner) throws SQLException {
        Conto conto = null;
        String[] params = {nomeConto,owner};
        Cursor cursor = database.query("conti", allColumns,"nomeConto=? and owner=?",params,null,null,null);
        cursor.moveToFirst();
        conto = cursorToConto(cursor);
        return conto;
    }
}
