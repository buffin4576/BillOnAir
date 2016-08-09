package com.btd.billonair.com.btd.billonair.db;

import com.btd.billonair.Stanza;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Buffin on 09/08/2016.
 */
public interface StanzaDAO {

    public void open() throws SQLException;
    public void close();

    public Stanza insertStanza(Stanza stanza);
    public void deleteStanza(Stanza stanza);
    public List<Stanza> getAllStanze(String username) throws SQLException;
    public Stanza getStanzaById(int id) throws SQLException;

}
