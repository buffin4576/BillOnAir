package com.btd.billonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ernrico on 25/08/2016.
 */
public class NotificheActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifiche);

        TextView txtNotifiche=(TextView) findViewById(R.id.txtNomeStanza);
        ListView lv=(ListView)findViewById(R.id.listanotifiche);

        Stanza s=(Stanza) getIntent().getSerializableExtra("stanza");

    }
}

