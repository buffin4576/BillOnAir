package com.btd.billonair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Buffin on 22/08/2016.
 */
public class Notifica implements Serializable{
    private String username;
    private double dovuto;
    private String data;
    private int idNotifica;
    private int idStanza;
    private String nomeSpesa;
    private String testo;

    public Notifica(){}

    public Notifica(String nomeSpesa, String username, double dovuto, String data, int idNotifica, int idStanza, String testo){
        this.nomeSpesa = nomeSpesa;
        this.username = username;
        this.dovuto = dovuto;
        this.data = data;
        this.idNotifica = idNotifica;
        this.idStanza = idStanza;
        this.testo = testo;
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

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getTesto() {
        return testo;
    }

    public void SendNotifica() throws ExecutionException, InterruptedException, JSONException {
        ConnectionController connectionController = new ConnectionController();
        String url = "https://billonair.herokuapp.com/api/notifiche";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nomeSpesa", this.nomeSpesa);
        jsonObject.put("username", this.username);
        jsonObject.put("dovuto", this.dovuto);
        jsonObject.put("data", this.data);
        jsonObject.put("idStanza", this.idStanza);
        jsonObject.put("testo", this.testo);
        String resp = connectionController.execute("POST", url, jsonObject).get();
    }

    public void EliminaNotifica() throws ExecutionException, InterruptedException {
        ConnectionController connectionController = new ConnectionController();
        String url = "https://billonair.herokuapp.com/api/notifiche/"+this.idNotifica;
        String resp = connectionController.execute("DELETE", url).get();
    }
}
