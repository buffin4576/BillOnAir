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

import java.util.ArrayList;
import java.util.List;


public class DettagliConto extends AppCompatActivity {
    // private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagliconto);

        Conto conto=(Conto) getIntent().getSerializableExtra("Conto");
        TextView DNomeConto=(TextView)findViewById(R.id.DettagliNomeConto);
        TextView DSaldoConto=(TextView)findViewById(R.id.DettagliSaldoConto);
        ListView lv= (ListView)findViewById(R.id.DettagliListaSpese);
        Button Indietro=(Button) findViewById(R.id.buttonIndietro);

        DNomeConto.setText(conto.getNomeConto());
        DSaldoConto.setText(""+conto.getSaldo());
        lv.setAdapter(new AdapterListaSpese(this,R.layout.dettaglispesa,conto.getListaSpese()));

        Indietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
