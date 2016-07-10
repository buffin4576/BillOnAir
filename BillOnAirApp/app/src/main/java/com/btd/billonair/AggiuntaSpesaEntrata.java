package com.btd.billonair;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ernrico on 10/07/2016.
 */
public class AggiuntaSpesaEntrata extends Activity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.aggiuntasaesaentrata);


        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int width =DM.widthPixels;
        int height= DM.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));


    }
}
