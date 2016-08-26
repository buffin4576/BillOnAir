package com.btd.billonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class PagaSpesa extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagaspesastanza);
        final EditText importo=(EditText)findViewById(R.id.txtImportopagato);

        final Spinner spinnerConti = (Spinner)findViewById(R.id.spinnerPagaSpesaStanza);

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

        Button BtnPagato=(Button)findViewById(R.id.BtnPagato);
        String type=getIntent().getStringExtra("type");

        if(type.equals("total"))
        {
            final double debito=getIntent().getDoubleExtra("debito",0);
            importo.setText(Double.toString(debito));
            assert BtnPagato != null;
            BtnPagato.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Stanza stanza=(Stanza)getIntent().getSerializableExtra("stanza");
                    ArrayList<SpesaStanza>Tutte=stanza.getSpeseStanza();
                    String Nome=getIntent().getStringExtra("nome");
                    double pagato=Double.parseDouble(importo.getText().toString());
                    for (int i=0;i<Tutte.size() && pagato>0;i++)
                    {
                        SpesaStanza sel=Tutte.get(i);
                        double dd=sel.getDovuto();
                        String sss=sel.getDebitore();
                        SpesaStanza temp2=null;
                        SpesaStanza temp3;
                        if((sel.getDebitore().equals(Nome))&&sel.getDovuto()>0)
                        {
                            SpesaStanza temp;
                            if(sel.getDovuto()>pagato)
                            {
                                for ( int y=0;y<Tutte.size() && pagato>0;y++ )
                                {
                                    if(Tutte.get(y).getIdSpesa()==sel.getIdSpesa()&& Tutte.get(y).getCreditore().equals(Tutte.get(y).getDebitore()))
                                    {
                                        temp2=Tutte.get(y);
                                    }
                                }
                                temp=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),sel.getDovuto()-pagato,sel.getData(),sel.getIdStanza(),sel.getImporto());
                                temp3=new SpesaStanza(temp2.getIdSpesa(),temp2.getCreditore(),temp2.getDebitore(),temp2.getNome(),temp2.getDovuto()+pagato,temp2.getData(),temp2.getIdStanza(),temp2.getImporto());
                                try {
                                    temp.AggiornaSpesaStanza(temp);
                                    temp3.AggiornaSpesaStanza(temp3);

                                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    final String utcTime = sdf.format(new Date());
                                    String testo=temp.getCreditore()+" ti ha saldato "+pagato+" per la spesa "+temp.getNome();
                                    Notifica n = new Notifica(temp.getNome(),temp.getDebitore(),temp.getDovuto(),utcTime, -1, temp.getIdStanza(),testo);

                                    n.SendNotifica();


                                    if(spinnerConti.getSelectedItemPosition()!=0)
                                        PagaDB(spinnerConti, LConti, pagato+"",temp.getNome());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                pagato=0;
                            }
                            else
                            {
                                for ( int y=0;y<Tutte.size() && pagato>0;y++ )
                                {
                                    if(Tutte.get(y).getIdSpesa()==sel.getIdSpesa()&& Tutte.get(y).getCreditore().equals(Tutte.get(y).getDebitore()))
                                    {
                                        temp2=Tutte.get(y);
                                    }
                                }
                                Double t=sel.getDovuto();
                                temp=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),0,sel.getData(),sel.getIdStanza(),sel.getImporto());
                                temp3=new SpesaStanza(temp2.getIdSpesa(),temp2.getCreditore(),temp2.getDebitore(),temp2.getNome(),0,temp2.getData(),temp2.getIdStanza(),temp2.getImporto());

                                try {
                                    temp.AggiornaSpesaStanza(temp);
                                    temp3.AggiornaSpesaStanza(temp3);

                                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    final String utcTime = sdf.format(new Date());
                                    String testo=temp.getCreditore()+" ti ha saldato "+pagato+" per la spesa "+temp.getNome();
                                    Notifica n = new Notifica(temp.getNome(),temp.getDebitore(),temp.getDovuto(),utcTime, -1, temp.getIdStanza(),testo);

                                    n.SendNotifica();

                                    if(spinnerConti.getSelectedItemPosition()!=0)
                                        PagaDB(spinnerConti, LConti, sel.getDovuto()+"",temp.getNome());
                                } catch (JSONException e) {
                                  e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                pagato-=t;
                            }
                        }
                    }
                    finish();
                }

            });

        }
        else
        {
            final SpesaStanza sel=(SpesaStanza) getIntent().getSerializableExtra("spesa");
            importo.setText(sel.getDovuto()+"");

            BtnPagato.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    double pagato=Double.parseDouble(importo.getText().toString());
                    ArrayList<SpesaStanza>Tutte=(ArrayList<SpesaStanza>)getIntent().getSerializableExtra("lista");
                    if(!(pagato>sel.getDovuto()))
                    {
                        SpesaStanza temp2=null;
                        SpesaStanza temp3=null;
                        for ( int y=0;y<Tutte.size() && pagato>0;y++ )
                        {
                            if(Tutte.get(y).getIdSpesa()==sel.getIdSpesa()&& Tutte.get(y).getCreditore().equals(Tutte.get(y).getDebitore()))
                            {
                                temp2=Tutte.get(y);
                            }
                        }
                        SpesaStanza t=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),sel.getDovuto()-pagato,sel.getData(),sel.getIdStanza(),sel.getImporto());
                        temp3=new SpesaStanza(temp2.getIdSpesa(),temp2.getCreditore(),temp2.getDebitore(),temp2.getNome(),temp2.getDovuto()+pagato,temp2.getData(),temp2.getIdStanza(),temp2.getImporto());
                        try {
                            t.AggiornaSpesaStanza(t);
                            temp3.AggiornaSpesaStanza(temp3);

                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                            final String utcTime = sdf.format(new Date());
                            String testo=t.getCreditore()+" ti ha saldato "+pagato+" per la spesa "+t.getNome();
                            Notifica n = new Notifica(t.getNome(),t.getDebitore(),t.getDovuto(),utcTime, -1, t.getIdStanza(),testo);

                            n.SendNotifica();

                            if(spinnerConti.getSelectedItemPosition()!=0)
                                PagaDB(spinnerConti, LConti, pagato+"",t.getNome());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    finish();
                }
            });

        }
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
        //importo=importo*(-1);

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
