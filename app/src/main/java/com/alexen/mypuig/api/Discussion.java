package com.alexen.mypuig.api;

import java.io.Serializable;

public class Discussion {

    public String id;
    public String name;
    public String message;
    public String userpictureurl;
    public String userfullname;
    public long created;
    public long timemodified;
    public boolean isFav;

    public Discussion(String name, String message, String userpictureurl, String userfullname, long created, long timemodified , boolean isFav) {
        this.name = name;
        this.message = message;
        this.userpictureurl = userpictureurl;
        this.userfullname = userfullname;
        this.created = created;
        this.timemodified = timemodified;
        this.isFav = isFav;
    }
    public Discussion(String name, String message, String userpictureurl, String userfullname, long created, long timemodified , boolean isFav, String id) {
        this.name = name;
        this.message = message;
        this.userpictureurl = userpictureurl;
        this.userfullname = userfullname;
        this.created = created;
        this.timemodified = timemodified;
        this.isFav = isFav;
        this.id = id;
    }

    public Discussion(String id) {
        this.id = id;
    }

    public Discussion(){

    }
    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", userpictureurl='" + userpictureurl + '\'' +
                ", userfullname='" + userfullname + '\'' +
                ", created=" + created +
                ", timemodified=" + timemodified +
                '}';
    }
}
