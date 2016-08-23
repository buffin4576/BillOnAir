package com.btd.billonair;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ernrico on 21/08/2016.
 */
public class DettagliStanza extends AppCompatActivity {
    Stanza stanza;
    ArrayList<String> users = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stanza);
        sharedPreferences = getSharedPreferences("Shared",0);
        user = sharedPreferences.getString("username","offline");

        stanza= (Stanza) getIntent().getSerializableExtra("Stanza");
        LinearLayout linearUsers = (LinearLayout)findViewById(R.id.linearUtentiStanza);
        TextView txtNomeStanza = (TextView)findViewById(R.id.DettagliNomeStanza);
        txtNomeStanza.setText(stanza.getNome());
        users.addAll(stanza.getUsers());
        ArrayList<Object[]> saldi = CalcolaSaldoUtente(stanza.getSpeseStanza(),users,user);
        for(Object[] o:saldi){
            TextView txtUser = new TextView(this);
            txtUser.setText(o[0]+": "+o[1]);
            txtUser.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            linearUsers.addView(txtUser);
            Log.w("Stanza",o[0]+": "+o[1]);
        }
    }

    private ArrayList<Object[]> CalcolaSaldoUtente(ArrayList<SpesaStanza> spese, ArrayList<String> users, String user){
        ArrayList<Object[]> totali = new ArrayList<>();

        for(int i = 0; i < users.size(); i++){
            if(users.get(i)!=user){
                double tot=0;
                for(int j = 0; j < spese.size(); j++){
                    if(spese.get(j).getCreditore()==user && spese.get(j).getDebitore()==users.get(i)){
                        tot+=spese.get(j).getImporto();
                    }
                    if(spese.get(j).getCreditore()==users.get(i) && spese.get(j).getDebitore()==user){
                        tot-=spese.get(j).getImporto();
                    }
                }
                Object[] o = new Object[]{users.get(i),tot};
                totali.add(o);
            }
        }

        return totali;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stanza, menu);
        return true;
    }

}
