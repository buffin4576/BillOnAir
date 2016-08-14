package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.Conto;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Buffin on 24/06/2016.
 */
public interface ContoDAO {
    public void open() throws SQLException;
    public void close();

    public boolean insertConto(Conto conto);

    public boolean updateConto(Conto conto, String vecchioNomeConto);

    public boolean deleteConto(Conto conto);
    public List<Conto> getAllConti() throws SQLException;
    public Conto getContoByName(String nomeConto) throws SQLException;
}
