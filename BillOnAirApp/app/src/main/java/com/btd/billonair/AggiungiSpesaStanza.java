package com.btd.billonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class AggiungiSpesaStanza extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiuntaspesastanza);

        EditText causale=(EditText)findViewById(R.id.txtCausaleSpesaStanza);
        EditText importo=(EditText)findViewById(R.id.txtImportSpesaStanza);
        Stanza ST=(Stanza) getIntent().getSerializableExtra("stanza");


        SpesaStanza Sp=new SpesaStanza();
        int i=-1;
        try {
            i=Sp.CreaSpesaStanza("ernrico21","ernrico21","prova causale",-25,ST.getIdStanza(),100.00);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.w("idspesa",i+"");

    }
}