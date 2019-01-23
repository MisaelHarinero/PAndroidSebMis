package com.example.missi.proyectoandroid.connectionFirebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.missi.proyectoandroid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthenticationFirebase {
    private FirebaseUser user;
    private FirebaseAuth auth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient clientGoogle;
    public final static int RC_SIGN_IN = 9001;

    /**
     * Introduce R.string.default_web_client_id as Token.
     *
     *
     */
    public AuthenticationFirebase(Activity activity) {
        initFirebaseAuth();
        // Configure Google Sign In
        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        this.clientGoogle = GoogleSignIn.getClient(activity, gso);
    }

    private void initFirebaseAuth() {
        this.auth = FirebaseAuth.getInstance();
        chargeUser();

    }

    public void chargeUser() {
        this.user = this.auth.getCurrentUser();
    }

    public boolean isLogInAnyUser() {
        return this.user != null;
    }

    public void logOut(Context context) {
        Toast.makeText(context, "By by User", Toast.LENGTH_SHORT).show();
        this.auth.signOut();
        this.clientGoogle.signOut();
    }

    public boolean logInEmail(String email, String password) {
        boolean correcto = true;
        correcto = this.auth.signInWithEmailAndPassword(email, password).isSuccessful();
        return correcto;
    }

    /**
     * For obtain a result you need to override OnActivityResult and use a method call googleLogInActivityResult
     * @param activity
     */
    public void logInGoogle(Activity activity) {
        Intent logGoogleIntent = this.clientGoogle.getSignInIntent();
        activity.startActivityForResult(logGoogleIntent, RC_SIGN_IN);
    }

    public boolean googleLogInActivityResult(int requestCode, Intent data) {
        boolean correct = false;
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> tasks = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acount = tasks.getResult(ApiException.class);
                correct = firebaseAuthWithGoogle(acount);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return correct;
    }

    private boolean firebaseAuthWithGoogle(final GoogleSignInAccount acount) {
        boolean correcto;
        AuthCredential credential = GoogleAuthProvider.getCredential(acount.getIdToken(), null);
         this.auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   chargeUser();
                }else{
                    user = null;
                }
            }
        });
        correcto = user !=null;
        return correcto;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }
    public void logIn(final String email, final String password){
        this.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    auth.signInWithEmailAndPassword(email,password);
                    user = auth.getCurrentUser();
                    FireStoreController controller = new FireStoreController();
                    controller.insertUser("Misael","Harinero","1999-01-12",user.getUid());
                }
            }
        });

       // this.user.sendEmailVerification();

    }
    public boolean comprobarVerificacionEmail(){
        return this.user.isEmailVerified();
    }
}
