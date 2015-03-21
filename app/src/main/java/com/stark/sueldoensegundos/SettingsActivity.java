package com.stark.sueldoensegundos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stark.SQLite.Tables.SettingsLite;
import com.stark.SQLite.Utils.SQLiteMapper;

import java.util.ArrayList;


public class SettingsActivity extends ActionBarActivity {

    LinearLayout btnVolver;
    Button btnGuardar;
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

        btnGuardar = (Button)findViewById(R.id.settings_btn_Guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSalario.getText().length()==0) txtSalario.setText("0");
                if(Integer.parseInt(String.valueOf(txtSalario.getText()))>0){
                if(array.size()>0){
                    new SettingsLite(getApplicationContext()).Update(Integer.parseInt(String.valueOf(txtSalario.getText())));
                }
                else {
                    new SettingsLite(getApplicationContext()).Insert(Integer.parseInt(String.valueOf(txtSalario.getText())));
                }
                finish();
            }
                else {
                    Toast msg = Toast.makeText(getApplicationContext(), "El salario debe ser mayor a 0",Toast.LENGTH_LONG);
                    msg.show();
                }
            }
        });

        txtSalario = (EditText)findViewById(R.id.settings_txtSueldo);
        array = SQLiteMapper.Map(new SettingsLite(getApplicationContext()).select(),SettingsLite.class);
        if(array.size()>0)
            txtSalario.setText(String.valueOf(array.get(0).Salary));
    }
}
