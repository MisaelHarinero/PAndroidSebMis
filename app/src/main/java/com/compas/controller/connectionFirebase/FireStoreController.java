package com.compas.controller.connectionFirebase;

import android.support.annotation.NonNull;

import com.compas.model.Ticket;
import com.compas.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FireStoreController {
    private FirebaseFirestore db;
    private final static String  USER_COLLECTION = "users";
    private final static String  TICKET_COLLECTION = "tickets";
    private User user;

    public FireStoreController() {
        initFirestore();
    }
    private void initFirestore(){
        this.db = FirebaseFirestore.getInstance();
    }
    public void insertUser(User user,String uid){
        this.db.collection(USER_COLLECTION).document(uid).set(user);
    }
    public DocumentReference getUser(String uid){
        DocumentReference documentReference  =  this.db.collection(USER_COLLECTION).document(uid);
        return documentReference;

    }
    public void sendTicket(Ticket ticket){
        this.db.collection(TICKET_COLLECTION).document(ticket.getUid()).set(ticket);
    }
    public Query  getMyTickets(String tag){
        return this.db.collection(TICKET_COLLECTION).whereEqualTo("tagTransmitter",tag);
    }
    public Query getTikectsNotServed(){
        return this.db.collection(TICKET_COLLECTION).whereEqualTo("idResponsable",null);
    }
    public Query getTikectsServeByME(String tag){
        return this.db.collection(TICKET_COLLECTION).whereEqualTo("idResponsable",tag);
    }
    public  void setTicketAtended(Ticket ticketModified){
        this.db.collection(TICKET_COLLECTION).document(ticketModified.getUid()).set(ticketModified);
    }
    public void deleteTicket(String uid){
        this.db.collection(TICKET_COLLECTION).document(uid).delete();

    }
    public DocumentReference getTicket(String uid){
        return  this.db.collection(TICKET_COLLECTION).document(uid);
    }






}
