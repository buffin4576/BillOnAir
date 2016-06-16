package com.btd.billonair;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
/**
 * Created by ernrico on 16/06/2016.
 */
public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.nuovoconto);

        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int width =DM.widthPixels;
        int height= DM.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));


    }

}
