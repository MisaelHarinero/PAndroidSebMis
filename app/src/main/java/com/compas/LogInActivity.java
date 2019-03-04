package com.compas;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.compas.controller.connectionFirebase.AuthenticationFirebase;
import com.compas.view.RegisterFragment;
import com.compas.view.SingInFragment;
import com.compas.view.VerificationFragment;

public class LogInActivity extends AppCompatActivity implements SingInFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, VerificationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SingInFragment fragment = new SingInFragment();
        fragment.setActivity(this);
        getFragmentManager().beginTransaction().add(R.id.mainContainer,fragment).commit();

    }
    public void intenToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void showVerificationFragment(AuthenticationFirebase auth){
    VerificationFragment verificationFragment =  new VerificationFragment();
    verificationFragment.setAuth(auth);
    getFragmentManager().beginTransaction().replace(R.id.mainContainer,verificationFragment).commit();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
