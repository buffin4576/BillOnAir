package com.btd.billonair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ernrico on 21/08/2016.
 */
public class DettagliStanza extends AppCompatActivity {
    Stanza stanza;
    ArrayList<String> users = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stanza);
        stanza= (Stanza) getIntent().getSerializableExtra("Stanza");
        LinearLayout linearUsers = (LinearLayout)findViewById(R.id.linearUtentiStanza);
        TextView txtNomeStanza = (TextView)findViewById(R.id.DettagliNomeStanza);
        txtNomeStanza.setText(stanza.getNome());
        users.addAll(stanza.getUsers());
        for(String user:users){
            TextView txtUser = new TextView(getApplicationContext());
            txtUser.setText(user);
            linearUsers.addView(txtUser);
            Log.w("Stanza",user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stanza, menu);
        return true;
    }
}
