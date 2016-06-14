package com.example.lrdzero.accidenteagil.Inicio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lrdzero.accidenteagil.RecopilacionDatos.Implicado;
import com.example.lrdzero.accidenteagil.R;
import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener {
    private EditText cor;
    private EditText pss;
    private TextView reg;
    private TextView forg;
    private Button btn;
    private UtilesDialog utils;
    private boolean error_login=false;
    private static final String LOGIN_URL = "http://aplicacionseguros.esy.es/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cor =(EditText) findViewById(R.id.correo);
        pss =(EditText) findViewById(R.id.pass);
        reg = (TextView)findViewById(R.id.textRegistro);
        forg =(TextView) findViewById(R.id.textForgot);
        btn=(Button) findViewById(R.id.btnInicio);

        utils = new UtilesDialog(getApplicationContext());

        reg.setOnClickListener(this);
        forg.setOnClickListener(this);
        btn.setOnClickListener(this);



    }

    public void onClick(View id){

        switch (id.getId()){
            case R.id.btnInicio:
                String tCorreo = cor.getText().toString();
                String tPss = pss.getText().toString();

                if(tCorreo.matches("")||tPss.matches("")) {
                    //LANZAR MENSAJE ERROR
                    utils.UtilesDialog(MainActivity.this,"Faltan campos por rellenar").show();
                }
                else {
                    //COMPROBAR EXISTENCIA EN BD
                    //IDENTIFICAR ERROR Y MOSTRARLO

                    long timeStart,timeOut;
                    timeStart= System.currentTimeMillis();
                    peticionLogin( tCorreo, tPss);
                    timeOut=System.currentTimeMillis();
                    double time = Double.valueOf((timeStart-timeOut)/1000);
                    //Toast.makeText(getApplicationContext(),"Timepo de respuesta = "+Double.toString(time), Toast.LENGTH_LONG).show();
                    if(error_login){
                        utils.UtilesDialog(MainActivity.this,"Correo inexistente o contraseña no valida").show();
                        error_login=false;
                    }

                }
                break;
            case R.id.textRegistro:
                Intent toRegistry = new Intent(getApplicationContext(),Registro.class);
                startActivity(toRegistry);
                break;

            case R.id.textForgot:
                Intent toForgot = new Intent(getApplicationContext(),ForgotPass.class);
                startActivity(toForgot);
                break;

        }



    }

    public void peticionLogin(final String tCorreo, String password){
        final String[] value = {""};
        String urlSuffix = "?correo=" + tCorreo + "&password=" + password;
        class RegistrarEnBD extends AsyncTask<String,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading= ProgressDialog.show(MainActivity.this,"Espere...",null,true,true);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                if(s.equals("1")){
                    Intent toInit = new Intent(MainActivity.this,Implicado.class);
                    toInit.putExtra("UsuarioA",tCorreo);
                    startActivity(toInit);
                }
                else if(s.equals("0")){
                    error_login=true;
                    //utils.UtilesDialog(MainActivity.this,"Correo inexistente o contraseña no valida").show();
                }

            }
            @Override
            protected String doInBackground(String... params){
                String s = params[0];
                BufferedReader bufferedReader=null;
                String result;
                try{
                    URL url = new URL(LOGIN_URL+s);
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