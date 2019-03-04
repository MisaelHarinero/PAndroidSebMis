package com.example.missi.proyectoandroid.controller;

public class CheckRulesFormulary {
    private final static int MINIMAL_LENGTH = 9;

    private static boolean checkMail(String mail){
        return  mail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
    private static boolean checkPassword(String password){
        boolean correcta = true;
        if (password.length()<MINIMAL_LENGTH){
            correcta = false;
        }
        if (!password.matches("[\\d]++")){
            correcta = false;
        }
        if (!password.matches("[a-z]++")){
            correcta = false;
        }
        if (!password.matches("[A-Z]++")){
            correcta = false;
        }
        return  correcta;
    }
}
