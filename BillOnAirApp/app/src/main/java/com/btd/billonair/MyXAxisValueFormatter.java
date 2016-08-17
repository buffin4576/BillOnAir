package com.btd.billonair;

/**
 * Created by Buffin on 17/08/2016.
 */
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

public class MyXAxisValueFormatter implements  AxisValueFormatter {

    private  String[] mValues;

    public MyXAxisValueFormatter(String[] values){
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(value<0f || (int)value>=mValues.length)
            return "";
        return mValues[(int)value];
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
