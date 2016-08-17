package com.btd.billonair;

/**
 * Created by Buffin on 17/08/2016.
 */
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyValueFormatter implements ValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return String.format("%.2f", Double.parseDouble(value+""))+"€";
    }
}
