package com.alexen.mypuig.model;

public class Notice {
    private String autor;
    private String tema;
    private String msgCorto;
    private String msg;
    private String fecha;
    private String fechaCorta;

    private boolean favNotice;

    public Notice(String autor, String tema, String msgCorto, String msg, String fecha, String fechaCorta, boolean favNotice) {
        this.autor = autor;
        this.tema = tema;
        this.msgCorto = msgCorto;
        this.msg = msg;
        this.fecha = fecha;
        this.fechaCorta = fechaCorta;
        this.favNotice = favNotice;
    }

    public String getAutor() {
        return autor;
    }

    public String getTema() {
        return tema;
    }

    public String getMsgCorto() {
        return msgCorto;
    }

    public String getMsg() {
        return msg;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFechaCorta() {
        return fechaCorta;
    }

    public boolean getFavNotice() {
        return favNotice;
    }
}
