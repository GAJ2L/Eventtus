package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class User extends Entity {

    private String name;
    private String mail;
    private String image;
    private String methodAutentication;

    public static String METHOD_GOOGLE = "google";
    public static String METHOD_FACEBOOK = "facebook";

    public User() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMethodAutentication() {
        return methodAutentication;
    }

    public void setMethodAutentication(String methodAutentication) {
        this.methodAutentication = methodAutentication;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
