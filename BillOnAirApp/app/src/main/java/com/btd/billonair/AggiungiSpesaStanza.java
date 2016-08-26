package com.btd.billonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import org.json.JSONException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class AggiungiSpesaStanza extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiuntaspesastanza);

        final EditText causale=(EditText)findViewById(R.id.txtCausaleSpesaStanza);
        final EditText importo=(EditText)findViewById(R.id.txtImportSpesaStanza);
        final Stanza stanza =(Stanza) getIntent().getSerializableExtra("stanza");
        Button Aggiungi=(Button)findViewById(R.id.btnAddSpesaStanza);
        final SpesaStanza Sp=new SpesaStanza();
        final SharedPreferences preferences=getSharedPreferences("Shared",0);
        final String username=preferences.getString("username","offline");


        final ArrayList<String> users = stanza.getUsers();
        LinearLayout layoutUtenti = (LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);


        final Spinner spinnerConti = (Spinner)findViewById(R.id.spinnerContiStanza);

        importo.setText("0");

        ContoDAO dao=new ContoDAO_DB_impl();
        final ArrayList<Conto> LConti=new ArrayList<>();
        ArrayList<Conto> LConti1= null;
        SharedPreferences sharedPreferences = getSharedPreferences("Shared",0);
        String owner = sharedPreferences.getString("username","offline");

        try {
            dao.open();
            LConti1=(ArrayList<Conto>) dao.getAllConti(owner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.close();
        for (int i=0;i<LConti1.size();i++)
        {
            LConti.add(LConti1.get(i));
        }
        List<String> list = new ArrayList<String>();

        list.add("---------");
        for (int i=0;i<LConti.size();i++ )
        {
            list.add(LConti.get(i).getNomeConto());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConti.setAdapter(dataAdapter);


        for(int i = 0; i < users.size(); i++){
            LinearLayout h = new LinearLayout(this);
            h.setOrientation(LinearLayout.HORIZONTAL);
            h.setTag("layout"+users.get(i));

            TextView txtUtente = new TextView(this);
            txtUtente.setText(users.get(i));
            txtUtente.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            h.addView(txtUtente);

            EditText editUtente = new EditText(this);
            editUtente.setWidth(400);

            editUtente.setTag("edit"+users.get(i));

            h.addView(editUtente);

            layoutUtenti.addView(h);
        }

        assert importo != null;
        if (importo != null) {
            importo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    LinearLayout l = (LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);
                    double d = 0;
                    if(!(importo.getText()+"").equals(""))
                        d = Double.parseDouble(importo.getText()+"");
                    d=d/users.size();
                    for (int i = 0; i< users.size(); i++){
                        EditText editText = (EditText) l.findViewWithTag("edit"+users.get(i));
                        editText.setText(d+"");
                    }
                }
            });
        }
        final LinearLayout vertical=(LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);
        final EditText Editt=(EditText)vertical.findViewWithTag("edit"+username);

        Aggiungi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int id=0;
                    double dimporto=Double.parseDouble(importo.getText().toString());
                    double dovuto=Double.parseDouble(Editt.getText().toString());
                    String StCausale=causale.getText().toString();
                    id=Sp.CreaSpesaStanza(username,username,StCausale,(-1)*(dimporto-dovuto),stanza.getIdStanza(),dimporto);
                    for (int i=0;i<users.size();i++)
                    {
                        if(!users.get(i).equals(username))
                        {
                            EditText Edittuser=(EditText)vertical.findViewWithTag("edit"+users.get(i));
                            double ddovuto=Double.parseDouble(Edittuser.getText().toString());
                            Sp.AggiungiSpesaUtente(id,username,users.get(i),StCausale,ddovuto,stanza.getIdStanza(),ddovuto);

                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                            final String utcTime = sdf.format(new Date());
                            String testo=username +" ha aggiunto una spesa di "+ddovuto+"per "+StCausale;
                            Notifica n = new Notifica(StCausale,users.get(i),ddovuto,utcTime, -1, stanza.getIdStanza(),testo);

                            n.SendNotifica();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LinearLayout l = (LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);
                EditText editText = (EditText) l.findViewWithTag("edit"+username);

                if(spinnerConti.getSelectedItemPosition()!=0)
                    PagaDB(spinnerConti, LConti, (importo.getText()+""),causale.getText().toString());
                finish();
            }
        });
        /*SpesaStanza Sp=new SpesaStanza();
        int i=-1;
        try {
            i=Sp.CreaSpesaStanza("ernrico21","ernrico21","prova causale",-25,ST.getIdStanza(),100.00);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.w("idspesa",i+"");*/

    }

    private void PagaDB(Spinner SpinnerConti, ArrayList<Conto> LConti, String s_importo, String causale){
        String s =String.valueOf(SpinnerConti.getSelectedItem());
        boolean tr=false;
        int indice;
        for (indice=0;indice<LConti.size() && tr==false;indice++)
        {
            if(s.equals(LConti.get(indice).getNomeConto()))
            {
                tr=true;
            }
        }
        if(s_importo.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Import is empty";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        double importo=Double.parseDouble(s_importo);
        SpesaContoDAO dao = new SpesaContoDAO_DB_impl();
        ContoDAO dao2=new ContoDAO_DB_impl();
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //esecuzione spesa
        importo=importo*(-1);

        Conto selezionato=LConti.get(SpinnerConti.getSelectedItemPosition()-1);
        Date data=new Date();
        //SpesaConto sc=new SpesaConto(1,TxtDettagli.getText().toString(),importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-"+data.getDate(),selezionato.getNomeConto());

        //Denis' work
        SpesaConto sc;
        if(data.getMonth()+1<10){
            if(data.getDate()<10){
                sc=new SpesaConto(1,causale,importo,(data.getYear()+1900)+"-0"+(data.getMonth()+1)+"-0"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
            }
            else {
                sc = new SpesaConto(1, causale, importo, (data.getYear() + 1900) + "-0" + (data.getMonth() + 1) + "-" + data.getDate(), selezionato.getNomeConto(),selezionato.getOwner());
            }
        }
        else{
            if(data.getDate()<10){
                sc=new SpesaConto(1,causale,importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-0"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
            }
            else{
                sc=new SpesaConto(1,causale,importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
            }
        }

        Boolean br=dao.insertSpesa(sc);
        dao.close();
        if(br)
        {
            selezionato.setUltimaSpesa(sc);
        }
        try {
            dao2.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selezionato.setSaldo(selezionato.getSaldo()+importo);
        br=dao2.updateConto(selezionato,selezionato.getNomeConto());
        dao2.close();
    }
}