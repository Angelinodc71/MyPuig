package com.alexen.mypuig.model;

import java.io.Serializable;

public class User {

    public String uid;

    public String token;

    public User(String uid,String token) {
        this.uid = uid;
        this.token = token;
    }

    public User() {

    }
}
