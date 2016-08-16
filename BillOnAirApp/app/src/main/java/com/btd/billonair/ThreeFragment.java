package com.btd.billonair;

/**
 * Created by ernrico on 19/05/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.RatingCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.roomorama.caldroid.CaldroidFragment;

import android.widget.SimpleAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        caldroidFragment = new CaldroidSampleCustomFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL,false);
        caldroidFragment.setArguments(args);

        //setCustomResourceForDates();
        /*TextDrawable myText = new TextDrawable(12.32,9.80);
        Date d = new Date();
        caldroidFragment.setBackgroundDrawableForDate(myText,d);
        */

        HashMap<String, Object> extraData = (HashMap<String, Object>) caldroidFragment.getExtraData();
        ArrayList<String> singleDayValue = new ArrayList<>();
        singleDayValue.add("10.45");
        singleDayValue.add("12.57");
        singleDayValue.add("2016-08-16");
        ArrayList<String> singleDayValue2 = new ArrayList<>();
        singleDayValue2.add("10.45");
        singleDayValue2.add("12.57");
        singleDayValue2.add("2016-08-17");

        ArrayList<ArrayList> valori = new ArrayList<>();
        valori.add(singleDayValue);
        valori.add(singleDayValue2);
        extraData.put("valori",valori);

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
