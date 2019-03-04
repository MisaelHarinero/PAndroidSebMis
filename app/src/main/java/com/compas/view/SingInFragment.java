package com.compas.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compas.LogInActivity;
import com.compas.R;

import com.compas.controller.CheckRulesFormulary;
import com.compas.controller.connectionFirebase.AuthenticationFirebase;
import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingInFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Controller
    private AuthenticationFirebase authenticationFirebase;
    //Interace Reference
    private EditText email;
    private EditText password;
    //Google Deshabilitado
    private SignInButton button;
    //Botones de inicio de Sesion con el correo
    private Button singIn;
    private Button logIn;
    private AuthenticationFirebase auth;
    private LogInActivity activity;



    private OnFragmentInteractionListener mListener;

    public SingInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingInFragment newInstance(String param1, String param2) {
        SingInFragment fragment = new SingInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sing_in, container, false);
        this.email = view.findViewById(R.id.email);
        this.password = view.findViewById(R.id.password);
        this.singIn = view.findViewById(R.id.sing_in_button);
        this.logIn = view.findViewById(R.id.logInButton);
        this.singIn.setOnClickListener(this);
        this.logIn.setOnClickListener(this);
        this.auth = new AuthenticationFirebase();
        if (this.auth.getUser() != null){
            chargeMainActivity();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sing_in_button:{
                Toast.makeText(v.getContext(), (this.auth.getUser() != null ? this.auth.getUser().getEmail() : "No hay nadie"), Toast.LENGTH_SHORT).show();
                singIn();
                break;
            }
            case R.id.logInButton:{

                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setActivity(activity);
                registerFragment.setFirebase(this.auth);
                getFragmentManager().beginTransaction().replace(R.id.mainContainer,registerFragment).commit();
                break;
            }

        }
    }
    public void singIn(){
        String mail = this.email.getText().toString(), password = this.password.getText().toString();
        this.auth.singInEmail(mail,password,this);
        if (CheckRulesFormulary.checkMail(mail) && CheckRulesFormulary.checkPassword(password)){
        }

    }



    public void setActivity(LogInActivity activity) {
        this.activity = activity;
    }

    public void chargeVerificationMail(){

        VerificationFragment verificationFragment = new VerificationFragment();
        getFragmentManager().beginTransaction().replace(R.id.mainContainer,verificationFragment);
    }
    public void chargeMainActivity(){
        this.activity.intenToMain();


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
