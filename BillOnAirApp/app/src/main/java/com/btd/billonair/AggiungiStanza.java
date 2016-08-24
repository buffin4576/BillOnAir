package com.btd.billonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class AggiungiStanza extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiuntastanza);

        final TextView txtnome=(TextView)findViewById(R.id.txtAddNomeStanza);
        Button aggiungi=(Button)findViewById(R.id.btnAddStanza);
        final SharedPreferences settings = getSharedPreferences("Shared",0);
        final Stanza NStanza=new Stanza();
        aggiungi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!txtnome.getText().toString().equals(""))
                {
                    try {
                        NStanza.CreaStanza(txtnome.getText().toString(),settings.getString("username","offline"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    finish();
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Nome conto è vuoto";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
            }
        });




    }
}
