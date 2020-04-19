package com.alexen.mypuig.model;

import java.io.Serializable;

public class Chat implements Serializable {

    private String autor;
    private String tema;
    private String imgaAutor;
    private Mensaje mensaje;

    public Chat(String autor, String tema, String imgaAutor) {

        this.autor = autor;
        this.tema = tema;
        this.imgaAutor = imgaAutor;
    }

    public String getAutor() {
        return autor;
    }

    public String getTema() {
        return tema;
    }

    public String getImgaAutor() {
        return imgaAutor;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }
}
