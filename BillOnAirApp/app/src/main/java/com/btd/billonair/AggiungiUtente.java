package com.btd.billonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ernrico on 24/08/2016.
 */
public class AggiungiUtente extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiunginuovoutente);
        final Stanza stanza = (Stanza)getIntent().getSerializableExtra("stanza");

        Button btnMore = (Button)findViewById(R.id.btnInsertUserToList);
        Button btnAdd = (Button)findViewById(R.id.aggiungiutente);

        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listUtentiInvitati);
        final EditText txtUsername = (EditText)findViewById(R.id.txtAddNomeUtente);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(v.getContext());
                textView.setText(txtUsername.getText()+"");
                users.add(txtUsername.getText()+"");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                linearLayout.addView(textView);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    stanza.AggiungiUtente(stanza.getNome(),users,stanza.getIdStanza());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
