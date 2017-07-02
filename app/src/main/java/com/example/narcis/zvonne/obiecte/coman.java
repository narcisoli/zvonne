package com.example.narcis.zvonne.obiecte;

/**
 * Created by Narcis on 5/15/2017.
 */

public class coman {
    String text;
    String nume;
    String nrdetelefon;
    long id;
    int status;

 public coman(){}
    public coman(String text, String nume, int status, long id,String numar) {
        this.text = text;
        this.nume = nume;
        this.status = status;
        this.id=id;
        this.nrdetelefon=numar;
    }

    public String getNrdetelefon() {
        return nrdetelefon;
    }

    public void setNrdetelefon(String nrdetelefon) {
        this.nrdetelefon = nrdetelefon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
