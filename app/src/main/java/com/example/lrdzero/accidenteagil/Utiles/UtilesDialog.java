package com.example.lrdzero.accidenteagil.Utiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lrdzero.accidenteagil.R;

import junit.framework.Test;

/**
 * Created by lrdzero on 29/03/2016.
 */
public class UtilesDialog extends AlertDialog {
    private AlertDialog.Builder builder;
    public UtilesDialog(Context context) {
        super(context);
    }

    public AlertDialog.Builder UtilesDialog(Context context,String msg){
        builder= new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder;
    }

    public AlertDialog.Builder ListSecure(Context context){
        final CharSequence[] items = {"Seguritas", "Alliance", "Otra"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection

            }
        });

        return builder;

    }



}

