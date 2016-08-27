package com.btd.billonair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DettagliConto extends AppCompatActivity {
    // private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Conto conto;
    private String owner;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagliconto);
        context=this;
        conto=(Conto) getIntent().getSerializableExtra("Conto");
        final TextView DNomeConto=(TextView)findViewById(R.id.DettagliNomeConto);
        final TextView DSaldoConto=(TextView)findViewById(R.id.DettagliSaldoConto);
        ListView lv= (ListView)findViewById(R.id.DettagliListaSpese);
        Button Indietro=(Button) findViewById(R.id.buttonIndietro);
        AdapterListaSpese adapter=null;
        SharedPreferences sharedPreferences = getSharedPreferences("Shared",0);
        owner = sharedPreferences.getString("username","offline");
        final Formattazione form=new Formattazione();
        try {
            adapter=new AdapterListaSpese(context,R.layout.dettaglispesa,conto.getSpeseConto());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        DNomeConto.setText(conto.getNomeConto());
        DSaldoConto.setText(""+form.Soldi(conto.getSaldo()));
        lv.setAdapter(adapter);


        adapter.setOnDataChangeListener(new AdapterListaSpese.OnDataChangeListener(){
            public void onDataChanged(int size){
                ContoDAO dao= new ContoDAO_DB_impl();
                try {
                    dao.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    DSaldoConto.setText(form.Soldi(dao.getContoByName(conto.getNomeConto(),conto.getOwner()).getSaldo())+"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dao.close();
            }
        });


/*
        DNomeConto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    ArrayList<Conto> LC=null;
                    String NuovoNome=DNomeConto.getText().toString();
                    String VecchioNome=conto.getNomeConto();
                    Boolean b=true;
                    ContoDAO dao=new ContoDAO_DB_impl();
                    try {
                        dao.open();
                        LC=(ArrayList<Conto>) dao.getAllConti(owner);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<LC.size() && b;i++)
                    {
                        if(LC.get(i).getNomeConto().equals(NuovoNome))
                        {
                            b=false;
                        }
                    }
                    if(b)
                    {
                        conto.setNomeConto(NuovoNome);
                        dao.updateConto(conto, VecchioNome);;
                        AdapterListaSpese adapter = null;
                        try {
                            adapter = new AdapterListaSpese(context, R.layout.dettaglispesa, conto.getSpeseConto());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ListView lv = (ListView) findViewById(R.id.DettagliListaSpese);
                        lv.setAdapter(adapter);
                    }
                    dao.close();
                }
            }
        });*/

        Indietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
