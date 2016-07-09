package com.btd.billonair;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
<<<<<<< HEAD
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

    public int SetNomeConto(String s)
    {
        NomeConto=s;
        return 1;
    }

    public String GetNomeCOnto()
    {
        return NomeConto;
    }
    public int SetSaldo(Double d)
    {
        Saldo=d;
        return 1;
    }

    public double GetSaldo()
    {
        return Saldo;
    }

    public int SetAttivo(Boolean b)
    {
        Attivo=b;
        return 0;
    }

    public boolean GetAttivo()
    {
        return Attivo;
    }

    public int SetListaSpese(ArrayList<Spesa> AS)
    {
        ListaSpese=AS;
        return 0;
    }

    public ArrayList<Spesa> GetListaSpese()
    {
        return ListaSpese;
    }

    public int SetUltimaSpesa(Spesa s)
    {
        UltimaSpesa=s;
        return 0;
    }

    public Spesa GetUltimaSpesa()
    {
        return UltimaSpesa;
    }

    public int SetColore(String s)
    {
        Colore=s;
        return 0;
    }

    public String GetColore()
    {
        return Colore;
    }
=======
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
>>>>>>> 11f3879dd30f58b0f4b124f622975b0dd8fb770b
}
