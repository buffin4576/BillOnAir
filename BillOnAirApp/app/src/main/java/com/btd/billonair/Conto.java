package com.btd.billonair;

import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class Conto {
    /*String NomeConto;
    double Saldo;
    boolean Attivo;
    ArrayList<Spesa> ListaSpese;
    Spesa UltimaSpesa;
    int Colore;
    public Conto(){}*/

    private String nomeConto;
    private String colore;

    public Conto(){

    };

    public Conto(String nomeConto, String colore){
        this.nomeConto = nomeConto;
        this.colore = colore;
    };

    public String getNomeConto(){
        return nomeConto;
    };

    public String getColore(){
        return colore;
    };

    public void setNomeConto(String nomeConto)
    {
        this.nomeConto=nomeConto;
    };

    public void setColore(String colore)
    {
        this.colore = colore;
    };

    public String toString(){
        return "Nome Conto: "+nomeConto+" "+"Colore: "+colore;
    };
}
