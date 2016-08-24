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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ArrayList<Object[]> lista = new ArrayList<>();
    private String username="offline";


    public AdapterListaSpeseStanza(Activity activity, Context context, int resource, ArrayList<SpesaStanza> objects)
    {
        super(context, resource, objects);
        LStanza=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
        mactivity=activity;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Shared",0);
        username = sharedPreferences.getString("username","offline");
    }

    @Override
    public View getView(final int pos, View view, final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final SpesaStanza spstanza = LStanza.get(pos);
        TextView creditore=(TextView)view.findViewById(R.id.txtcreditore);
        TextView causale=(TextView)view.findViewById(R.id.txtcausale);
        TextView data=(TextView)view.findViewById(R.id.txtdataspesa);
        Formattazione form=new Formattazione();

        creditore.setText(spstanza.getCreditore());
        causale.setText(spstanza.getNome());

        LinearLayout l = (LinearLayout)view.findViewById(R.id.verticalListautentiSpesa);

        LinearLayout h = new LinearLayout(view.getContext());
        h.setOrientation(LinearLayout.VERTICAL);
        h.setTag("layout"+LStanza.get(pos).getNome());

        for(SpesaStanza spesaStanza:LStanza){
            if(spesaStanza.getIdSpesa()==LStanza.get(pos).getIdSpesa()){
                if(!spesaStanza.getDebitore().equals(username)) {
                    TextView u = new TextView(getContext());
                    u.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    u.setText(spesaStanza.getDebitore() + " deve " + spesaStanza.getDovuto());
                    int c = Color.parseColor("#555555");
                    u.setTextColor(c);
                    h.addView(u);
                }
            }
        }
        l.addView(h);

        String[] d = spstanza.getData().split("T");
        String dataFormattata = d[0].split("-")[2]+"/"+d[0].split("-")[1]+"/"+d[0].split("-")[0];
        data.setText(dataFormattata);
        return view;

    }

}
