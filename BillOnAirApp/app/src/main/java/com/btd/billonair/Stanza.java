package com.btd.billonair;

import com.btd.billonair.com.btd.billonair.db.SpesaStanzaDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Buffin on 09/08/2016.
 */
public class Stanza {

    private String nome;
    private String username;
    private int idStanza;
    private ArrayList<SpesaStanza> speseStanza = new ArrayList<>();

    public Stanza(){}

    public Stanza(String nome, String username, int idStanza) throws SQLException {
        this.nome=nome;
        this.idStanza=idStanza;
        this.username=username;

        SpesaStanzaDAO_DB_impl spesaStanzaDAODbImpl = new SpesaStanzaDAO_DB_impl();
        spesaStanzaDAODbImpl.open();
        this.speseStanza.addAll(spesaStanzaDAODbImpl.getAllSpesaStanza(idStanza));
        spesaStanzaDAODbImpl.close();
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setIdStanza(int idStanza){
        this.idStanza = idStanza;
    }

    public int getIdStanza() {
        return this.idStanza;
    }

    public void setSpeseStanza(ArrayList<SpesaStanza> speseStanza){
        this.speseStanza.addAll(speseStanza);
    }

    public ArrayList<SpesaStanza> getSpeseStanza(){
        return this.speseStanza;
    }
}
