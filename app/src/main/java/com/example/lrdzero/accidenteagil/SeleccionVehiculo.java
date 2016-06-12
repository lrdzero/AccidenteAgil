package com.example.lrdzero.accidenteagil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;

public class SeleccionVehiculo extends Activity {

    private Spinner matriculasA;
    private Spinner matriculasB;
    private ImageView siguiente;
    private ImageView backData;
    private String userA,userB;
    private String matriculaA,matriculaB;
    private UtilesDialog utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_vehiculo);

        siguiente = (ImageView) findViewById(R.id.siguienteDat);
        backData =(ImageView)findViewById(R.id.backData);
        matriculasA = (Spinner) findViewById(R.id.matriculasA);
        matriculasB = (Spinner) findViewById(R.id.matriculasB);

        userA = getIntent().getExtras().getString("UsuarioA");
        userB = getIntent().getExtras().getString("UsuarioB");

        //TODO: Cargar matrículas por usuario desde BD.
        final String[] matriculas = new String[]{"Hola", "Puta"};


        utils = new UtilesDialog(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, matriculas);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, matriculas);
        matriculasA.setAdapter(adapter);

        matriculasB.setAdapter(adapter1);

        matriculasA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                matriculaA = parent.getItemAtPosition(pos).toString();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        matriculasB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                matriculaB=parent.getItemAtPosition(pos).toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matriculaA.matches("") || matriculaB.matches("")) {
                    utils.UtilesDialog(SeleccionVehiculo.this, "Faltan campos de matricula por rellenar");
                } else {
                    Intent toSeleccion = new Intent(SeleccionVehiculo.this, Circunstancias.class);
                    toSeleccion.putExtra("UserA", userA);
                    toSeleccion.putExtra("UserB", userB);
                    toSeleccion.putExtra("matriculaA", matriculaA);
                    toSeleccion.putExtra("matriculaB", matriculaB);
                    startActivity(toSeleccion);
                }
            }
        });

        backData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }




    }
