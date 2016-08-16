package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.roomorama.caldroid.CaldroidFragment;

import android.widget.SimpleAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThreeFragment extends Fragment
{
    public ThreeFragment()
    {
    }

    private CaldroidFragment caldroidFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_three, container, false);

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        caldroidFragment.setArguments(args);
        //setCustomResourceForDates();
        TextDrawable myText = new TextDrawable("ciao");
        Date d = new Date(); //
        caldroidFragment.setBackgroundDrawableForDate(myText,d);

        // Attach to the activity
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.cal_container, caldroidFragment);
        t.commit();

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //codice mensile
    }


}
