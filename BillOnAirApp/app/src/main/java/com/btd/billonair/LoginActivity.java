package com.btd.billonair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.btd.billonair.com.btd.billonair.db.MySQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    String ipServer = "https://billonair.herokuapp.com/";

    ViewSwitcher viewSwitcher;
    LinearLayout layoutLogin;
    LinearLayout layoutRegister;

    Button btnLogin;
    Button btnRegister;

    TextView linkRegister;
    TextView linkLogin;
    TextView linkOffline;
    TextView linkOffline2;

    ConnectionController connectionController;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
        layoutLogin = (LinearLayout)findViewById(R.id.layoutLogin);
        layoutRegister = (LinearLayout)findViewById(R.id.layoutRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        linkRegister = (TextView)findViewById(R.id.linkRegister);
        linkLogin = (TextView)findViewById(R.id.linkLogin);
        linkOffline = (TextView)findViewById(R.id.linkOffline);
        linkOffline2 = (TextView)findViewById(R.id.linkOffline2);

        //Aggiunta online
        settings = getSharedPreferences("Shared",0);
        editor = settings.edit();

        String userSetted = settings.getString("username","");
        if(!userSetted.equals("offline"))
            ((TextView)findViewById(R.id.txtLoginUsername)).setText(userSetted);
        else
            ((TextView)findViewById(R.id.txtLoginUsername)).setText("");
        ((TextView)findViewById(R.id.txtLoginPassword)).setText(settings.getString("password",""));

        //logout per prevenire il pulsante back ?

        //TEST db
        /*SQLiteDatabase database;
        MySQLiteHelper dbHelper=new MySQLiteHelper(getApplicationContext());
        database = dbHelper.getWritableDatabase();
        String[] columns = {"nomeConto","colore"};
        Cursor cursor = database.query("conti",columns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String s = "nomeConto: "+cursor.getString(0)+"   colore: "+cursor.getString(1);
            System.out.println(s);
            cursor.moveToNext();
        }*/
    }

    public void OnClickLink(View v){
        if((TextView)v==linkLogin)
        {
            viewSwitcher.showPrevious();
        }
        else
        if((TextView)v==linkRegister)
        {
            viewSwitcher.showNext();
        }
        else
        {
            //go offline
            Intent intent = new Intent(this,MainActivity.class);
            Bundle info = new Bundle();
            info.putString("mode","offline");
            intent.putExtra("info",info);
            editor.putBoolean("online",false);
            editor.putString("username","offline");
            editor.putString("password","");
            editor.commit();
            startActivity(intent);
        }
    }

    public void OnRegister(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle info = new Bundle();
        info.putString("mode", "register");
        intent.putExtra("info",info);
        //registrazione

        ConnectionController connectionController;
        connectionController = new ConnectionController();
        String resp="";
        try {
            //resp = connectionController.execute("GET","http://www.surrenderat20.net/").get();
            //passwrod da cifrare
            JSONObject json = new JSONObject();
            json.put("password",((EditText)findViewById(R.id.txtRegisterPassword)).getText());
            json.put("username", ((EditText) findViewById(R.id.txtRegisterUsername)).getText());
            String p1 = ((EditText)findViewById(R.id.txtRegisterPassword)).getText().toString();
            String p2 = ((EditText)findViewById(R.id.txtRegisterConfirmPassword)).getText().toString();
            if(p1.compareTo(p2)==0)
            {
                resp = connectionController.execute("POST", "https://billonair.herokuapp.com/api/users/register", json).get();
                json = new JSONObject(resp);
                String res = json.get("Message").toString();
                if(res.compareTo("Utente inserito")==0)
                {
                    Query.SvuotaDBOnline();
                    String lastUpdate = settings.getString("lastUpdate","1900-01-01 00:00:00");
                    Query.SetLastUpdate(lastUpdate);
                    editor.putBoolean("online",true);
                    editor.putString("password",p1);
                    editor.putString("username",((EditText) findViewById(R.id.txtRegisterUsername)).getText()+"");
                    editor.commit();
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Password non coincidenti",Toast.LENGTH_LONG);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("Debug","Resp: "+ resp);


        //startActivity(intent);
    }

    public void OnLogin(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle info = new Bundle();
        intent.putExtra("info",info);
        //login
        ConnectionController connectionController;
        connectionController = new ConnectionController();
        String resp="";
        try {
            //resp = connectionController.execute("GET","http://www.surrenderat20.net/").get();
            //passwrod da cifrare
            JSONObject json = new JSONObject();
            json.put("password",((EditText)findViewById(R.id.txtLoginPassword)).getText());
            json.put("username",((EditText)findViewById(R.id.txtLoginUsername)).getText());
            resp = connectionController.execute("POST",ipServer+"api/users/login",json).get();
            json = new JSONObject(resp);
            String res = json.get("Message").toString();
            if(res.compareTo("Login")==0)
            {
                String u = ((EditText) findViewById(R.id.txtLoginUsername)).getText()+"";
                String s = settings.getString("username","offline");
                if(!u.equals(s)) {
                    Query.SvuotaDBOnline();
                    editor.putString("lastUpdate","1900-01-01 00:00:00");
                    editor.commit();
                }

                String lastUpdate = settings.getString("lastUpdate","1900-01-01 00:00:00");
                Query.SetLastUpdate(lastUpdate);
                editor.putBoolean("online",true);
                editor.putString("password",((EditText)findViewById(R.id.txtLoginPassword)).getText()+"");
                editor.putString("username",((EditText) findViewById(R.id.txtLoginUsername)).getText()+"");
                editor.commit();
                startActivity(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("Debug",resp);

        //startActivity(intent);
    }
}