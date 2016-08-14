package com.btd.billonair;

import java.util.Date;

/**
 * Created by Buffin on 10/08/2016.
 */
public final class Query {
    private static String lastUpdate;

    private Query(){

        //settare tutti i parametri (ip server db, info user....)
    }

    public static void SetLastUpdate(String date){
        lastUpdate = date;
    }

    public static void AddQuery(String query){

    }

    public static String GetAndExecAllQueries(){
        //scaricare con data maggiore di ultimo update, eseguire e aggiornare ultimo update
        return lastUpdate;
    }
}
