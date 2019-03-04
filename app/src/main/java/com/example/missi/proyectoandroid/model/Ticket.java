package com.example.missi.proyectoandroid.model;

import java.util.ArrayList;

/**
 * Clase que contiene la informacion de  los tikets de los usuarios asi como un ArrayList de mensajes que se enviaran mientras este abierto
 * el tiket
 */
public class Ticket {
    private final static int STATE_OPEN = 0;
    private final static int STATE_RENOVADO = 1;
    private final static int STATE_CERRADO = 2;
    private String uid;
    private int importance;
    private String title;
    private String description;
    private String localization;
    private String image;
    private String uidResponsable;
    private int state;
    private ArrayList<Message>messages;

    public Ticket(String uid, int importance, String title, String description, String localization, String image, String uidResponsable, int state) {
        this.uid = uid;
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.localization = localization;
        this.image = image;
        this.uidResponsable = uidResponsable;
        this.state = state;
    }

    public Ticket() {
    }

    public Ticket(int importance, String title, String description, String localization, String image, String uidResponsable, int state) {
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.localization = localization;
        this.image = image;
        this.uidResponsable = uidResponsable;
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUidResponsable() {
        return uidResponsable;
    }

    public void setUidResponsable(String uidResponsable) {
        this.uidResponsable = uidResponsable;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}

