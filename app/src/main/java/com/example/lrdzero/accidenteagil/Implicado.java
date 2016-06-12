package com.example.lrdzero.accidenteagil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;

public class Implicado extends Activity implements View.OnClickListener{
    private Button continuar;
    private EditText nombre_imp;
    private EditText dni_imp;
    private UtilesDialog utils;
    private String userA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_iniciales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        continuar = (Button)findViewById(R.id.continuarButton);
        nombre_imp=(EditText)findViewById(R.id.nombreImplicado);
        dni_imp=(EditText) findViewById(R.id.dniImplicado);

        userA=getIntent().getExtras().getString("UsuarioA");
        utils = new UtilesDialog(getApplicationContext());
        continuar.setOnClickListener(this);

    }

    public void onClick(View id){
        switch (id.getId()){
            case R.id.continuarButton:
                if(matchingND(nombre_imp.getText().toString(),dni_imp.getText().toString())){
                    Intent toCar= new Intent(Implicado.this,SeleccionVehiculo.class);
                    toCar.putExtra("UsuarioA",userA);
                    toCar.putExtra("UsuarioB",nombre_imp.getText().toString());
                    startActivity(toCar);
                }
                else{
                    utils.UtilesDialog(Implicado.this,"No se ha encontrado al usuario");
                }
                break;
        }
    }

    public boolean matchingND(String nombre,String DNI){

        return true;
    }



}
