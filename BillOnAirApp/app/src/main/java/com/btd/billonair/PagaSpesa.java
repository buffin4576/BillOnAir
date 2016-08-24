package com.btd.billonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class PagaSpesa extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagaspesastanza);
        final EditText importo=(EditText)findViewById(R.id.txtImportopagato);
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
                        if((sel.getDebitore().equals(Nome))&&sel.getDovuto()>0)
                        {
                            SpesaStanza temp;
                            if(sel.getDovuto()>pagato)
                            {
                                temp=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),sel.getDovuto()-pagato,sel.getData(),sel.getIdStanza(),sel.getImporto());
                                try {
                                    temp.AggiornaSpesaStanza(temp);
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
                                Double t=sel.getDovuto();
                                temp=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),0,sel.getData(),sel.getIdStanza(),sel.getImporto());
                                try {
                                    temp.AggiornaSpesaStanza(temp);
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
                    if(!(pagato>sel.getDovuto()))
                    {
                        SpesaStanza t=new SpesaStanza(sel.getIdSpesa(),sel.getCreditore(),sel.getDebitore(),sel.getNome(),sel.getDovuto()-pagato,sel.getData(),sel.getIdStanza(),sel.getImporto());
                        try {
                            t.AggiornaSpesaStanza(t);
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
}
