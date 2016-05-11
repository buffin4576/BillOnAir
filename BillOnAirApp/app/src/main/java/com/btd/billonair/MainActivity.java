package com.btd.billonair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    ViewSwitcher viewSwitcher ;
    LinearLayout layoutLogin;
    LinearLayout layoutRegister;

    Button btnLogin ;
    Button btnRegister ;

    TextView linkRegister ;
    TextView linkLogin ;
    TextView linkOffline ;
    TextView linkOffline2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
        layoutLogin = (LinearLayout)findViewById(R.id.layoutLogin);
        layoutRegister = (LinearLayout)findViewById(R.id.layoutRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        linkRegister = (TextView)findViewById(R.id.linkRegister);
        linkLogin = (TextView)findViewById(R.id.linkLogin);
        linkOffline = (TextView)findViewById(R.id.linkOffline);
        linkOffline2 = (TextView)findViewById(R.id.linkOffline2);
    }


    public void OnClickLink(View v){
        if((TextView)v==linkLogin)
        {
            viewSwitcher.showPrevious();
        }
        else
        if((TextView)v==linkRegister)
        {
            viewSwitcher.showNext();
        }
        else
        {
            //go offline
        }
    }
}