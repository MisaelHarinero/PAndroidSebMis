package com.example.missi.proyectoandroid;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Toast;

import com.example.missi.proyectoandroid.connectionFirebase.AuthenticationFirebase;

import com.example.missi.proyectoandroid.view.RegisterFragment;
import com.example.missi.proyectoandroid.view.SingInFragment;
import com.example.missi.proyectoandroid.view.VerificationFragment;


/**
 * A login screen that offers login via email/password.
 */
public class LogIn extends AppCompatActivity implements SingInFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, VerificationFragment.OnFragmentInteractionListener {


    // UI references.

    private AuthenticationFirebase authenticationFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.authenticationFirebase = new AuthenticationFirebase(this);
        if (this.authenticationFirebase.getUser() != null){
            Toast.makeText(this, this.authenticationFirebase.getUser().getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            SingInFragment fragment = new SingInFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.mainContainer,fragment).commit();

        }
        //this.authenticationFirebase.logOut(this);


    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean suscesfull = this.authenticationFirebase.googleLogInActivityResult(requestCode,data);
        if (!suscesfull){
            Toast.makeText(this, "No se ha podido logear Con google", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this,this.authenticationFirebase.getUser().getEmail() , Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

