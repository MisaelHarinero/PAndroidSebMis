package com.compas.model;


import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private final static int MAX_KARMA = 100;
    private String UID;
    private String id;
    private String mail;
    private String name;
    private String surnames;
    private Timestamp date_born;
    private int karma;
    private ArrayList<String>friends;


    public User() {
    }

    public User(String UID, String id, String name, String surnames,String mail, Timestamp date_born, int karma) {
        this.UID = UID;
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.mail = mail;
        this.date_born = date_born;
        this.karma = karma;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public User(String id, String name, String surnames, String mail, Timestamp date_born, int karma) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.mail = mail;
        this.date_born = date_born;
        this.karma = karma;
        this.friends = new ArrayList<>();
    }
    public User(String id, String name, String surnames, String mail, Timestamp date_born) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.mail = mail;
        this.date_born = date_born;
        this.karma = MAX_KARMA;

    }
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public Timestamp getDate_born() {
        return date_born;
    }


    public void setDate_born(Date date_born) {
        this.date_born = new Timestamp(date_born);
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

