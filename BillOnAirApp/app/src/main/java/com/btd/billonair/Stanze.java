package com.btd.billonair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Buffin on 22/08/2016.
 */
public class Stanze {

    private String username;

    public Stanze(){}

    public Stanze(String username){
        this.username = username;
    }

    public ArrayList<Notifica> getNotifiche(){
        ArrayList<Notifica> notifiche = new ArrayList<>();

        String url = "https://billonair.herokuapp.com/api/stanza/user/"+this.username;

        return notifiche;
    }

    public ArrayList<Stanza> getStanze() {
        ArrayList<Stanza> stanze = new ArrayList<>();

        String url = "https://billonair.herokuapp.com/api/stanza/user/"+this.username;
        ConnectionController connectionController = new ConnectionController();
        try {
            String resp = connectionController.execute("GET", url).get();
            if (resp.length() > 0) {
                resp = resp.substring(0, resp.length() - 1);

                JSONArray jsonArray = new JSONArray(resp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Stanza stanza = new Stanza();

                    stanza.setIdStanza(jsonObject.getInt("idStanza"));
                    stanza.setUsername(jsonObject.getString("username"));
                    stanza.setNome(jsonObject.getString("nome"));

                    stanza.setNotifica(false);

                    stanze.add(stanza);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stanze;
    }
}
