package com.btd.billonair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Buffin on 25/08/2016.
 */
public class AdapterListaDay extends ArrayAdapter<SpesaConto> {

    private ArrayList<SpesaConto> LSpese;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaDay myadapter;


    public AdapterListaDay(Context context, int resource, ArrayList<SpesaConto> objects)
    {
        super(context, resource, objects);
        LSpese=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);

        TextView txtNome = (TextView)view.findViewById(R.id.txtNomeSpesaDay);
        TextView txtImporto = (TextView)view.findViewById(R.id.txtImportoSpesaDay);
        TextView txtData = (TextView)view.findViewById(R.id.txtDataSpesaDay);

        txtNome.setText(LSpese.get(pos).getNomeSpesa());
        String d = String.format("%.2f", Double.parseDouble(LSpese.get(pos).getCosto()+""));
        txtImporto.setText(d);
        String data = LSpese.get(pos).getData()+"";
        txtData.setText(data.split("-")[2]+"/"+data.split("-")[1]+"/"+data.split("-")[0]);

        return view;
    }

}
