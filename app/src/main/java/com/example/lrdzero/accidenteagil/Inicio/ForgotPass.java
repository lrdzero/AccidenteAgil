package com.example.lrdzero.accidenteagil.Inicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lrdzero.accidenteagil.R;
import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;


public class ForgotPass extends AppCompatActivity {
    private Button btn;
    private EditText corr;
    private UtilesDialog util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        btn = (Button) findViewById(R.id.send);
        corr=(EditText) findViewById(R.id.recordarContra);

        util = new UtilesDialog(getApplicationContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(corr.getText().toString().matches("")){
                    util.UtilesDialog(ForgotPass.this,"No ha especificado ningún correo").show();
                }
                else{
                    //if comprobar si existe en BD y enviar; en caso contrario mostrar mensaje de error.
                    //util.UtilesDialog(ForgotPass.this,"El correo electronico no esta asociado a ninguna cuenta").show();
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("text/plain");
                    email.putExtra(Intent.EXTRA_EMAIL,new String[]{corr.getText().toString()});
                    email.putExtra(Intent.EXTRA_SUBJECT,"AutoGuismoAPP");
                    email.putExtra(Intent.EXTRA_TEXT,"He aquí su contraseña");
                    startActivity(email.createChooser(email,"Enviando correo"));
                }
            }
        });
    }
}
