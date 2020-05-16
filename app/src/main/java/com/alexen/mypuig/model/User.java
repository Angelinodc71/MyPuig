package com.alexen.mypuig.model;

import java.io.Serializable;

public class User implements Serializable {

    public String uid;
    public String nombre;
    public String apellidos;
    public String token;

    public User(String nombre, String apellidos, String token) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.token = token;
    }

    public User(String uid,String token) {
        this.uid = uid;
        this.token = token;
    }

    public User() {

    }
}
