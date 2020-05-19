package com.alexen.mypuig.api;

import java.io.Serializable;

public class Discussion {

    int id;
    public String name;
    public String message;
    public String userpictureurl;
    public String userfullname;
    public long created;
    public long timemodified;

    public Discussion(String name, String message, String userpictureurl, String userfullname, long created, long timemodified) {
        this.name = name;
        this.message = message;
        this.userpictureurl = userpictureurl;
        this.userfullname = userfullname;
        this.created = created;
        this.timemodified = timemodified;
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
