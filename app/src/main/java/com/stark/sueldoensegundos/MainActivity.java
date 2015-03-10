package com.stark.sueldoensegundos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    LinearLayout btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettings = (LinearLayout)findViewById(R.id.main_btn_Opciones);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        SettingsActivity.class);
                startActivity(i);
            }
        });
    }
}
