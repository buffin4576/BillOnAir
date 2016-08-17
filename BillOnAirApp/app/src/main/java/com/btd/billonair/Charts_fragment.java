package com.btd.billonair;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buffin on 17/08/2016.
 */
public class Charts_fragment extends Fragment {


    public Charts_fragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_grafico, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        BarChart chart = (BarChart)getView().findViewById(R.id.chart);

        List<BarEntry> entries1 = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();

        int[] groupIn = {10,20,48,32,0};
        int[] groupOut = {30,22,18,9,0};

        String[] nomeConti = new String[]{"Conto 1","Conto 2","Conto 3","Conto 4"};

        for(int i = 0; i < groupIn.length; i++) {
            BarEntry bIn = new BarEntry(i,groupIn[i]);
            BarEntry bOut = new BarEntry(i,groupOut[i]);
            entries1.add(bIn);
            entries2.add(bOut);
        }

        BarDataSet set1 = new BarDataSet(entries1, "In");
        BarDataSet set2 = new BarDataSet(entries2, "Out");

        set1.setColor(Color.GREEN);
        set2.setColor(Color.RED);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset

        ArrayList<BarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        BarData data = new BarData(set1,set2);

        XAxis xAxis = chart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(nomeConti));
        xAxis.setGranularity(1f);

        data.setBarWidth(barWidth);
        data.setValueTextSize(8f);

        data.setValueFormatter(new MyValueFormatter());

        chart.setDescription("Resoconto conti");
        chart.setDescriptionTextSize(12f);
        chart.setDrawGridBackground(false);
        chart.setData(data);
        chart.groupBars(0, groupSpace, barSpace);
        chart.invalidate();

        //chart 2//
        /*
        BarChart chart2 = (BarChart)getView().findViewById(R.id.chart2);

        chart2.setDrawGridBackground(false);
        chart2.setData(data);
        chart2.groupBars(0, groupSpace, barSpace);
        chart2.invalidate();*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
