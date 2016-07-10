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
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import android.widget.SimpleAdapter;
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
        ListView lv = (ListView) getView().findViewById(R.id.ListaConti);
        //DBOperations.getInstance(getActivity().getApplicationContext()).open();

        ContoDAO dao=new ContoDAO_DB_impl();

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Conto> LConti= (ArrayList<Conto>) dao.getAllConti();
        //apro db
        //creazione array di tipo conto prendendo le info dal db
        dao.close();
        //chiudo il db
        lv.setAdapter(new AdapterListaConti(getContext(),R.layout.rigaconto,LConti));
        final Bundle bund=new Bundle();
        bund.putSerializable("ListaConti",LConti);
        final Button NCButton=(Button)getView().findViewById(R.id.NCButton);
        NCButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NuovoConto.class);
                intent.putExtras(bund);
                startActivity(intent);
            }
        });
    }


}
