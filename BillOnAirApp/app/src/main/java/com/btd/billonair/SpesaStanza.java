package com.btd.billonair;

/**
 * Created by Buffin on 09/08/2016.
 */
public class SpesaStanza {

    private int idSpesa;
    private String creditore;
    private String debitore;
    private String nome;
    private double dovuto;
    private String data;
    private int idStanza;
    private double importo;

    public SpesaStanza(){}

    public SpesaStanza(int idSpesa, String creditore, String debitore, String nome, double dovuto, String data, int idStanza, double importo){
        this.idSpesa = idSpesa;
        this.creditore = creditore;
        this.debitore = debitore;
        this.nome = nome;
        this.dovuto = dovuto;
        this.data = data;
        this.idStanza = idStanza;
        this.importo = importo;
    }

    public void setIdSpesa(int idSpesa){
        this.idSpesa = idSpesa;
    }

    public int getIdSpesa() {
        return idSpesa;
    }

    public double getDovuto() {
        return dovuto;
    }

    public double getImporto() {
        return importo;
    }

    public int getIdStanza() {
        return idStanza;
    }

    public String getCreditore() {
        return creditore;
    }

    public String getData() {
        return data;
    }

    public String getDebitore() {
        return debitore;
    }

    public String getNome() {
        return nome;
    }

    public void setCreditore(String creditore) {
        this.creditore = creditore;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDebitore(String debitore) {
        this.debitore = debitore;
    }

    public void setDovuto(double dovuto) {
        this.dovuto = dovuto;
    }

    public void setIdStanza(int idStanza) {
        this.idStanza = idStanza;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
