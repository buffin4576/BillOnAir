package com.btd.billonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.btd.billonair.com.btd.billonair.db.QueryDAO;
import com.btd.billonair.com.btd.billonair.db.QueryDAO_DB_impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Buffin on 10/08/2016.
 */
public final class Query {
    private static String lastUpdate;
    private static Context context;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public static void SetContext(Context c){
        context = c;
        settings = context.getSharedPreferences("Shared",0);
        editor = settings.edit();
    }

    public static void SetToOffline(){
        editor.putBoolean("online",false);
        editor.commit();
    }

        /*
        ConnectionController connectionController;
        connectionController = new ConnectionController();
        JSONObject json;
        String resp = connectionController.execute("GET", "http://10.196.175.26:3000/api/query/register").get();
        json = new JSONObject(resp);
        String res = json.get("Message").toString();
     */

    private Query(){

        //settare tutti i parametri (ip server db, info user....)
    }

    //se fallisce sendQuery allora siamo offline e bisogna eseguire addquery

    public static boolean SendQuery(String query) throws JSONException, ExecutionException, InterruptedException, SQLException {

        boolean online = settings.getBoolean("online", false);

        if(online) {
            Date now = new Date();
            String d = (now.getYear() + 1900) + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();

            ConnectionController connectionController = new ConnectionController();
            String url = "http://192.168.1.34:3000/api/users/query";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query", query);
            jsonObject.put("username", "buffin");
            jsonObject.put("timestamp", d);
            connectionController.execute("POST", url, jsonObject).get();
            return true;
        }
        else
            return false;
    }

    public static void SendQueriesOffline() throws SQLException, InterruptedException, ExecutionException, JSONException {
        QueryDAO dao = new QueryDAO_DB_impl();
        ArrayList<String> queries = new ArrayList<>();
        dao.open();
        queries.addAll(dao.getAllQueries());
        dao.close();
        Log.w("SENDOffline:",queries.size()+"");
        for(String q:queries){
            SendQuery(q);
            Log.w("SENDOffline:",q);
        }
        dao.open();
        dao.deleteAllQueries();
        dao.close();
    }

    public static void SetLastUpdate(String date){
        lastUpdate = date;
    }

    public static void AddQuery(String query) throws SQLException {
        QueryDAO dao = new QueryDAO_DB_impl();
        dao.open();
        dao.insertQuery(query);
        dao.close();
    }

    public static String GetAndExecAllQueries() throws SQLException, ExecutionException, InterruptedException, JSONException {
        //scaricare con data maggiore di ultimo update, eseguire e aggiornare ultimo update
        boolean online = settings.getBoolean("online", false);

        if(online) {

            Date now = new Date();
            String d = (now.getYear() + 1900) + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();

            String url = "http://192.168.1.34:3000/api/query/buffin/" + lastUpdate.split(" ")[0] + "%20" + lastUpdate.split(" ")[1];

            ArrayList<String> queries = new ArrayList<>();
            ConnectionController connectionController = new ConnectionController();
            String resp = connectionController.execute("GET", url).get();
            resp = resp.substring(0, resp.length() - 1);
            Log.w("RESP", resp);

            if (resp.length() > 0) {
                JSONArray jsonArray = new JSONArray(resp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String query = jsonObject.getString("query");
                    queries.add(query);
                    Log.w("QUERY", query);
                }

                QueryDAO dao = new QueryDAO_DB_impl();
                dao.open();
                dao.execQueries(queries);
                dao.close();
            }

            lastUpdate = d;
            editor.putString("lastUpdate",lastUpdate);
            editor.commit();
            return lastUpdate;
        }

        return "";
    }
}
