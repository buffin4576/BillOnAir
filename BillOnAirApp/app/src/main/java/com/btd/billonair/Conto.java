package com.btd.billonair;

import android.os.Parcel;
import android.os.Parcelable;

import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ernrico on 08/06/2016.
 */


/*public class Conto implements Serializable{
    private String NomeConto;
    private Double Saldo;
    private Boolean Attivo;
    private ArrayList<Spesa> ListaSpese;
    private String Colore;

    public Conto(String NConto,Double SaldoC,String ColoreC)
    {
        NomeConto=NConto;
        Saldo= SaldoC;
        Attivo=true;
        ListaSpese=new ArrayList<Spesa>();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df= new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        Spesa UltimaSpesa=new Spesa("creazione conto",0.0,formattedDate,0);
        ListaSpese.add(UltimaSpesa);
        Colore=ColoreC;
    }

    protected Conto(Parcel in) {
        NomeConto = in.readString();
        Colore = in.readString();
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


    public Spesa getUltimaSpesa()
    {
        return ListaSpese.get(ListaSpese.size()-1);
    }

    public int setColore(String s)
    {
        Colore=s;
        return 0;
    }

    public int addSpesa(Spesa s)
    {
        ListaSpese.add(s);
        return 0;
    }

    public String getColore()
    {
        return Colore;
    }

    public String toString(){
        return "Nome Conto: "+NomeConto+" Saldo: "+Saldo+" Colore: "+Colore;
    };*/
///=======
public class Conto implements Serializable{
    /*String NomeConto;
    double Saldo;
    boolean Attivo;
    ArrayList<Spesa> ListaSpese;
    Spesa UltimaSpesa;
    int Colore;*/

    private String nomeConto;
    private String colore;
    private double saldo;
    private ArrayList<SpesaConto> spese = new ArrayList<>();
    private SpesaConto ultimaSpesa;

    public Conto(){

    };

    public Conto(String nomeConto, double saldo, String colore) throws SQLException {
        this.nomeConto = nomeConto;
        this.colore = colore;
        this.saldo = saldo;

        SpesaContoDAO dao = new SpesaContoDAO_DB_impl();
        dao.open();
        ArrayList<SpesaConto> s = new ArrayList<>();
        s.addAll(dao.getAllSpeseByConto(nomeConto));
        if(s.size()>0)
            this.ultimaSpesa = s.get(s.size()-1);
        else
            this.ultimaSpesa = new SpesaConto();
        dao.close();
    };

    public String getNomeConto(){
        return nomeConto;
    }

    public String getColore(){
        return colore;
    }

    public double getSaldo() { return  saldo; }

    public ArrayList<SpesaConto> getSpeseConto() throws SQLException {
        SpesaContoDAO_DB_impl spesaContoDAODbImpl = new SpesaContoDAO_DB_impl();
        spesaContoDAODbImpl.open();
        ArrayList<SpesaConto> spese = new ArrayList<>();
        spese.addAll(spesaContoDAODbImpl.getAllSpeseByConto(this.nomeConto));
        spesaContoDAODbImpl.close();
        return spese;
    }

    public void setNomeConto(String nomeConto)
    {
        this.nomeConto=nomeConto;
    }

    public void setColore(String colore)
    {
        this.colore = colore;
    }

    public void setSaldo(double saldo) { this.saldo = saldo; }

    public SpesaConto getUltimaSpesa()
    {
        return this.ultimaSpesa;
    }

    public void  setUltimaSpesa(SpesaConto ultimaSpesa){
        this.ultimaSpesa = ultimaSpesa;
    }

    public String toString(){
        return "Nome Conto: "+nomeConto+" Saldo: "+saldo+" Colore: "+colore;
    };
}
