package com.stark.sueldoensegundos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.stark.SQLite.Tables.SettingsLite;
import com.stark.SQLite.Utils.SQLiteMapper;

import java.util.ArrayList;


public class SettingsActivity extends ActionBarActivity {

    LinearLayout btnVolver;
    LinearLayout btnGuardar;
    EditText txtSalario;
    ArrayList<SettingsLite> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnVolver = (LinearLayout)findViewById(R.id.settings_btn_Volver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardar = (LinearLayout)findViewById(R.id.settings_btn_Guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(array.size()>0){
                    new SettingsLite(getApplicationContext()).Update(Float.parseFloat(String.valueOf(txtSalario.getText())));
                }
                else {
                    new SettingsLite(getApplicationContext()).Insert(Float.parseFloat(String.valueOf(txtSalario.getText())));
                }
                finish();
            }
        });

        txtSalario = (EditText)findViewById(R.id.settings_txtSueldo);
        array = SQLiteMapper.Map(new SettingsLite(getApplicationContext()).select(),SettingsLite.class);
        if(array.size()>0)
            txtSalario.setText(String.valueOf(array.get(0).Salary));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
