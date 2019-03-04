package com.compas.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compas.LogInActivity;
import com.compas.R;
import com.compas.controller.CheckRulesFormulary;
import com.compas.controller.connectionFirebase.AuthenticationFirebase;
import com.compas.model.User;
import com.google.firebase.Timestamp;

import java.sql.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnKeyListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Interface Elements
    private EditText name;
    private EditText surname;
    private EditText birthdate;
    private EditText mail;
    private EditText password;
    private EditText rePassword;
    private CheckBox checkPolitics;
    private Button buttonRegister;
    private TextView error;
    //Firebase
    private AuthenticationFirebase firebase;
    private LogInActivity activity;


    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }



    public void setActivity(LogInActivity activity) {
        this.activity = activity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        this.name = view.findViewById(R.id.etName);
        this.surname = view.findViewById(R.id.etSurname);
        this.birthdate = view.findViewById(R.id.etDate);
        this.mail = view.findViewById(R.id.etMail);
        this.password = view.findViewById(R.id.etPass);
        this.rePassword = view.findViewById(R.id.etRepeatPass);
        this.checkPolitics = view.findViewById(R.id.checkBox);
        this.buttonRegister = view.findViewById(R.id.buttonContinue);
        this.error = view.findViewById(R.id.errorMssg);
        this.rePassword.setOnKeyListener(this);
        this.buttonRegister.setOnClickListener(this);


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
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {

            case R.id.etRepeatPass: {
                if (password.getText().toString().equals(rePassword.getText().toString())) {
                    password.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null));
                    rePassword.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null));
                } else {
                    password.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null));
                    rePassword.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null));
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonContinue: {
                register();
                break;
            }

        }
    }

    public void register() {
        String name, surname, date, email, pass, rePass;
        name = this.name.getText().toString();
        surname = this.surname.getText().toString();
        date = this.birthdate.getText().toString();
        email = this.mail.getText().toString();
        pass = this.password.getText().toString();
        rePass = this.rePassword.getText().toString();
        String mmsg = "";
        boolean register = true;
        if (!name.equals("") && !surname.equals("") &&
                !date.equals("") &&
                !email.equals("") &&
                !pass.equals("") &&
                !rePass.equals("")) {
            if (!CheckRulesFormulary.checkMail(email)) {
                register = false;
                mmsg += "El email esta mal formado\n";
            }
            if (!CheckRulesFormulary.checkPassword(pass)) {
                register = false;
                mmsg += "La contraseña esta mal formada\n";
            }
            if (!pass.equals(rePass)) {
                register = false;
                mmsg += "Las contraseñas tienen que ser las mismas";


            }
            if (!checkPolitics.isChecked()) {
                register = false;
                mmsg += "Tienes que aceptar nuestra Politica de privacidad";
            }


        } else {
            register = false;
            mmsg += "Tienes que introducir todos los datos\n";
        }
        if (register) {
            String id = generateID(email.trim());
            try {

                User user = new User(id, name, surname, email, new Timestamp(Date.valueOf(date)));
                this.firebase.logIn(user, pass, activity,this.firebase);
            } catch (IllegalArgumentException e) {
                mmsg += "Formato de la fecha debe ser yyyy-MM-dd";

            }

        }
        this.error.setText(mmsg);
    }

    public AuthenticationFirebase getFirebase() {
        return firebase;
    }

    public void setFirebase(AuthenticationFirebase firebase) {
        this.firebase = firebase;
    }

    public String generateID(String mail) {
        String mailCut[] = mail.split("@");
        String newID = mailCut[0] + "#" + generate4DigitsNumber();
        return newID;
    }

    public String generate4DigitsNumber() {
        String number = "";
        for (int i = 0; i < 4; i++) {
            number += Integer.toString((int) Math.floor(Math.random() * 10));
        }
        return number;
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
