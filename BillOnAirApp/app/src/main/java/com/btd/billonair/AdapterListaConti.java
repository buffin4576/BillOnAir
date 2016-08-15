package com.btd.billonair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaConti extends ArrayAdapter<Conto>
{
    private ArrayList<Conto> LConti;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaConti myadapter;


    public AdapterListaConti(Context context, int resource, ArrayList<Conto> objects)
    {
        super(context, resource, objects);
        LConti=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
    }
    @Override
    public View getView(final int pos, View view, final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final Conto conto = LConti.get(pos);
        TextView nomeconto=(TextView)view.findViewById(R.id.TxtNomeConto);
        TextView saldocondo=(TextView)view.findViewById(R.id.TxtSaldoConto);
        TextView ultimaspesa=(TextView)view.findViewById(R.id.TxtUltimaSpesa);
        final ImageView imgconto=(ImageView)view.findViewById(R.id.ImgConto);
        final ImageView deleteconto=(ImageView)view.findViewById(R.id.deleteconto);

        String upperNomeConto = conto.getNomeConto().substring(0,1).toUpperCase() + conto.getNomeConto().substring(1);
        nomeconto.setText(upperNomeConto);
        saldocondo.setText("Saldo:"+conto.getSaldo());
        ultimaspesa.setText("Ultima Spesa:"+conto.getUltimaSpesa().getCosto());

        //imgconto.setBackgroundColor(Color.parseColor(conto.getColore()));
        int color = Color.parseColor(conto.getColore()); //The color u want
        imgconto.setColorFilter(color);

        final ImageView freccia=(ImageView)view.findViewById(R.id.Imagefreccia);
        final Bundle bund=new Bundle();
        freccia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(mContext,DettagliConto.class);
                bund.putSerializable("Conto",conto);
                intent.putExtras(bund);
                mContext.startActivity(intent);
            }
        });
        deleteconto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alertDlg=new AlertDialog.Builder(mContext);
                alertDlg.setMessage("Sei sicuto di volere cancellare questo conto?");

                alertDlg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContoDAO dao = new ContoDAO_DB_impl();
                        try {
                            dao.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Boolean Ret=dao.deleteConto(LConti.get(pos));

                        if(Ret)
                        {
                            LConti.remove(pos);
                            myadapter.notifyDataSetChanged();
                        }
                        else
                        {

                        }
                    }
                });

                alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDlg.create().show();
            }
        });
        return view;

    }

}
