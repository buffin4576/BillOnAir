package com.btd.billonair;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

//import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;

/**
 * Created by Buffin on 12/05/2016.
 */
public class ConnectionController extends AsyncTask<Object, Void, String>
{
    URL url;
    HttpURLConnection connection;
    DataOutputStream outputStream;
    String method;

    public ConnectionController(){}

    @Override
    protected String doInBackground(Object... params) {
        String response="";
        this.method = (String)params[0];
        switch (method)
        {
            case "GET":
                try {
                    return doGet((String)params[1]);
                } catch (IOException e) {
                    Query.SetToOffline();
                    e.printStackTrace();
                }break;
            case "POST":
                try {
                    return doPost((String)params[1],(JSONObject)params[2]);
                } catch (IOException e) {
                    Query.SetToOffline();
                    e.printStackTrace();
                }break;
            case "PUT":try {
                return doPut((String)params[1],(JSONObject)params[2]);
            } catch (IOException e) {
                Query.SetToOffline();
                e.printStackTrace();
            }break;
            case "DELETE":try {
                return doDelete((String)params[1]);
            } catch (IOException e) {
                Query.SetToOffline();
                e.printStackTrace();
            }break;
            default: break;
        }
        return response;
    }

    private void InitConnection(String url, String method) throws IOException {
        this.url = new URL(url);
        connection = (HttpURLConnection)this.url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod(method);
       /* connection.setDoInput(true);
        connection.setDoOutput(true);*/

        this.method=method;
    }

    public String doGet(String url) throws IOException {
        InitConnection(url, "GET");
        Log.w("GET","inside get "+url);
        connection.setDoInput(true);
        connection.connect();
        int HttpResult = connection.getResponseCode();

        Log.w("GET","httpresult: "+HttpResult);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                sb.append(s + "\n");
            }
            reader.close();
        }
        connection.disconnect();
        Log.w("GET",sb.toString());
        return sb.toString();
    }

    public String doPost(String url, JSONObject json) throws IOException {
        InitConnection(url, "POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/json");
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(json.toString());
        out.close();

        Log.w("JSON",json.toString());

        int HttpResult = connection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                sb.append(s + "\n");
            }
            reader.close();
        }
        connection.disconnect();

        Log.w("POST",sb.toString());
        return sb.toString();
    }

    public String doPut(String url, JSONObject json) throws IOException {
        InitConnection(url, "PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/json");
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(json.toString());
        out.close();

        Log.w("JSON",json.toString());

        int HttpResult = connection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                sb.append(s + "\n");
            }
            reader.close();
        }
        connection.disconnect();

        Log.w("PUT",sb.toString());
        return sb.toString();
    }

    public String doDelete(String url) throws IOException {
        InitConnection(url, "DELETE");
        Log.w("DELETE","inside get "+url);
        connection.setDoInput(true);
        connection.connect();
        int HttpResult = connection.getResponseCode();

        Log.w("DELETE","httpresult: "+HttpResult);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                sb.append(s + "\n");
            }
            reader.close();
        }
        connection.disconnect();
        Log.w("DELETE",sb.toString());
        return sb.toString();
    }

}
