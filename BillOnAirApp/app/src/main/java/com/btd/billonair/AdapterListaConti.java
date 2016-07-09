package com.btd.billonair;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaConti extends ArrayAdapter<Conto>
{
    private ArrayList<Conto> LConti;
    private Context mContext= null;
    private int mRowLayout;


    public AdapterListaConti(Context context, int resource, ArrayList<Conto> objects)
    {
        super(context, resource, objects);
        LConti=objects;
        mContext=context;
        mRowLayout=resource;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final Conto conto = LConti.get(pos);
        TextView nomeconto=(TextView)view.findViewById(R.id.TxtNomeConto);
        TextView saldocondo=(TextView)view.findViewById(R.id.TxtSaldoConto);
        TextView ultimaspesa=(TextView)view.findViewById(R.id.TxtUltimaSpesa);
        final ImageView imgconto=(ImageView)view.findViewById(R.id.ImgConto);
        nomeconto.setText(conto.getNomeConto());
        saldocondo.setText("Saldo:"+conto.getSaldo());
        ultimaspesa.setText("Ultima Spesa:"+conto.getUltimaSpesa().getCosto());
        imgconto.setColorFilter(Color.parseColor(conto.getColore()));

        return view;

    }
}
