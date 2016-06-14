package com.example.lrdzero.accidenteagil.RecopilacionDatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lrdzero.accidenteagil.R;
import com.example.lrdzero.accidenteagil.Utiles.UtilesDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class Posicionamiento extends Activity{
    private GoogleMap map;
    private MapView mapView;

    private Button btn;

    private UtilesDialog utils;
    private Bitmap bp;
    private Button finalizar;
    private View capture;
    private String path;
    private ArrayList<String> lista;
    private int tamanio;
    private String captura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicionamiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        utils=new UtilesDialog(getApplicationContext());
        path=getIntent().getExtras().getString("path");
        tamanio=getIntent().getExtras().getInt("tamanioImages");
        lista = new ArrayList<String>();
        for(int i=0;i<tamanio;i++)
            lista.add(getIntent().getExtras().getString("image"+i));

        finalizar=(Button)findViewById(R.id.finalizar);
        btn=(Button) findViewById(R.id.getPosition);

        mapView = (MapView) findViewById(R.id.mi_mapa);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        capture=this.findViewById(R.id.lin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

                    private File fn;

                    @Override
                    public void onSnapshotReady(Bitmap snapshot) {
                        // TODO Auto-generated method stub
                        bp = snapshot;
                        try {
                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/MapScreenShots");
                            // Created directory if not exist
                            if(!sampleDir.exists()){
                                sampleDir.mkdirs();
                            }
                            Date d=new Date();
                            fn=new File(sampleDir+"/"+"Map"+d.getTime()+".png");
                            captura=fn.getPath();
                            FileOutputStream out = new FileOutputStream(fn);
                            bp.compress(Bitmap.CompressFormat.PNG, 90, out);
                            //Toast.makeText(getApplicationContext(), "Screenshot saved at - "+sampleDir+"!!!",Toast.LENGTH_SHORT).show();
                            utils.UtilesDialog(Posicionamiento.this,"Se ha capturado el mapa con exito.").show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                map.snapshot(callback);
            }


        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            ArrayList<Uri> uris = new ArrayList<Uri>();
            @Override
            public void onClick(View v) {

                if(captura!=null) {
                    // Toast.makeText(Posicionamiento.this,path,Toast.LENGTH_LONG).show();
                    File pngfile = new File(path);
                    Uri pngUri = Uri.fromFile(pngfile);
                    uris.add(pngUri);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"elendil.capt.gondor@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Parte accidente");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Parte generado ");
                    //emailIntent.putExtra(Intent.EXTRA_STREAM,pngUri);
                    for (int i = 0; i < lista.size(); i++) {
                        File img = new File(lista.get(i));
                        Uri data = Uri.fromFile(img);
                        uris.add(data);

                    }
                    File ult = new File(captura);
                    Uri ult2 = Uri.fromFile(ult);
                    uris.add(ult2);
                    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
                else{
                    utils.UtilesDialog(Posicionamiento.this,"No se ha tomado la posición del accidente. Por favor pulse el mapa para localizarse y despues tome su localización.");
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



}
