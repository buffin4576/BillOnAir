package com.btd.billonair.com.btd.billonair.db;

import com.btd.billonair.SpesaStanza;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Buffin on 09/08/2016.
 */
public interface SpesaStanzaDAO {
    public void open() throws SQLException;
    public void close();

    public SpesaStanza insertSpesaStanza(SpesaStanza spesaConto);
    public void deleteSpesaStanza(SpesaStanza spesaConto);
    public List<SpesaStanza> getAllSpesaStanza(int idStanza);
}
