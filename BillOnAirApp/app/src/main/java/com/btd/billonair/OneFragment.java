package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import android.widget.SimpleAdapter;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;

public class OneFragment extends Fragment
{
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

        ContoDAO dao=new ContoDAO_DB_impl();

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Conto> LConti= null;
        try {
            LConti = (ArrayList<Conto>) dao.getAllConti();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //apro db
        //creazione array di tipo conto prendendo le info dal db
        dao.close();
        //chiudo il db
        lv.setAdapter(new AdapterListaConti(getContext(),R.layout.rigaconto,LConti));
        final Bundle bund=new Bundle();
        bund.putSerializable("ListaConti",LConti);
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
                Intent intent = new Intent(getActivity(),AggiuntaSpesaEntrata.class);
                bund.putCharSequence("tipo","Entrata");
                intent.putExtras(bund);
                startActivity(intent);
            }
        });
        BTSpesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AggiuntaSpesaEntrata.class);
                bund.putCharSequence("tipo","Spesa");
                intent.putExtras(bund);
                startActivity(intent);
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

    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }


}
