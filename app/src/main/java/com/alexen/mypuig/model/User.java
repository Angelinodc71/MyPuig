package com.alexen.mypuig.model;

import java.io.Serializable;
import java.util.HashMap;

public class User {

    public String uid;
    public String name;
    public String token;
    public String imageAccount;
    public HashMap<String, Boolean> favs;

    public User(String uid,String token, String name) {
        this.uid = uid;
        this.token = token;
        this.name = name;
    }

    public User(String uid,String token, String name, String imageAccount) {
        this.uid = uid;
        this.token = token;
        this.name = name;
        this.imageAccount = imageAccount;
    }

    public User() {

    }
}
