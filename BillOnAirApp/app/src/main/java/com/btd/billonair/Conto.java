package com.btd.billonair;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */


public class Conto implements Serializable{
    private String NomeConto;
    private Double Saldo;
    private Boolean Attivo;
    private ArrayList<Spesa> ListaSpese;
    private Spesa UltimaSpesa;
    private String Colore;

    public Conto(String NConto,Double SaldoC,String ColoreC)
    {
        NomeConto=NConto;
        Saldo= SaldoC;
        Attivo=true;
        ListaSpese=new ArrayList<Spesa>();
        UltimaSpesa=null;
        Colore=ColoreC;
    }

    public int setNomeConto(String s)
    {
        NomeConto=s;
        return 1;
    }

    public String getNomeConto()
    {
        return NomeConto;
    }
    public int setSaldo(Double d)
    {
        Saldo=d;
        return 1;
    }

    public double getSaldo()
    {
        return Saldo;
    }

    public int setAttivo(Boolean b)
    {
        Attivo=b;
        return 0;
    }

    public boolean getAttivo()
    {
        return Attivo;
    }

    public int setListaSpese(ArrayList<Spesa> AS)
    {
        ListaSpese=AS;
        return 0;
    }

    public ArrayList<Spesa> getListaSpese()
    {
        return ListaSpese;
    }

    public int setUltimaSpesa(Spesa s)
    {
        UltimaSpesa=s;
        return 0;
    }

    public Spesa getUltimaSpesa()
    {
        return UltimaSpesa;
    }

    public int setColore(String s)
    {
        Colore=s;
        return 0;
    }

    public String getColore()
    {
        return Colore;
    }

    public String toString(){
        return "Nome Conto: "+NomeConto+" "+"Colore: "+Colore;
    };/*
=======
public class Conto implements Serializable{
    /*String NomeConto;
    double Saldo;
    boolean Attivo;
    ArrayList<Spesa> ListaSpese;
    Spesa UltimaSpesa;
    int Colore;
    public Conto(){}

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
    };*/
}
