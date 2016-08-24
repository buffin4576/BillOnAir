package com.btd.billonair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaSpeseStanza extends ArrayAdapter<SpesaStanza>
{
    private ArrayList<SpesaStanza> LStanza;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaSpeseStanza myadapter;
    private Activity mactivity=null;
    private String[] myColors= {"#1256b4","#b41256","#12b456"};


    public AdapterListaSpeseStanza(Activity activity, Context context, int resource, ArrayList<SpesaStanza> objects)
    {
        super(context, resource, objects);
        LStanza=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
        mactivity=activity;
    }

    @Override
    public View getView(final int pos, View view, final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final SpesaStanza spstanza = LStanza.get(pos);
        TextView debitore=(TextView)view.findViewById(R.id.txtdebitore);
        TextView debito=(TextView)view.findViewById(R.id.txtdebito);
        TextView creditore=(TextView)view.findViewById(R.id.txtcreditore);
        TextView causale=(TextView)view.findViewById(R.id.txtcausale);
        TextView data=(TextView)view.findViewById(R.id.txtdataspesa);
        Formattazione form=new Formattazione();

        debitore.setText(spstanza.getDebitore());
        debito.setText(spstanza.getImporto()+"");
        creditore.setText(spstanza.getCreditore());
        causale.setText(spstanza.getNome());

        String[] d = spstanza.getData().split("T");
        String dataFormattata = d[0].split("-")[2]+"/"+d[0].split("-")[1]+"/"+d[0].split("-")[0];
        data.setText(dataFormattata);
        return view;

    }

}
