package com.example.lrdzero.accidenteagil.Utiles;

/**
 * Created by lrdzero on 12/06/2016.
 */
public class Testigos {
    private String nombre;
    private String direccion;
    private String telefono;

    public Testigos(){}
    public Testigos(String n,String dir,String tlf){
        this.nombre=n;
        this.direccion=dir;
        this.telefono=tlf;
    }

    public String getNombre(){return nombre;}
    public String getDireccion(){return direccion;}
    public String getTelefono(){return telefono;}
    public void setNombre(String n){this.nombre=n;}
    public void setDireccion(String n){this.direccion=n;}
    public void setTelefono(String n){this.telefono=n;}
}
