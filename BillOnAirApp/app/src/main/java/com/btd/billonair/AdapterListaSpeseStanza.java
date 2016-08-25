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
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaSpeseStanza extends ArrayAdapter<ArrayList<SpesaStanza>>
{
    private ArrayList<ArrayList<SpesaStanza>> LStanza;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaSpeseStanza myadapter;
    private Activity mactivity=null;
    private String[] myColors= {"#1256b4","#b41256","#12b456"};
    private ArrayList<Object[]> lista = new ArrayList<>();
    private String username="offline";


    public AdapterListaSpeseStanza(Activity activity, Context context, int resource, ArrayList<ArrayList<SpesaStanza>> objects)
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
        final ArrayList<SpesaStanza> spstanza = LStanza.get(pos);
        TextView creditore=(TextView)view.findViewById(R.id.txtcreditore);
        TextView causale=(TextView)view.findViewById(R.id.txtcausale);
        TextView data=(TextView)view.findViewById(R.id.txtdataspesa);
        ImageView deleteSpesa=(ImageView) view.findViewById(R.id.deleteSpesaStanza);
        Formattazione form=new Formattazione();

        creditore.setText(spstanza.get(0).getCreditore());
        causale.setText(spstanza.get(0).getNome());

        LinearLayout l = (LinearLayout)view.findViewById(R.id.verticalListautentiSpesa);

        LinearLayout h = new LinearLayout(view.getContext());
        h.setOrientation(LinearLayout.VERTICAL);
        h.setTag("layout"+LStanza.get(pos).get(0).getNome());

        deleteSpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDlg=new AlertDialog.Builder(v.getRootView().getContext());
                alertDlg.setMessage("Sei sicuto di volere cancellare questa spesa?");

                alertDlg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            spstanza.get(0).DeleteSpesaStanza(spstanza.get(0));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        LStanza.remove(spstanza);
                        myadapter.notifyDataSetChanged();
                        if(mOnDataChangeListener != null){
                            mOnDataChangeListener.onDataChanged(LStanza.size());
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

        for(final SpesaStanza spesaStanza:spstanza){
            if(!spesaStanza.getDebitore().equals(username) && spesaStanza.getDovuto()!=0) {
                LinearLayout oriz = new LinearLayout(view.getContext());
                oriz.setOrientation(LinearLayout.HORIZONTAL);

                TextView u = new TextView(getContext());
                u.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                if(spesaStanza.getDovuto()<0)
                {
                    u.setText("Devo " + (spesaStanza.getDovuto()*(-1))+ " a "+spesaStanza.getDebitore());
                }
                else
                    u.setText(spesaStanza.getDebitore() + " deve " + spesaStanza.getDovuto());
                int c = Color.parseColor("#555555");
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                u.setLayoutParams(lp);
                u.setTextColor(c);
                oriz.addView(u);


                if(spesaStanza.getCreditore().equals(username)){
                    //mostro pulsanti paga
                    Button btnPaga = new Button(mContext);
                    btnPaga.setLayoutParams(lp);
                    int btnColor = Color.parseColor("#d6d7d7");
                    btnPaga.setText("Paga");
                    final Bundle bund=new Bundle();
                    btnPaga.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext,PagaSpesa.class);
                            bund.putCharSequence("type","nottotal");
                            bund.putSerializable("spesa",spesaStanza);
                            bund.putSerializable("lista",LStanza.get(pos));
                            intent.putExtras(bund);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    });

                    oriz.addView(btnPaga);
                }

                h.addView(oriz);
            }
            if(!spesaStanza.getCreditore().equals(username)&&spesaStanza.getDebitore().equals(username)){
                ((ViewManager)deleteSpesa.getParent()).removeView(deleteSpesa);
            }
        }
        l.addView(h);

        String[] d = spstanza.get(0).getData().split("T");
        String dataFormattata = d[0].split("-")[2]+"/"+d[0].split("-")[1]+"/"+d[0].split("-")[0];
        data.setText(dataFormattata);
        return view;

    }

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

}
