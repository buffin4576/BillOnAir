package com.btd.billonair;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

       /* Date now = new Date();
        final Calendar calendar  = Calendar.getInstance();
        final int      utcOffset = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
        final long     tempDate  = new Date().getTime();
        now = new Date(tempDate - utcOffset);
        String d = (now.getYear() + 1900) + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
*/
        String url = "https://billonair.herokuapp.com/api/notifiche/"+this.username;

        ConnectionController connectionController = new ConnectionController();
        try {
            String resp = connectionController.execute("GET", url).get();
            if (resp.length() > 0) {
                resp = resp.substring(0, resp.length() - 1);

                JSONArray jsonArray = new JSONArray(resp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Notifica notifica = new Notifica();

                    notifica.setNomeSpesa(jsonObject.getString("nomespesa"));
                    notifica.setUsername(jsonObject.getString("username"));
                    notifica.setDovuto(jsonObject.getDouble("dovuto"));
                    notifica.setData(jsonObject.getString("data"));
                    notifica.setIdNotifica(jsonObject.getInt("idnotifica"));
                    notifica.setIdStanza(jsonObject.getInt("idstanza"));
                    notifica.setTesto(jsonObject.getString("testo"));

                    notifiche.add(notifica);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return notifiche;
    }

    public ArrayList<Stanza> getStanze() {
        ArrayList<Notifica> notifiche = getNotifiche();
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

                    stanza.setIdStanza(jsonObject.getInt("idstanza"));
                    stanza.setUsername(jsonObject.getString("username"));
                    stanza.setNome(jsonObject.getString("nome"));

                    stanza.setNotifica(false);
                    ArrayList<Notifica> notificheStanza = new ArrayList<>();
                    for(int j = 0; j < notifiche.size(); j++)
                    {
                        if(notifiche.get(j).getIdStanza()==stanza.getIdStanza()){
                            stanza.setNotifica(true);
                            notificheStanza.add(notifiche.get(j));
                        }
                    }
                    stanza.setNotifiche(notificheStanza);

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
