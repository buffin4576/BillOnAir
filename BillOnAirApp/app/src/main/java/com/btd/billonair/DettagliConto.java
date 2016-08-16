package com.btd.billonair;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagliconto);

        conto=(Conto) getIntent().getSerializableExtra("Conto");
        TextView DNomeConto=(TextView)findViewById(R.id.DettagliNomeConto);
        final TextView DSaldoConto=(TextView)findViewById(R.id.DettagliSaldoConto);
        ListView lv= (ListView)findViewById(R.id.DettagliListaSpese);
        Button Indietro=(Button) findViewById(R.id.buttonIndietro);
        AdapterListaSpese adapter=null;
        try {
            adapter=new AdapterListaSpese(this,R.layout.dettaglispesa,conto.getSpeseConto());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter.setOnDataChangeListener(new AdapterListaSpese.OnDataChangeListener(){
            public void onDataChanged(int size){
                ContoDAO dao= new ContoDAO_DB_impl();
                try {
                    dao.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    DSaldoConto.setText(dao.getContoByName(conto.getNomeConto()).getSaldo()+"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dao.close();
            }
        });

        DNomeConto.setText(conto.getNomeConto());
        DSaldoConto.setText(""+conto.getSaldo());
        lv.setAdapter(adapter);



        Indietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
