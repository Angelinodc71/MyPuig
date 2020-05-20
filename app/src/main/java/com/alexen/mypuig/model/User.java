package com.alexen.mypuig.model;

import java.io.Serializable;
import java.util.HashMap;

public class User {

    public String uid;

    public String token;

    public HashMap<String, Boolean> favs;

    public User(String uid,String token) {
        this.uid = uid;
        this.token = token;
    }

    public User() {

    }
}
