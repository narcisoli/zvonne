package com.example.narcis.zvonne.obiecte;

/**
 * Created by Narcis on 12/21/2016.
 */

public class mesaj {
    String user;
    String text;

    public mesaj() {
    }

    public mesaj(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
