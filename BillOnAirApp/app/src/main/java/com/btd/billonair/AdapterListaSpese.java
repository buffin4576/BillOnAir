package com.btd.billonair;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
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
public class AdapterListaSpese extends ArrayAdapter<Spesa>
{
    private ArrayList<Spesa> LSpese;
    private Context mContext= null;
    private int mRowLayout;


    public AdapterListaSpese(Context context, int resource, ArrayList<Spesa> objects)
    {
        super(context, resource, objects);
        LSpese=objects;
        mContext=context;
        mRowLayout=resource;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final Spesa spesa = LSpese.get(pos);
        /*TextView nomeconto=(TextView)view.findViewById(R.id.TxtNomeConto);
        TextView saldocondo=(TextView)view.findViewById(R.id.TxtSaldoConto);
        TextView ultimaspesa=(TextView)view.findViewById(R.id.TxtUltimaSpesa);
        final ImageView imgconto=(ImageView)view.findViewById(R.id.ImgConto);
        nomeconto.setText(conto.getNomeConto());
        saldocondo.setText("Saldo:"+conto.getSaldo());
        ultimaspesa.setText("Ultima Spesa:"+conto.getUltimaSpesa().getCosto());

        imgconto.setBackgroundColor(Color.parseColor("#"+conto.getColore()));*/

        TextView NomeSpesa=(TextView)view.findViewById(R.id.NomeSpesa);
        TextView CostoSpesa=(TextView)view.findViewById(R.id.CostoSpesa);
        TextView DataSpesa=(TextView)view.findViewById(R.id.DataSpesa);

        NomeSpesa.setText(spesa.getNome());
        CostoSpesa.setText(""+spesa.getCosto());
        DataSpesa.setText(spesa.getData());

        return view;

    }

}
