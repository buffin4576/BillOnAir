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
import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaStanze extends ArrayAdapter<Stanza>
{
    private ArrayList<Stanza> LStanza;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaStanze myadapter;
    private Activity mactivity=null;
    private String[] myColors= {"#1256b4","#b41256","#12b456"};


    public AdapterListaStanze(Activity activity, Context context, int resource, ArrayList<Stanza> objects)
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
        final Stanza stanza = LStanza.get(pos);
        TextView nomeStanza=(TextView)view.findViewById(R.id.txtNomeStanza);
        TextView iniziale=(TextView)view.findViewById(R.id.TxtIniziale);
        Formattazione form=new Formattazione();


        iniziale.setText(stanza.getNome().substring(0,1).toUpperCase());
        String upperNomeConto = stanza.getNome().substring(0,1).toUpperCase() + stanza.getNome().substring(1);
        nomeStanza.setText(upperNomeConto);

        GradientDrawable shapeDrawable = (GradientDrawable )iniziale.getBackground();
        String c = myColors[pos%3];
        shapeDrawable.setColor(Color.parseColor(c));

        final ImageView freccia=(ImageView)view.findViewById(R.id.imgFrecciaStanza);
        final Bundle bund=new Bundle();
        freccia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(mContext,DettagliStanza.class);
                bund.putSerializable("Stanza",stanza);
                intent.putExtras(bund);
                mContext.startActivity(intent);
            }
        });

        return view;

    }

}
