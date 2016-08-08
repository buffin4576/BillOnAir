package com.btd.billonair;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by ernrico on 08/06/2016.
 */
public class SpesaConto implements Serializable {
    private int idSC;
    private String nomeSpesa;
    private double costo;
    private String data;
    private String nomeConto;


    /*protected SpesaConto(Parcel in) {
        Nome = in.readString();
        Data = in.readString();
    }*/

    public SpesaConto(){}

    public SpesaConto(int idSC, String nomeSpesa, double costo, String data, String nomeConto){
        this.idSC = idSC;
        this.nomeSpesa = nomeSpesa;
        this.costo = costo;
        this.data = data;
        this.nomeConto = nomeConto;
    }

    public String getNomeSpesa(){
        return nomeSpesa;
    }

    public double getCosto(){
        return costo;
    }

    public String getData(){
        return data;
    }

    public int getId(){
        return idSC;
    }

    public String getNomeConto(){
        return nomeConto;
    }

    public void setId(int id){
        this.idSC = id;
    }

    public void setNomeSpesa(String nomeSpesa){
        this.nomeSpesa = nomeSpesa;
    }

    public void setCosto(double costo){
        this.costo = costo;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setNomeConto(String nomeConto){
        this.nomeConto = nomeConto;
    }
}
