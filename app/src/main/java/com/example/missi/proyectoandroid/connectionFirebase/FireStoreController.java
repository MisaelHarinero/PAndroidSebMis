package com.example.missi.proyectoandroid.connectionFirebase;

import android.support.annotation.NonNull;

import com.example.missi.proyectoandroid.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public void insertUser(String name, String surname, Timestamp date, String uid){
        Map<String,Object> userData = new HashMap<>();
        userData.put("name",name);
        userData.put("surname",surname);
        userData.put("date",date);
        this.db.collection(USER_COLLECTION).document(uid).set(userData);
    }
    public User getUser(String uid){
        DocumentReference documentReference  =  this.db.collection(USER_COLLECTION).document(uid);
            this.user = null;
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                if (task.isSuccessful()){
                    user = (User) snapshot.toObject(User.class);

                }
            }
        });
        return  user;

    }
    public void sendTicket(Ticket ticket){
        this.db.collection(TICKET_COLLECTION).document(ticket.getUid()).set(ticket);
    }
}
