package com.btd.billonair.com.btd.billonair.db;

import com.btd.billonair.Conto;
import com.btd.billonair.Spesa;
import com.btd.billonair.SpesaConto;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Buffin on 24/06/2016.
 */
public interface SpesaContoDAO {
    public void open() throws SQLException;
    public void close();

    public boolean insertSpesa(SpesaConto spesaConto);
    public boolean deleteSpesa(SpesaConto spesaConto);
    public List<SpesaConto> getAllSpese(String owner);
    public SpesaConto getSpesaById(int id);
    public List<SpesaConto> getAllSpeseByConto(String nomeConto, String owner);
}
