package com.example.lrdzero.accidenteagil.Utiles;

/**
 * Created by lrdzero on 12/06/2016.
 */
public class Conductor {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String permiso;
    private String categoria;
    private String expedido;
    private String valido;

    public Conductor(){}
    public Conductor(String n,String ap,String dir,String per,String cat,String exp,String val){
        this.nombre=n;
        this.apellidos=ap;
        this.direccion=dir;
        this.permiso=per;
        this.categoria=cat;
        this.expedido=exp;
        this.valido=val;
    }

    public String getNombre(){return nombre;}
    public String getApellidos(){return apellidos;}
    public String getDireccion(){return direccion;}
    public String getPermiso(){return permiso;}
    public String getExpedido(){return expedido;}
    public String getCategoria(){return categoria;}
    public String getValido(){return valido;}
}
