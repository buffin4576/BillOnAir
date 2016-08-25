package com.btd.billonair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Buffin on 25/08/2016.
 */
public class CalendarShowDay extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarday);
        String data = (String) getIntent().getSerializableExtra("data");

        String username = "offline";
        SharedPreferences sharedPreferences = getSharedPreferences("Shared",0);
        username = sharedPreferences.getString("username","offline");

        ArrayList<SpesaConto> spese = new ArrayList<>();
        ListView lv = (ListView)findViewById(R.id.listaMovimentiDay);

        SpesaContoDAO dao = new SpesaContoDAO_DB_impl();
        try {
            dao.open();
            spese.addAll(dao.getAllSpese(username));
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<SpesaConto> filtrate = new ArrayList<>();

        for(int i = 0; i < spese.size(); i++){
            String d = spese.get(i).getData();
            String dd = d.split(" ")[0];
            if(dd.equals(data))
            {
                filtrate.add(spese.get(i));
            }
        }


        AdapterListaDay ad = new AdapterListaDay(getApplicationContext(), R.layout.rigamovimentoday, filtrate);
        lv.setAdapter(ad);

    }
}
