package com.btd.billonair;

/**
 * Created by ernrico on 18/08/2016.
 */
public class Formattazione {
    public Formattazione()
    {

    }

    public String Soldi(double d)
    {
        return String.format("%.2f", Double.parseDouble(d+""));
    }

    public String Data(String s)
    {
        String[]st=s.split("-");
        return st[2]+"/"+st[1]+"/"+st[0];
    }
}
