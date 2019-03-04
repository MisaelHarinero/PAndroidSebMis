package com.compas.controller.connectionFirebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.compas.LogInActivity;
import com.compas.model.User;
import com.compas.view.SingInFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
     * Constructor de la clase que obtiene la instancia con FirebaseAuth, e instancia el googleClient
     *
     * @param activity
     */
    public AuthenticationFirebase() {
        initFirebaseAuth();
        // Configure Google Sign In
      /** this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(activity.getString(R.string.common_google_play_services_unsupported_text))
                .requestEmail()
                .build();

        this.clientGoogle = GoogleSignIn.getClient(activity, gso);*/


    }

    /**
     * Metodo en el que instanciamos con Firebase y llamamos al metodo chargeUser para obtener un FirebaseUser en el caso de que
     * este iniciada la sesion de uno
     */
    private void initFirebaseAuth() {
        this.auth = FirebaseAuth.getInstance();
        chargeUser();

    }

    /**
     * Obtener un FirebaseUser en el caso de que se
     * este iniciada la sesion de uno.
     */
    public void chargeUser() {
        this.user = this.auth.getCurrentUser();
    }

    /**
     * Nos retorna true en caso de que este logeado un user y fase en el caso de que no
     *
     * @return : boolean
     */
    public boolean isLogInAnyUser() {
        return this.user != null;
    }

    /**
     * LogOut nos cierra sesion en Firebase
     *
     * @param context
     */
    public void logOut(Context context) {
        Toast.makeText(context, "By by User", Toast.LENGTH_SHORT).show();
        this.auth.signOut();
        //this.clientGoogle.signOut();
        chargeUser();
    }

    /**
     * Metodo en el que nos logeamos con un mail y contraseña,
     * y nos retorna true en caso de que nos logeemos correctamente y false en el caso en que no.
     *
     * @param email    : String
     * @param password : String
     * @return : boolean
     */
    public boolean singInEmail(String email, String password, final SingInFragment singInFragment) {
         boolean correcto = true;
         this.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    chargeUser();
                    if (comprobarVerificacionEmail()){
                                singInFragment.chargeVerificationMail();
                    }else{
                            singInFragment.chargeMainActivity();
                    }
                }
            }
        });

        return correcto;
    }

    /**
     * For obtain a result you need to override OnActivityResult and use a method call googleLogInActivityResult
     *
     * @param activity
     */
    public void singInGoogle(Activity activity) {
        Intent logGoogleIntent = this.clientGoogle.getSignInIntent();
        activity.startActivityForResult(logGoogleIntent, RC_SIGN_IN);
    }

    /**
     * En este metodo comprobamos que haya sido el intent de login de google con el requestCode, en el caso de que si
     * obtenemos los datos obtenidos del intent y nos intentamos logear con ellos, en el caso de que no sea posible,
     * retornamos false y en el caso de que nos hayamos podido loggear retornamos true
     * @param requestCode
     * @param data
     * @return
     */
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

    /**
     * Mediante una cuenta de google nos logeamos en firebase
     * @param acount : GoogleSignInAccount
     * @return
     */
    private boolean firebaseAuthWithGoogle(final GoogleSignInAccount acount) {
        boolean correcto;
        AuthCredential credential = GoogleAuthProvider.getCredential(acount.getIdToken(), null);
        this.auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    chargeUser();
                } else {
                    user = null;
                }
            }
        });
        correcto = user != null;
        return correcto;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }


    /**
     * @param newUser
     * @param password
     * @param activity
     */
    public void logIn(final User newUser, final String password, final LogInActivity activity, AuthenticationFirebase authenticationFirebase) {
        this.auth.createUserWithEmailAndPassword(newUser.getMail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.signInWithEmailAndPassword(newUser.getMail(), password);

                    user = auth.getCurrentUser();
                    user.sendEmailVerification();
                    newUser.setUID(user.getUid());
                    FireStoreController controller = new FireStoreController();
                    controller.insertUser(newUser, user.getUid());
                    activity.showVerificationFragment(authenticationFirebase);
                }
            }
        });


    }

    /**
     * Comprobamos si el email esta verificado retornamos true si lo esta y false si no
     * @return : boolean
     */
    public boolean comprobarVerificacionEmail() {
        this.user.reload();
        return !this.user.isEmailVerified();
    }

    /**
     * Metodo en el que enviamos al usuario un email de verificacion.
     */
    public void sendVerificationMail() {
        this.user.sendEmailVerification();
    }
}
