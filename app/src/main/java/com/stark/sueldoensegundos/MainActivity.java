package com.stark.sueldoensegundos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.stark.SQLite.Tables.SettingsLite;


public class MainActivity extends ActionBarActivity {

    LinearLayout btnSettings;
    LinearLayout btnSalary;
    LinearLayout btnExit;


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

        btnSalary = (LinearLayout)findViewById(R.id.main_btn_VerSueldo);
        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(new SettingsLite(getApplicationContext()).select().size()>0)
                        i = new Intent(getApplicationContext(),
                                SalaryActivity.class);
                else
                    i = new Intent(getApplicationContext(),
                            SettingsActivity.class);

                startActivity(i);
            }
        });
        btnExit= (LinearLayout)findViewById(R.id.main_btn_Salir);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
