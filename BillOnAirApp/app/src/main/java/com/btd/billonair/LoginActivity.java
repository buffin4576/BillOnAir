package com.btd.billonair;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
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

        //logout per prevenire il pulsante back ?
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
                resp = connectionController.execute("POST", "http://10.196.75.26:3000/api/users/register", json).get();
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
            resp = connectionController.execute("POST","http://httpbin.org/post",json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Debug",resp);

        //startActivity(intent);
    }
}