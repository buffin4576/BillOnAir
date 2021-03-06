package com.btd.billonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.btd.billonair.com.btd.billonair.db.ContoDAO;
import com.btd.billonair.com.btd.billonair.db.ContoDAO_DB_impl;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO;
import com.btd.billonair.com.btd.billonair.db.SpesaContoDAO_DB_impl;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Buffin on 17/08/2016.
 */
public class Charts_fragment extends Fragment {

    String owner="offline";

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

        LineChart lchart = (LineChart) getView().findViewById(R.id.lchart);

        List<List<Entry>> lentries = new ArrayList<List<Entry>>();

        ContoDAO dao= new ContoDAO_DB_impl();
        SpesaContoDAO dao2= new SpesaContoDAO_DB_impl();
        ArrayList<Conto> LConti=null;
        ArrayList<SpesaConto>LSpese=null;
        ArrayList<ArrayList<SpesaConto>>LSpeseMese=new ArrayList<ArrayList<SpesaConto>>();
        ArrayList<Double> saldoMese= new ArrayList<Double>();
        ArrayList<LineDataSet>LlineDataset=new ArrayList<LineDataSet>();
        List<ILineDataSet> ldataSets = new ArrayList<ILineDataSet>();
        double s;

        String[] Data1;
        String[]Data2;
        String mese;
        float giorno;
        Date Now=new Date();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared",0);
        owner = sharedPreferences.getString("username","offline");

        try {
            dao.open();
            LConti= (ArrayList<Conto>) dao.getAllConti(owner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.close();

        try {
            dao2.open();
            LSpese= (ArrayList<SpesaConto>) dao2.getAllSpese(owner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao2.close();

        for (int i=0;i<LConti.size();i++)
        {
            saldoMese.add(LConti.get(i).getSaldo());
            lentries.add(new ArrayList<Entry>());
            LSpeseMese.add(new ArrayList<SpesaConto>());
        }

        for (int i=0;i<LSpese.size();i++)
        {
            Data1=(LSpese.get(i).getData()).split("-");
            if(Now.getMonth() +1<10)
            {
                mese="0"+(Now.getMonth()+1);
            }
            else
            {
                mese=""+(Now.getMonth()+1);
            }
            if(mese.equals(mese))
            {
                for (int y=0;y<LConti.size();y++)
                {
                    if(LConti.get(y).getNomeConto().equals(LSpese.get(i).getNomeConto()))
                    {
                        s=saldoMese.get(y);
                        saldoMese.set(y,(s-LSpese.get(i).getCosto()));
                        LSpeseMese.get(y).add(LSpese.get(i));
                    }
                }
            }
        }

        for (int i=0;i<saldoMese.size();i++)
        {
            Entry lentry=new Entry(0,Float.parseFloat(saldoMese.get(i).toString()));
            lentries.get(i).add(lentry);
            for (int y=0;y<LSpeseMese.get(i).size();y++)
            {
                s=saldoMese.get(i);
                saldoMese.set(i,s+LSpeseMese.get(i).get(y).getCosto());
                Data2=LSpeseMese.get(i).get(y).getData().split("-");
                lentry=new Entry(Float.parseFloat(Data2[2]),Float.parseFloat(saldoMese.get(i).toString()));
                lentries.get(i).add(lentry);
            }
            LlineDataset.add(new LineDataSet(lentries.get(i),LConti.get(i).getNomeConto()));
            LlineDataset.get(i).setAxisDependency(YAxis.AxisDependency.LEFT);
            int color = Color.parseColor(LConti.get(i).getColore());
            LlineDataset.get(i).setColor(color);
            LlineDataset.get(i).setCircleColor(color);
            ldataSets.add(LlineDataset.get(i));
        }

        LineData ldata = new LineData(ldataSets);
        lchart.setData(ldata);

        Legend l = lchart.getLegend();
        l.setEnabled(true);
        l.setTypeface(Typeface.DEFAULT);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
        l.setYOffset(20f);

        // set custom labels and colors
        String[] legendLabels = new String[LConti.size()];
        int[] legendColor = new int[LConti.size()];
        for(int i = 0; i < LConti.size(); i++)
        {
            legendColor[i] = Color.parseColor(LConti.get(i).getColore());
            legendLabels[i] = LConti.get(i).getNomeConto();
        }
        l.setCustom(legendColor, legendLabels);

        lchart.invalidate();


        //grafico barre
        BarChart chart = (BarChart)getView().findViewById(R.id.chart);

        List <BarEntry> bentriesIn=new ArrayList<BarEntry>();
        List <BarEntry> bentriesOut=new ArrayList<BarEntry>();
        ArrayList<Double>groupIn=new ArrayList<Double>();
        ArrayList<Double>groupOut=new ArrayList<Double>();
        double spese;
        double entrate;
        int j=0;
        for(j=0;j<LConti.size();j++)
        {

            spese=0;
            entrate=0;
            for (int y=0;y<LSpeseMese.get(j).size();y++)
            {
                if(LSpeseMese.get(j).get(y).getCosto()<0)
                {
                    spese-=LSpeseMese.get(j).get(y).getCosto();
                }
                else
                {
                    entrate+=LSpeseMese.get(j).get(y).getCosto();
                }
            }
            BarEntry bIn=new BarEntry(j, (float) entrate);
            BarEntry bOu=new BarEntry(j,(float) spese);
            bentriesIn.add(bIn);
            bentriesOut.add(bOu);
        }
        BarEntry bIn=new BarEntry(j+1, 0f);
        BarEntry bOu=new BarEntry(j+1,0f);
        bentriesIn.add(bIn);
        bentriesOut.add(bOu);

        String[] nomeConti=new String[LConti.size()];

        for (int i=0;i<nomeConti.length;i++)
        {
            nomeConti[i]=LConti.get(i).getNomeConto();
        }

        /*
        int[] groupIn = {10,20,48,32,0};
        int[] groupOut = {30,22,18,9,0};

        String[] nomeConti = new String[]{"Conto 1","Conto 2","Conto 3","Conto 4"};


        for(int i = 0; i < groupIn.length; i++) {
            BarEntry bIn = new BarEntry(i,groupIn[i]);
            BarEntry bOut = new BarEntry(i,groupOut[i]);
            entries1.add(bIn);
            entries2.add(bOut);
        }*/



        BarDataSet set1 = new BarDataSet(bentriesIn, "In");
        BarDataSet set2 = new BarDataSet(bentriesOut, "Out");

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

        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        chart.getLegend().setYOffset(30f);
        chart.invalidate();


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
