package com.example.jacksonmajolo.app.models;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class User {

    private String name;
    private String mail;
    private String method_autentication;

    public User() {}

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

    public String getMethod_autentication() {
        return this.method_autentication;
    }

    public void setMethod_autentication(String method_autentication) { this.method_autentication = method_autentication; }

    @Override
    public String toString() {
        return this.name;
    }
}
