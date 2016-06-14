package com.example.lrdzero.accidenteagil.RecopilacionDatos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrdzero.accidenteagil.R;
import com.example.lrdzero.accidenteagil.Utiles.Conductor;
import com.example.lrdzero.accidenteagil.Utiles.Testigos;
import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

public class DatosIniciales extends AppCompatActivity {
    private ArrayList<Testigos> listaTestigos;

    private ListView testigosView;
    private RadioButton radioVSI,radioVNO,radioDSI,radioDNO;
    private ImageView testigoADD,conductorADD,conductorBDD;
    private  Conductor conductorA,conductorB;
    private Boolean otroConductorA,otroConductorB;
    private ImageView next,back;
    private UtilesDialog utils;
    private String userA,userB,matriculaA,matriculaB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_iniciales2);

        listaTestigos=new ArrayList<Testigos>();

        userA=getIntent().getExtras().getString("UserA");
        userB=getIntent().getExtras().getString("UserB");
        matriculaA=getIntent().getExtras().getString("matriculaA");
        matriculaB=getIntent().getExtras().getString("matriculaB");



        conductorA=new Conductor();
        conductorB=new Conductor();
        otroConductorB=false;
        otroConductorA=false;

        utils = new UtilesDialog(getApplicationContext());

        testigosView=(ListView)findViewById(R.id.listaTestigos);
        testigoADD=(ImageView)findViewById(R.id.addTestigo);

        radioVSI=(RadioButton)findViewById(R.id.victimasSi);
        radioVNO=(RadioButton)findViewById(R.id.victimasNo);
        radioDSI=(RadioButton)findViewById(R.id.daniosSi);
        radioDNO=(RadioButton)findViewById(R.id.daniosNo);

        conductorADD=(ImageView)findViewById(R.id.addConductor);
        conductorBDD=(ImageView)findViewById(R.id.addconductorB);

        next=(ImageView)findViewById(R.id.nextItem);
        back=(ImageView)findViewById(R.id.backItem);

       final UsersAdapter adapter = new UsersAdapter(this, listaTestigos);
        testigosView.setAdapter(adapter);

        conductorADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDConductor(DatosIniciales.this, "A").show();
            }
        });
        conductorBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDConductor(DatosIniciales.this,"B").show();
            }
        });
        testigoADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ADDTestigo(DatosIniciales.this).show();
                //Toast.makeText(DatosIniciales.this,testigo.getNombre(),Toast.LENGTH_LONG).show();

                adapter.notifyDataSetChanged();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean vict=null;
                Boolean dan=null;
                if(radioVSI.isChecked()){
                    vict=true;
                }
                else if(radioVNO.isChecked()){
                    vict=false;
                }

                if(radioDSI.isChecked()){
                    dan=true;
                }
                else if(radioDNO.isChecked()){
                    dan=false;
                }
                if(vict!=null&&dan!=null) {

                    File fn;
                    String contenido="";
                    if(listaTestigos.isEmpty()) {
                        contenido += "Usuario A:" + userA + "\nMatricula:" + matriculaA + "\nUsuarioB" + userB + "\nMatricula" + matriculaB;
                    }
                    else{
                        contenido += "Usuario A:" + userA + "\nMatricula:" + matriculaA + "\nUsuarioB" + userB + "\nMatricula" + matriculaB+"\nTestigos: ";

                        for(int i=0;i<listaTestigos.size();i++){
                            contenido+="\n\tTestigo"+(i+1)+": ";
                            contenido+="\n\tNombre: "+listaTestigos.get(i).getNombre();
                            contenido+="\n\tDireccion: "+listaTestigos.get(i).getDireccion();
                            contenido+="\n\tTlfo: "+listaTestigos.get(i).getTelefono();
                        }
                    }
                    if(otroConductorA){
                        contenido+="\nConductor del Vehiculo A:\n\tNombre"+conductorA.getNombre()+"\n\tApellidos"+conductorA.getApellidos()+"\n\tCategoría: "+conductorA.getCategoria()+"\n\tDireccion: "+conductorA.getDireccion()+"\n\tPermiso: "+conductorA.getPermiso()+"\n\tExpedido: "+conductorA.getExpedido()+"\n\tValido: "+conductorA.getValido();
                    }
                    if(otroConductorB){
                        contenido+="\nConductor del Vehiculo B:\n\tNombre"+conductorB.getNombre()+"\n\tApellidos"+conductorB.getApellidos()+"\n\tCategoría: "+conductorB.getCategoria()+"\n\tDireccion: "+conductorB.getDireccion()+"\n\tPermiso: "+conductorB.getPermiso()+"\n\tExpedido: "+conductorB.getExpedido()+"\n\tValido: "+conductorB.getValido();
                    }
                    if(vict){
                        contenido+="\nVictimas: Si";
                    }
                    if(dan){
                        contenido+="\nDaños: Si";
                    }


                    String documento ="GeneradoParte.pdf";
                    try {
                        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/ArchivosPartes");
                        // Created directory if not exist
                        if (!sampleDir.exists()) {
                            sampleDir.mkdirs();
                        }
                        Date d = new Date();
                        File file = new File(sampleDir + "/" + d.getTime() + "parte");

                        FileWriter file2=new FileWriter(file.getPath(),false);
                        file2.write(contenido);
                        file2.close();

                        Toast.makeText(DatosIniciales.this, "Los datos fueron grabados correctamente en "+file.getPath(), Toast.LENGTH_SHORT).show();
                        Intent toCircunstancias = new Intent(DatosIniciales.this, Circunstancias.class);
                        //PUTEXTRAS
                        toCircunstancias.putExtra("archivo", file.getPath());
                        startActivity(toCircunstancias);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                else{
                    utils.UtilesDialog(DatosIniciales.this,"No ha seleccionado las casillas victimas o daños.").show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public AlertDialog.Builder ADDTestigo(final Context context){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText nombre=new EditText(context);
        final EditText direccion=new EditText(context);;
        final EditText telefono=new EditText(context);;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        nombre.setHint("Nombre");
        layout.addView(nombre);
        direccion.setHint("Direccion");
        layout.addView(direccion);
        telefono.setInputType(InputType.TYPE_CLASS_PHONE);
        telefono.setHint("Teléfono");
        layout.addView(telefono);

        builder.setView(layout);
        builder.setTitle("Testigo");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nombre.getText().toString().matches("") || direccion.getText().toString().matches("") || telefono.getText().toString().matches("")) {
                    Toast.makeText(context, "Hay campos nulos", Toast.LENGTH_LONG).show();
                } else {
                    Testigos testigo = new Testigos();
                    testigo.setNombre(nombre.getText().toString());
                    testigo.setDireccion(direccion.getText().toString());
                    testigo.setTelefono(telefono.getText().toString());
                    listaTestigos.add(testigo);
                    Toast.makeText(context, Integer.toString(listaTestigos.size()), Toast.LENGTH_LONG).show();

                }

            }
        });

        return builder;
    }
    public AlertDialog.Builder ADDConductor(final Context context, final String value){


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText nombre=new EditText(context);
        final EditText apellido=new EditText(context);
        final EditText direccion=new EditText(context);
        final EditText permiso=new EditText(context);
        final EditText categoria=new EditText(context);
        final EditText expedido=new EditText(context);
        final EditText valido=new EditText(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        nombre.setHint("Nombre");
        apellido.setHint("Apellido");
        direccion.setHint("Direccion");
        permiso.setHint("Permiso");
        categoria.setHint("Categoria");
        expedido.setHint("Expedido/el");
        valido.setHint("Valido hasta");

        layout.addView(nombre);
        layout.addView(apellido);
        layout.addView(direccion);
        layout.addView(permiso);
        layout.addView(categoria);
        layout.addView(expedido);
        layout.addView(valido);

        builder.setView(layout);
        builder.setTitle("Conductor");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nombre.getText().toString().matches("") || direccion.getText().toString().matches("") || apellido.getText().toString().matches("")||permiso.getText().toString().matches("")||categoria.getText().toString().matches("")||expedido.getText().toString().matches("")||valido.getText().toString().matches("")) {
                    Toast.makeText(context, "Hay campos nulos", Toast.LENGTH_LONG).show();
                } else {
                    if(value.equals("A")) {
                        conductorA = new Conductor(nombre.getText().toString(), apellido.getText().toString(), direccion.getText().toString(), permiso.getText().toString(), categoria.getText().toString(), expedido.getText().toString(), valido.getText().toString());
                        otroConductorA = true;
                    }
                    else{
                       conductorB=new Conductor(nombre.getText().toString(),apellido.getText().toString(),direccion.getText().toString(),permiso.getText().toString(),categoria.getText().toString(),expedido.getText().toString(),valido.getText().toString());
                       otroConductorB=true;
                    }
                }

            }
        });

        return builder;
    }

    public class UsersAdapter extends ArrayAdapter<Testigos> {
        public UsersAdapter(Context context, ArrayList<Testigos> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Testigos user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
            }
            // Lookup view for data population
            TextView nombre = (TextView) convertView.findViewById(R.id.nombreTest);
            TextView direccion = (TextView) convertView.findViewById(R.id.direccionTest);
            TextView telefono =(TextView) convertView.findViewById(R.id.telefonoTest);
            // Populate the data into the template view using the data object
            nombre.setText(user.getNombre());
            direccion.setText(user.getDireccion());
            telefono.setText(user.getTelefono());
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
