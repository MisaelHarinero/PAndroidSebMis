package com.example.missi.proyectoandroid.model;

import java.sql.Timestamp;

public class User {

    private final static int MAX_KARMA = 100;
    private String UID;
    private String id;
    private String name;
    private String surnames;
    private Timestamp date_born;
    private int karma;

    public User() {
    }

    public User(String UID, String id, String name, String surnames, Timestamp date_born, int karma) {
        this.UID = UID;
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.date_born = date_born;
        this.karma = karma;
    }

    public User(String id, String name, String surnames, Timestamp date_born, int karma) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.date_born = date_born;
        this.karma = karma;
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

    public void setDate_born(Timestamp date_born) {
        this.date_born = date_born;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }
}

