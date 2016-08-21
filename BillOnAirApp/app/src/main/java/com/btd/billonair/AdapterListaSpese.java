package com.btd.billonair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaSpese extends ArrayAdapter<SpesaConto>
{
    private ArrayList<SpesaConto> LSpese;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaSpese myadapter;


    public AdapterListaSpese(Context context, int resource, ArrayList<SpesaConto> objects)
    {
        super(context, resource, objects);
        LSpese=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
    }
    @Override
    public View getView(final int pos, View view, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);
        final SpesaConto spesa = LSpese.get(pos);
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
        final ImageView deletespesa=(ImageView)view.findViewById(R.id.deletespesa);
        Formattazione form=new Formattazione();

        NomeSpesa.setText(spesa.getNomeSpesa());
        CostoSpesa.setText(""+form.Soldi(spesa.getCosto()));
        DataSpesa.setText(form.Data(spesa.getData()));

        deletespesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alertDlg=new AlertDialog.Builder(mContext);
                alertDlg.setMessage("Sei sicuto di volere cancellare questa spesa?");

                alertDlg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpesaContoDAO dao = new SpesaContoDAO_DB_impl();
                        ContoDAO dao2= new ContoDAO_DB_impl();
                        try {
                            dao.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Boolean Ret=dao.deleteSpesa(spesa);
                        dao.close();
                        if(Ret)
                        {
                            try {
                                dao2.open();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Conto Con = null;
                            try {
                                Con = dao2.getContoByName(LSpese.get(pos).getNomeConto(),LSpese.get(pos).getOwner());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Con.setSaldo(Con.getSaldo()-LSpese.get(pos).getCosto());
                            dao2.updateConto(Con,Con.getNomeConto());
                            dao2.close();
                            LSpese.remove(pos);
                            myadapter.notifyDataSetChanged();
                            if(mOnDataChangeListener != null){
                                mOnDataChangeListener.onDataChanged(LSpese.size());
                            }
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

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


}
