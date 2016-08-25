package com.btd.billonair;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 25/08/2016.
 */
public class AdapterListaNotifiche extends ArrayAdapter<Notifica>
{
    private ArrayList<Notifica> LNotifiche;
    private Context mContext= null;
    private int mRowLayout;
    private AdapterListaNotifiche myadapter;
    private Activity mactivity=null;

    public AdapterListaNotifiche (Activity activity, Context context, int resource,ArrayList<Notifica> objects)
    {
        super(context,resource,objects);
        LNotifiche=objects;
        mContext=context;
        mRowLayout=resource;
        myadapter=this;
        mactivity=activity;

    }
    @Override
    public View getView(final int pos, View view, final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(mRowLayout, null);

        Button btnOk = (Button)view.findViewById(R.id.BtnNotificaok);
        TextView txtNotifica = (TextView)view.findViewById(R.id.txtNotifica);

        txtNotifica.setText(LNotifiche.get(pos).getTesto());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LNotifiche.get(pos).EliminaNotifica();
                    LNotifiche.remove(pos);
                    myadapter.notifyDataSetChanged();
                    if(mOnDataChangeListener != null){
                        mOnDataChangeListener.onDataChanged(LNotifiche.size());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
