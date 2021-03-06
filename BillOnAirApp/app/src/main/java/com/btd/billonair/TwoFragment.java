package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import android.widget.SimpleAdapter;
import java.sql.SQLException;
import java.util.ArrayList;

public class TwoFragment extends Fragment
{
    public TwoFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
        String username = sharedPreferences.getString("username","offline");
        if(!username.equals("offline"))
            return inflater.inflate(R.layout.fragment_two, container, false);
        else
            return inflater.inflate(R.layout.void_layout, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
        String username = sharedPreferences.getString("username","offline");
        if(!username.equals("offline")) {
            final ListView lv = (ListView) getView().findViewById(R.id.ListaStanze);
            final Button addroom = (Button) getView().findViewById(R.id.aggiungistanza);

            addroom.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AggiungiStanza.class);
                    startActivity(intent);
                }
            });
        }
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
    public void onResume() {
        super.onResume();
        String username;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
        username = sharedPreferences.getString("username","offline");
        if(!username.equals("offline")) {
            ArrayList<Stanza> LStanza;
            Stanze Lst = new Stanze(username);
            LStanza = Lst.getStanze();
            Log.w("ListaStanze", "" + LStanza.size());

            final ListView lv = (ListView) getView().findViewById(R.id.ListaStanze);
            AdapterListaStanze adapterListaStanze = new AdapterListaStanze(getActivity(), getContext(), R.layout.rigastanza, LStanza);
            lv.setAdapter(adapterListaStanze);
        }
    }
/*
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            String username;
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
            username = sharedPreferences.getString("username","offline");
            ArrayList<Stanza> LStanza;
            Stanze Lst=new Stanze(username);
            LStanza=Lst.getStanze();
            Log.w("ListaStanze",""+LStanza.size());
        }
        else {
        }
    }*/

}
