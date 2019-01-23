package com.example.missi.proyectoandroid.connectionFirebase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreController {
    private FirebaseFirestore db;

    public FireStoreController() {
        initFirestore();
    }
    private void initFirestore(){
        this.db = FirebaseFirestore.getInstance();
    }
    public void insertUser(String name, String surname, String date,String uid){
        Map<String,Object> userData = new HashMap<>();
        userData.put("name",name);
        userData.put("surname",surname);
        userData.put("date",date);
        this.db.collection("users").document(uid).set(userData);
    }
}
