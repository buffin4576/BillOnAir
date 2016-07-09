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

    public String GetNome()
    {
        return Nome;
    }

    public Double GetCosto()
    {
        return Costo;
    }

    public String GetData()
    {
        return Data;
    }

    public Integer GetId()
    {
        return Id;
    }
}
