package com.btd.billonair;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by ernrico on 08/06/2016.
 */
public class AdapterListaConti extends ArrayAdapter<Conto>
{
    private ArrayList<Conto> LConti;
    private Context mContext= null;



    public AdapterListaConti(Context context, int resource, ArrayList<Conto> objects)
    {
        super(context, resource, objects);

    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        return view;
    }
}
