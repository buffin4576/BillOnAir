package com.btd.billonair;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ernrico on 24/06/2016.
 */
public class NuovoConto extends Activity implements View.OnClickListener {

    String Colore;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);


/*
        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int width =DM.widthPixels;
        int height= DM.heightPixels;

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            //getWindow().setLayout((int) (width * 0.8), 840);
            //setTheme(R.style.AppTheme_CustomTheme);
            //getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        else
            getWindow().setLayout(width,height);
*/
        setContentView(R.layout.nuovoconto);

        ((EditText)findViewById(R.id.TxtSaldoNuovoConto)).setText("0");

        final Button AggiungiButton = (Button)findViewById(R.id.ButtonAggiungi);
        final Button CloseButton = (Button)findViewById(R.id.ButtonClose);

        final ImageView colore1 = (ImageView)findViewById(R.id.colore1);
        colore1.setOnClickListener(this);
        final ImageView colore2 = (ImageView)findViewById(R.id.colore2);
        colore2.setOnClickListener(this);
        final ImageView colore3 = (ImageView)findViewById(R.id.colore3);
        colore3.setOnClickListener(this);
        final ImageView colore4 = (ImageView)findViewById(R.id.colore4);
        colore4.setOnClickListener(this);
        final ImageView colore5 = (ImageView)findViewById(R.id.colore5);
        colore5.setOnClickListener(this);
        final ImageView colore6 = (ImageView)findViewById(R.id.colore6);
        colore6.setOnClickListener(this);
        final ImageView colore7 = (ImageView)findViewById(R.id.colore7);
        colore7.setOnClickListener(this);
        final ImageView colore8 = (ImageView)findViewById(R.id.colore8);
        colore8.setOnClickListener(this);
        final ImageView colore9 = (ImageView)findViewById(R.id.colore9);
        colore9.setOnClickListener(this);
        final ImageView colore10 = (ImageView)findViewById(R.id.colore10);
        colore10.setOnClickListener(this);

        final ImageView colore11 = (ImageView)findViewById(R.id.colore11);
        colore11.setOnClickListener(this);
        final ImageView colore12 = (ImageView)findViewById(R.id.colore12);
        colore12.setOnClickListener(this);
        final ImageView colore13 = (ImageView)findViewById(R.id.colore13);
        colore13.setOnClickListener(this);
        final ImageView colore14 = (ImageView)findViewById(R.id.colore14);
        colore14.setOnClickListener(this);
        final ImageView colore15 = (ImageView)findViewById(R.id.colore15);
        colore15.setOnClickListener(this);
        final ImageView colore16 = (ImageView)findViewById(R.id.colore16);
        colore16.setOnClickListener(this);
        final ImageView colore17 = (ImageView)findViewById(R.id.colore17);
        colore17.setOnClickListener(this);
        final ImageView colore18 = (ImageView)findViewById(R.id.colore18);
        colore18.setOnClickListener(this);
        final ImageView colore19 = (ImageView)findViewById(R.id.colore19);
        colore19.setOnClickListener(this);
        final ImageView colore20 = (ImageView)findViewById(R.id.colore20);
        colore20.setOnClickListener(this);



        AggiungiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              String Nome=((EditText)findViewById(R.id.TxtNomeNuovoConto)).getText().toString();
              String SaldoS=((EditText)findViewById(R.id.TxtSaldoNuovoConto)).getText().toString();
              Double Saldo=Double.parseDouble(SaldoS);
                Conto NConto= null;
                try {
                    NConto = new Conto(Nome,Saldo,Colore);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ArrayList<Conto> LConti= (ArrayList<Conto>) getIntent().getSerializableExtra("ListaConti");

               ContoDAO dao = new ContoDAO_DB_impl();
               try {
                   dao.open();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               Boolean Ret = dao.insertConto(NConto);
               dao.close();
                if(Ret)
                {

                }
                else
                {

                }
              finish();
            }
        });
        CloseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onClick(View v) {
       // Log.w("clicklistener","Nel metodo clicklistener");
      //  Color=((ImageView)v).getBackground().toString();
        ColorDrawable BGColor=(ColorDrawable)(((ImageView)v).getBackground());
        int Cl =BGColor.getColor();
        Colore = String.format("#%06X", 0xFFFFFF & Cl);
        ((TextView)findViewById(R.id.textView5)).setText(Colore);

    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {

    }
}
