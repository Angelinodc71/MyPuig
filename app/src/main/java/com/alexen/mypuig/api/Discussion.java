package com.alexen.mypuig.api;

public class Discussion {
    int id;
    public String name;
    public String message;
    public String userpictureurl;
    public String userfullname;
    public long created;
    public long timemodified;

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
