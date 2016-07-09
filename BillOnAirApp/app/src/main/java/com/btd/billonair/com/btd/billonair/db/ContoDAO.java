package com.btd.billonair.com.btd.billonair.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.btd.billonair.Conto;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Buffin on 24/06/2016.
 */
public interface ContoDAO {
    public void open() throws SQLException;
    public void close();

    public Conto insertConto(Conto conto);
    public void deleteConto(Conto conto);
    public List<Conto> getAllConti();
}
