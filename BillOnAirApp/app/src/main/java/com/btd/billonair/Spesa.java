package com.btd.billonair;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ernrico on 08/06/2016.
 */
public class Spesa implements Serializable {
    private String Nome;
    private Double Costo;
    private String Data;
    private Integer Id;

    public Spesa(String NomeS,Double CostoS,String DataS,Integer IdS)
    {
        Nome=NomeS;
        Costo=CostoS;
        Data=DataS;
        Id=IdS;
    }

    protected Spesa(Parcel in) {
        Nome = in.readString();
        Data = in.readString();
    }



    public String getNome()
    {
        return Nome;
    }

    public Double getCosto()
    {
        return Costo;
    }

    public String getData()
    {
        return Data;
    }

    public Integer getId()
    {
        return Id;
    }


}
