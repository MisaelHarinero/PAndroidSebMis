package com.compas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.compas.controller.connectionFirebase.AuthenticationFirebase;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Ticket;
import com.compas.model.User;
import com.compas.view.ViewDataTicket;
import com.compas.view.ViewDataUser;
import com.compas.view.ViewMyTickets;
import com.compas.view.ViewTicketsAtendidos;
import com.compas.view.ViewTiketsNotServed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ViewDataUser.OnFragmentInteractionListener,ViewMyTickets.OnFragmentInteractionListener, ViewTiketsNotServed.OnFragmentInteractionListener, ViewTicketsAtendidos.OnFragmentInteractionListener,ViewDataTicket.OnFragmentInteractionListener{
        private AuthenticationFirebase auth;
        private FireStoreController controller;
        private User user;
        private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = new AuthenticationFirebase();
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = new AuthenticationFirebase();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ticket ticket = new Ticket(user.getId()+ Timestamp.now().toDate().toString(),5,user.getId(),"Pc not Found","El pc ha explotado que mal rollo tengo miedo","Area 5","/user/area5.img",null,0);
                controller.sendTicket(ticket);
            }
        });
        controller = new FireStoreController();
        controller.getUser(this.auth.getUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               user =  task.getResult().toObject(User.class);
                TextView navHeader = findViewById(R.id.name);
                TextView mail = findViewById(R.id.mail);
                navHeader.setText(user.getName()+" "+user.getSurnames());
                mail.setText(user.getMail());
                ImageView image = findViewById(R.id.imageView);
                if (auth.getUser().getPhotoUrl() != null){
                    image.setImageURI(auth.getUser().getPhotoUrl());
                }





            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void startFragmentTicket(ViewDataTicket dataTicket){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,dataTicket).commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:{
                auth.logOut(this);
                Intent intent = new Intent(this,LogInActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.userData:{
                ViewDataUser fragment = new ViewDataUser();
                fragment.setUser(this.user);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            if (user!=null){
                ViewMyTickets fragment = new ViewMyTickets();
                fragment.setUser(this.user);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
            }
        } else if (id == R.id.nav_gallery) {
            if (user != null){
                ViewTiketsNotServed fragment = new ViewTiketsNotServed();
                fragment.setUser(this.user);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
            }

        } else if (id == R.id.nav_slideshow) {
                if (user != null){
                    ViewTicketsAtendidos fragment = new ViewTicketsAtendidos();
                    fragment.setUser(this.user);
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
                }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
