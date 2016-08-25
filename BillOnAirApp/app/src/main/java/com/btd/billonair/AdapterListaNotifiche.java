package com.btd.billonair;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

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
        return view;
        

    }
}
