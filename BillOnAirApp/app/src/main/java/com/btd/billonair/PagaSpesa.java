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
