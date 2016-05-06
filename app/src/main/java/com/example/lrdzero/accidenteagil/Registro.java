package com.example.lrdzero.accidenteagil;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registro extends Activity implements View.OnClickListener {
    private EditText nom,apellidos,correo,pass,pass2,dni;
    private Button btn;
    private ImageView btn2;
    private boolean check;
    private UtilesDialog util;



    private static final String REGISTER_URL = "http://aplicacionseguros.esy.es/registrarse.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nom=(EditText)findViewById(R.id.nombre);
        apellidos=(EditText)findViewById(R.id.apellidosuser);
        correo=(EditText)findViewById(R.id.correouser);
        pass=(EditText)findViewById(R.id.passuser);
        pass2=(EditText)findViewById(R.id.passuser2);
        dni=(EditText)findViewById(R.id.dniuser);

        btn=(Button)findViewById(R.id.registrarse);
        btn2=(ImageView)findViewById(R.id.imagenAsegura);

        check=false;
        util = new UtilesDialog(getApplicationContext());

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    public void onClick(View id){
        switch (id.getId()){
            case R.id.registrarse:


                if(nom.getText().toString().matches("")||apellidos.getText().toString().matches("")||correo.getText().toString().matches("")||pass.getText().toString().matches("")||pass2.getText().toString().matches("")||dni.getText().toString().matches("")){
                    util.UtilesDialog(Registro.this,"Hay campos sin rellenar").show();
                }
                else if(!pass.getText().toString().equals(pass2.getText().toString())){
                    util.UtilesDialog(Registro.this,"Las contraseñas no coinciden").show();
                }
                else if(!check){

                    util.UtilesDialog(Registro.this,"No ha seleccionado su aseguradora").show();
                }
                else {
                    //CREAR REGISTRO TRAS COMPROBACIÓN DE CORREO Y DNI
                    registra();
                    //finish();
                }

                break;
            case R.id.imagenAsegura:
                util.ListSecure(Registro.this).show();
                check=true;
                break;
        }
    }

    public void registra(){
        String name = nom.getText().toString();
        String password = pass.getText().toString();
        String apl = apellidos.getText().toString();
        String email = correo.getText().toString();
        String DNI = dni.getText().toString();

        peticionRegistro(name, password, apl, email, DNI);

    }
    public void peticionRegistro(String name, String password,String apellidos,String email,String dni){

        String urlSuffix = "?name="+name+"&password="+password+"&apellidos="+apellidos+"&email="+email+"&dni="+dni;
        class RegistrarEnBD extends AsyncTask<String,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading= ProgressDialog.show(Registro.this,"Espere...",null,true,true);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Registro.this, s, Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            protected String doInBackground(String... params){
                String s = params[0];
                BufferedReader bufferedReader=null;
                String result;
                try{
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));



                    result = bufferedReader.readLine();
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
                return result;
            }
        }
        RegistrarEnBD ru= new RegistrarEnBD();
        ru.execute(urlSuffix);

    }
}