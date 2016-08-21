package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        return inflater.inflate(R.layout.fragment_two, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        final ListView lv=(ListView)getView().findViewById(R.id.ListaStanze);
        final ImageView addRoom=(ImageView)getView().findViewById(R.id.addRoom);
        final  Button temp=(Button)getView().findViewById(R.id.button2);

        temp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 1;
                Intent intent = new Intent(getActivity(),DettagliStanza.class);
                startActivityForResult(intent, requestCode);
            }
        });

    }


}
