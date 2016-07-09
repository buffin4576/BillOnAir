package com.btd.billonair;

/**
 * Created by ernrico on 08/06/2016.
 */
public class Spesa {
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
