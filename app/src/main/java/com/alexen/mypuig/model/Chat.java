package com.alexen.mypuig.model;

import java.io.Serializable;

public class Chat implements Serializable {

    private int id;

    private String mensajePropio;
    private String mensajeAjeno;

    public Chat(String mensajePropio, String mensajeAjeno) {
        this.mensajePropio = mensajePropio;
        this.mensajeAjeno = mensajeAjeno;
    }

    public String getMensajePropio() {
        return mensajePropio;
    }

    public void setMensajePropio(String mensajePropio) {
        this.mensajePropio = mensajePropio;
    }

    public String getMensajeAjeno() {
        return mensajeAjeno;
    }

    public void setMensajeAjeno(String mensajeAjeno) {
        this.mensajeAjeno = mensajeAjeno;
    }
}
