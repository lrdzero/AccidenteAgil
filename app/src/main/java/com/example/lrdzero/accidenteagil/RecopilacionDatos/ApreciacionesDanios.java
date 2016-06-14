package com.example.lrdzero.accidenteagil.RecopilacionDatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lrdzero.accidenteagil.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

public class ApreciacionesDanios extends Activity {

    private ImageView carA,carB;
    private EditText danios,observaciones;
    private ImageView actualPictureA,actualPictureB,back,next;
    private ArrayList<Bitmap> imagesA,imagesB;
    private final static int CARA=1;
    private final static int CARB=0;
    private ArrayList<String> imagenes;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apreciaciones_danios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        path=getIntent().getExtras().getString("path");
        imagenes = new ArrayList<String>();
        actualPictureA=(ImageView)findViewById(R.id.actualPictureA);
        actualPictureB=(ImageView) findViewById(R.id.actualPictureB);
        back=(ImageView) findViewById(R.id.imageBack);
        next=(ImageView) findViewById(R.id.imageNext);


        carA=(ImageView) findViewById(R.id.picturesCrashA);
        carB=(ImageView) findViewById(R.id.picturesCrashB);
        danios=(EditText) findViewById(R.id.daniosApreciados);
        observaciones=(EditText) findViewById(R.id.observaciones);
        imagesA=new ArrayList<Bitmap>();
        imagesB=new ArrayList<Bitmap>();

        carA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CARA);
            }
        });

        carB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CARB);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fn;
                try {
                    File sampleDir = new File(Environment.getExternalStorageDirectory(), "/VehicleImageCrash");
                    // Created directory if not exist
                    if(!sampleDir.exists()){
                        sampleDir.mkdirs();
                    }
                    Date d=new Date();
                    for(int i=0;i<imagesA.size();i++) {
                        fn = new File(sampleDir + "/" + i+"imageA" + d.getTime() + ".png");
                        FileOutputStream out = new FileOutputStream(fn);
                        imagesA.get(i).compress(Bitmap.CompressFormat.PNG, 90, out);
                        imagenes.add(fn.getPath());
                    }
                    for(int i=0;i<imagesB.size();i++) {
                        fn = new File(sampleDir + "/" + i+"imageB" + d.getTime() + ".png");
                        FileOutputStream out = new FileOutputStream(fn);
                        imagesA.get(i).compress(Bitmap.CompressFormat.PNG, 90, out);
                        imagenes.add(fn.getPath());
                    }
                    if(!observaciones.getText().toString().matches("")){
                        FileWriter f = new FileWriter(path,true);
                        f.write("\n\nObservaciones:\n\t");
                        f.write(observaciones.getText().toString());
                        f.close();
                    }
                    if(!danios.getText().toString().matches("")){
                        FileWriter f = new FileWriter(path,true);
                        f.write("\n\nDaños:\n\t");
                        f.write(danios.getText().toString());
                        f.close();
                    }
                   // Toast.makeText(getApplicationContext(), "Screenshot saved at - " + sampleDir + "!!!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent toPosicionamiento= new Intent(ApreciacionesDanios.this,Posicionamiento.class);
                //EXTRAS IMAGES Y DEMAS.
                toPosicionamiento.putExtra("tamanioImages",imagenes.size());
                toPosicionamiento.putExtra("path",path);
                for(int i=0;i<imagenes.size();i++){
                    toPosicionamiento.putExtra("image"+i,imagenes.get(i));
                }
                startActivity(toPosicionamiento);
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CARA) {
            if(resultCode==RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imagesA.add(bp);
                actualPictureA.setImageBitmap(bp);
            }
        }
        else if(requestCode==CARB){
            if(resultCode==RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imagesB.add(bp);
                actualPictureB.setImageBitmap(bp);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
