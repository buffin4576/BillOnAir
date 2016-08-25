package com.btd.billonair;

import android.util.Log;

import com.btd.billonair.com.btd.billonair.db.SpesaStanzaDAO_DB_impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Buffin on 09/08/2016.
 */
public class Stanza implements Serializable{

    private String nome;
    private String username;
    private int idStanza;
    private boolean notifica;
    private ArrayList<SpesaStanza> speseStanza = new ArrayList<>();
    private ArrayList<Notifica> notifiche = new ArrayList<>();

    public Stanza(){}

    public Stanza(String nome, String username, int idStanza, boolean notifica) {
        this.nome=nome;
        this.idStanza=idStanza;
        this.username=username;
        this.notifica = notifica;
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

    public boolean isNotifica() {
        return notifica;
    }

    public void setNotifica(boolean notifica) {
        this.notifica = notifica;
    }

    public void setSpeseStanza(ArrayList<SpesaStanza> speseStanza){
        this.speseStanza.addAll(speseStanza);
    }

    public ArrayList<SpesaStanza> getSpeseStanza(){
        ArrayList<SpesaStanza> speseStanza = new ArrayList<>();
        String url = "https://billonair.herokuapp.com/api/spesestanza/"+this.idStanza;
        ConnectionController connectionController = new ConnectionController();
        try {
            String resp = connectionController.execute("GET", url).get();
            if (resp.length() > 0) {
                resp = resp.substring(0, resp.length() - 1);

                JSONArray jsonArray = new JSONArray(resp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    SpesaStanza spesa = new SpesaStanza();

                    spesa.setIdSpesa(jsonObject.getInt("idspesa"));
                    spesa.setCreditore(jsonObject.getString("creditore"));
                    spesa.setDebitore(jsonObject.getString("debitore"));
                    spesa.setNome(jsonObject.getString("nome"));
                    spesa.setDovuto(jsonObject.getDouble("dovuto"));
                    spesa.setData(jsonObject.getString("data"));
                    spesa.setIdStanza(jsonObject.getInt("idstanza"));
                    spesa.setImporto(jsonObject.getDouble("importo"));

                    speseStanza.add(spesa);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return speseStanza;
    }

    public void setNotifiche(ArrayList<Notifica> notifiche) {
        this.notifiche = notifiche;
    }

    public ArrayList<Notifica> getNotifiche(){
        return notifiche;
    }

    public ArrayList<String> getUsers(){
        ArrayList<String> users = new ArrayList<>();

        String url = "https://billonair.herokuapp.com/api/stanza/"+this.idStanza+"/users";
        ConnectionController connectionController = new ConnectionController();
        try {
            String resp = connectionController.execute("GET", url).get();
            if (resp.length() > 0) {
                resp = resp.substring(0, resp.length() - 1);

                JSONArray jsonArray = new JSONArray(resp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String user = jsonObject.getString("username");

                    users.add(user);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void CreaStanza(String nome, String username) throws JSONException, ExecutionException, InterruptedException {
        ConnectionController connectionController = new ConnectionController();
        String url = "https://billonair.herokuapp.com/api/stanza";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", nome);
        jsonObject.put("username", username);
        connectionController.execute("POST", url, jsonObject).get();
    }

    public void AggiungiUtente(String nome, ArrayList<String> usernames, int idStanza) throws JSONException, ExecutionException, InterruptedException {
        ConnectionController connectionController = new ConnectionController();
        String url = "https://billonair.herokuapp.com/api/stanza/"+idStanza;
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for(int i = 0; i < usernames.size(); i++){
            array.put(usernames.get(i));
        }
        //array.put(username);
        jsonObject.put("nome", nome);
        jsonObject.put("utenti", array);
        connectionController.execute("POST", url, jsonObject).get();
    }

    public void RimuoviUtente(String username) throws ExecutionException, InterruptedException {
        ConnectionController connectionController = new ConnectionController();
        String url = "https://billonair.herokuapp.com/api/stanza/"+this.idStanza+"/"+username;
        connectionController.execute("DELETE", url).get();

        ArrayList<String> users = getUsers();
        if(users.size()==0){
            connectionController = new ConnectionController();
            String url2 = "https://billonair.herokuapp.com/api/stanza/"+this.idStanza;
            connectionController.execute("DELETE", url2).get();
        }
    }
}
