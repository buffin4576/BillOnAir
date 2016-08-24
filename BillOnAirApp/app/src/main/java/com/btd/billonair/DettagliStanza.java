package com.btd.billonair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        Button addspesa=(Button)findViewById(R.id.addspesastanza);

        stanza= (Stanza) getIntent().getSerializableExtra("Stanza");
        LinearLayout linearUsers = (LinearLayout)findViewById(R.id.linearUtentiStanza);
        TextView txtNomeStanza = (TextView)findViewById(R.id.DettagliNomeStanza);
        txtNomeStanza.setText(stanza.getNome());
        users.addAll(stanza.getUsers());

        ArrayList<SpesaStanza> spese = stanza.getSpeseStanza();
        final Intent intent=new Intent(this,AggiungiSpesaStanza.class);
        final Bundle bund=new Bundle();
        bund.putSerializable("stanza",stanza);

        assert addspesa!=null;
        addspesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtras(bund);
                startActivity(intent);
            }
        });


        ListView lv = (ListView)findViewById(R.id.listSpeseStanza);

        /***************/

        /*ArrayList<ArrayList<SpesaStanza>> table = new ArrayList<>();
        int lastId=-1;
        ArrayList<SpesaStanza> riga = new ArrayList<>();
        for(int i = 0; i < spese.size(); i++){
            if(lastId!=spese.get(i).getIdSpesa()){
                table.add(riga);
                riga = new ArrayList<>();
                riga.add(spese.get(i));
                lastId=spese.get(i).getIdSpesa();
            }
            else
            {
                riga.add(spese.get(i));
            }
        }
        table.add(riga);
        table.remove(0);
        Log.w("Table",table.size()+"");*/
        /***************/

        //AdapterListaSpeseStanza adapterListaSpeseStanza = new AdapterListaSpeseStanza(getParent(),getApplicationContext(),R.layout.spesastanza, table);
        //lv.setAdapter(adapterListaSpeseStanza);

        ArrayList<Object[]> saldi = CalcolaSaldoUtente(spese,users,user);
        for(Object[] o:saldi){
            TextView txtUser = new TextView(this);
            txtUser.setText(o[0]+": "+o[1]);
            txtUser.setTag("stanza"+o[0]);
            txtUser.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            linearUsers.addView(txtUser);
        }
    }

    private ArrayList<Object[]> CalcolaSaldoUtente(ArrayList<SpesaStanza> spese, ArrayList<String> users, String user){
        ArrayList<Object[]> totali = new ArrayList<>();

        for(int i = 0; i < users.size(); i++){
            if(!users.get(i).equals(user)){
                double tot=0;
                for(int j = 0; j < spese.size(); j++){
                    if(spese.get(j).getCreditore().equals(user) && spese.get(j).getDebitore().equals(users.get(i))){
                        tot+=spese.get(j).getImporto();
                    }
                    if(spese.get(j).getCreditore().equals(users.get(i)) && spese.get(j).getDebitore().equals(user)){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_persona)
        {
            Intent intent = new Intent(this,AggiungiUtente.class);
            startActivity(intent);
        }
        if(id==R.id.add_spesa)
        {

        }
        if(id==R.id.delete_room)
        {

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        ArrayList<SpesaStanza> spese = stanza.getSpeseStanza();
        LinearLayout linearUsers = (LinearLayout)findViewById(R.id.linearUtentiStanza);
        ListView lv = (ListView)findViewById(R.id.listSpeseStanza);

        /***************/

        ArrayList<ArrayList<SpesaStanza>> table = new ArrayList<>();
        int lastId=-1;
        ArrayList<SpesaStanza> riga = new ArrayList<>();
        for(int i = 0; i < spese.size(); i++){
            if(lastId!=spese.get(i).getIdSpesa()){
                table.add(riga);
                riga = new ArrayList<>();
                riga.add(spese.get(i));
                lastId=spese.get(i).getIdSpesa();
            }
            else
            {
                riga.add(spese.get(i));
            }
        }
        table.remove(0);
        table.add(riga);
        /***************/

        AdapterListaSpeseStanza adapterListaSpeseStanza = new AdapterListaSpeseStanza(getParent(),getApplicationContext(),R.layout.spesastanza, table);
        lv.setAdapter(adapterListaSpeseStanza);

        ArrayList<Object[]> saldi = CalcolaSaldoUtente(spese,users,user);
        for(Object[] o:saldi){
            TextView temp=(TextView)linearUsers.findViewWithTag("stanza"+o[0]);
            temp.setText(o[0]+": "+o[1]);
        }

    }
}
