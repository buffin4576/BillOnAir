package com.btd.billonair;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class AggiungiSpesaStanza extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiuntaspesastanza);

        EditText causale=(EditText)findViewById(R.id.txtCausaleSpesaStanza);
        final EditText importo=(EditText)findViewById(R.id.txtImportSpesaStanza);
        Stanza stanza =(Stanza) getIntent().getSerializableExtra("stanza");

        final ArrayList<String> users = stanza.getUsers();
        LinearLayout layoutUtenti = (LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);

        for(int i = 0; i < users.size(); i++){
            LinearLayout h = new LinearLayout(this);
            h.setOrientation(LinearLayout.HORIZONTAL);
            h.setTag("layout"+users.get(i));

            TextView txtUtente = new TextView(this);
            txtUtente.setText(users.get(i));
            txtUtente.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            h.addView(txtUtente);

            EditText editUtente = new EditText(this);
            editUtente.setWidth(400);

            editUtente.setTag("edit"+users.get(i));

            h.addView(editUtente);

            layoutUtenti.addView(h);
        }

        assert importo != null;
        if (importo != null) {
            importo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    LinearLayout l = (LinearLayout)findViewById(R.id.verticalUtentiAggiungiStanza);
                    double d = Double.parseDouble(importo.getText()+"");
                    d=d/users.size();
                    for (int i = 0; i< users.size(); i++){
                        EditText editText = (EditText) l.findViewWithTag("edit"+users.get(i));
                        editText.setText(d+"");
                    }
                }
            });
        }

        /*SpesaStanza Sp=new SpesaStanza();
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
        Log.w("idspesa",i+"");*/

    }
}