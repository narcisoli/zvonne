package com.example.narcis.zvonne.obiecte;

/**
 * Created by Narcis on 12/21/2016.
 */

public class mesaj {
    String user;
    String text;
    String poza;


    public mesaj() {

    }

    public mesaj(String user, String text, String poza) {
        this.user = user;
        this.text = text;
        this.poza = poza;

    }

    public String getPoza() {
        return poza;
    }

    public void setPoza(String poza) {
        this.poza = poza;
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
