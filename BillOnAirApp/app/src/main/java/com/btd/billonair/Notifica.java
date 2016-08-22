package com.btd.billonair;

/**
 * Created by Buffin on 22/08/2016.
 */
public class Notifica {
    private String username;
    private double dovuto;
    private String data;
    private int idNotifica;
    private int idStanza;
    private String nomeSpesa;

    public Notifica(){}

    public Notifica(String nomeSpesa, String username, double dovuto, String data, int idNotifica, int idStanza){
        this.nomeSpesa = nomeSpesa;
        this.username = username;
        this.dovuto = dovuto;
        this.data = data;
        this.idNotifica = idNotifica;
        this.idStanza = idStanza;
    }

    public String getNomeSpesa(){
        return this.nomeSpesa;
    }

    public double getDovuto() {
        return dovuto;
    }

    public int getIdNotifica() {
        return idNotifica;
    }

    public int getIdStanza() {
        return idStanza;
    }

    public String getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDovuto(double dovuto) {
        this.dovuto = dovuto;
    }

    public void setIdNotifica(int idNotifica) {
        this.idNotifica = idNotifica;
    }

    public void setIdStanza(int idStanza) {
        this.idStanza = idStanza;
    }

    public void setNomeSpesa(String nomeSpesa) {
        this.nomeSpesa = nomeSpesa;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
