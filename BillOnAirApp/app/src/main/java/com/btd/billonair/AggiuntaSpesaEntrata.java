package com.btd.billonair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ernrico on 10/07/2016.
 */
public class AggiuntaSpesaEntrata extends Activity {

    String owner="offline";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.aggiuntaspesaentrata);

/*
        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int width =DM.widthPixels;
        int height= DM.heightPixels;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().setLayout((int)(width*0.8),840);
        else
            getWindow().setLayout(width,height);*/

        final TextView Titolo=(TextView)findViewById(R.id.AggSETitolo);
        final Spinner SpinnerConti=(Spinner)findViewById(R.id.SpinnerConti);
        final String tipo= (String) getIntent().getCharSequenceExtra("tipo");
        final ArrayList<Conto> LConti=new ArrayList<>();
        ArrayList<Conto> LConti1= null;
        final Button BTAggiungi=(Button)findViewById(R.id.BTSEConferma);
        final Button BTAnnulla=(Button)findViewById(R.id.BTSEAnulla);
        final EditText TxtImporto=(EditText)findViewById(R.id.txtImporto);
        final EditText TxtDettagli=(EditText)findViewById(R.id.txtDettagli);

        ContoDAO dao=new ContoDAO_DB_impl();

        SharedPreferences sharedPreferences = getSharedPreferences("Shared",0);
        owner = sharedPreferences.getString("username","offline");

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

        for (int i=0;i<LConti.size();i++ )
        {
            list.add(LConti.get(i).getNomeConto());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConti.setAdapter(dataAdapter);
        Titolo.setText(tipo);

        BTAggiungi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                if(TxtImporto.getText().toString().equals(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Import is empty";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                double importo=Double.parseDouble(TxtImporto.getText().toString());
                SpesaContoDAO dao = new SpesaContoDAO_DB_impl();
                ContoDAO dao2=new ContoDAO_DB_impl();
                    try {
                    dao.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(tipo.equals("Entrata"))
                {
                    //esecuzione entrata

                }
                else
                {
                    //esecuzione spesa
                    importo=importo*(-1);
                }
                Conto selezionato=LConti.get(SpinnerConti.getSelectedItemPosition());
                Date data=new Date();
                //SpesaConto sc=new SpesaConto(1,TxtDettagli.getText().toString(),importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-"+data.getDate(),selezionato.getNomeConto());

                //Denis' work
                SpesaConto sc;
                if(data.getMonth()+1<10){
                    if(data.getDate()<10){
                        sc=new SpesaConto(1,TxtDettagli.getText().toString(),importo,(data.getYear()+1900)+"-0"+(data.getMonth()+1)+"-0"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
                    }
                    else {
                        sc = new SpesaConto(1, TxtDettagli.getText().toString(), importo, (data.getYear() + 1900) + "-0" + (data.getMonth() + 1) + "-" + data.getDate(), selezionato.getNomeConto(),selezionato.getOwner());
                    }
                }
                else{
                    if(data.getDate()<10){
                        sc=new SpesaConto(1,TxtDettagli.getText().toString(),importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-0"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
                    }
                    else{
                        sc=new SpesaConto(1,TxtDettagli.getText().toString(),importo,(data.getYear()+1900)+"-"+(data.getMonth()+1)+"-"+data.getDate(),selezionato.getNomeConto(),selezionato.getOwner());
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
                finish();
            }
        });


        BTAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
