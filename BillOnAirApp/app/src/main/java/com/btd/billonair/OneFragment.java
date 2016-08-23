package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OneFragment extends Fragment
{
    ArrayList<SpesaConto>lusp;
    ArrayList<Conto> LConti;
    String owner="offline";
    boolean started=false;
    public OneFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_one, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        final ListView lv = (ListView) getView().findViewById(R.id.ListaConti);
        //DBOperations.getInstance(getActivity().getApplicationContext()).open();
        final Button NCButton=(Button)getView().findViewById(R.id.NCButton);
        final Button BTEntrata=(Button)getView().findViewById(R.id.BtEntrata);
        final Button BTSpesa=(Button)getView().findViewById(R.id.BtSpesa);
        started=true;

        final Bundle bund=new Bundle();
        NCButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 1; // Or some number you choose
                Intent intent = new Intent(getActivity(),NuovoConto.class);
                intent.putExtras(bund);
                startActivityForResult(intent, requestCode);
            }
        });


        BTEntrata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 1;
                Intent intent = new Intent(getActivity(),AggiuntaSpesaEntrata.class);
                bund.putCharSequence("tipo","Entrata");
                intent.putExtras(bund);
                startActivityForResult(intent, requestCode);
            }
        });
        BTSpesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 1;
                Intent intent = new Intent(getActivity(),AggiuntaSpesaEntrata.class);
                bund.putCharSequence("tipo","Spesa");
                intent.putExtras(bund);
                startActivityForResult(intent, requestCode);
            }
        });


        lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


    }



    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

            started=false;
            ListView lv = (ListView) getView().findViewById(R.id.ListaConti);
            ContoDAO dao=new ContoDAO_DB_impl();
            TextView TxtSpesa=(TextView)getView().findViewById(R.id.TxtSpesa);
            TextView TxtEntrata=(TextView)getView().findViewById(R.id.TxtEntrata);
            ArrayList<SpesaConto>allsc=null;
            Formattazione form=new Formattazione();
            String sd;
            String mese;
            Date now= new Date();
            double spese=0;
            double entrate=0;

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
            owner = sharedPreferences.getString("username","offline");

            try {
                dao.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LConti= null;
            try {
                LConti = (ArrayList<Conto>) dao.getAllConti(owner);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dao.close();

            lv.setAdapter(new AdapterListaConti(getActivity(),getContext(),R.layout.rigaconto,LConti));

            SpesaContoDAO dao1=new SpesaContoDAO_DB_impl();
            try {
                dao1.open();
                allsc= (ArrayList<SpesaConto>) dao1.getAllSpese(owner);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dao1.close();
            if((now.getMonth()+1)<10)
            {
                mese="0"+(now.getMonth()+1);
            }
            else
            {
                mese=now.getMonth()+"";
            }
            for (int i=0;i<allsc.size();i++)
            {
                sd=allsc.get(i).getData();

                if(((sd.split("-"))[1]).equals(mese))
                {
                    if(allsc.get(i).getCosto()<0)
                    {
                        spese-=allsc.get(i).getCosto();
                    }
                    else
                    {
                        entrate+=allsc.get(i).getCosto();
                    }
                }
            }
            TxtSpesa.setText(form.Soldi(spese));
            TxtEntrata.setText(form.Soldi(entrate));


        //INSERT CUSTOM CODE HERE
    }






}



