package com.compas.model;

import com.google.firebase.Timestamp;

public class Message {
    private String uid;
    private String uidTransmitter;
    private Timestamp date;
    private String mmsg;

    public Message() {
    }

    public Message(String uidTransmitter, Timestamp date, String mmsg) {
        this.uidTransmitter = uidTransmitter;
        this.date = date;
        this.mmsg = mmsg;
    }

    public Message(String uid, String uidTransmitter, Timestamp date, String mmsg) {
        this.uid = uid;
        this.uidTransmitter = uidTransmitter;
        this.date = date;
        this.mmsg = mmsg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidTransmitter() {
        return uidTransmitter;
    }

    public void setUidTransmitter(String uidTransmitter) {
        this.uidTransmitter = uidTransmitter;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMmsg() {
        return mmsg;
    }

    public void setMmsg(String mmsg) {
        this.mmsg = mmsg;
    }
}
