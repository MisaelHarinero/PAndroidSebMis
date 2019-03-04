package com.compas.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compas.R;
import com.compas.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewDataUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewDataUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDataUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //Elements Interface
    private TextView id;
    private TextView name;
    private TextView surname;
    private TextView dateBorn;
    private TextView email;
    private TextView karma;
    //Info
    private User user;

    private OnFragmentInteractionListener mListener;

    public ViewDataUser() {
        // Required empty public constructor
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewDataUser.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDataUser newInstance(String param1, String param2) {
        ViewDataUser fragment = new ViewDataUser();
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
        View view = getLayoutInflater().inflate(R.layout.fragment_edit_user,container,false);
        this.id = view.findViewById(R.id.tag);
        this.name = view.findViewById(R.id.name);
        this.surname = view.findViewById(R.id.surname);
        this.email = view.findViewById(R.id.mail);
        this.dateBorn = view.findViewById(R.id.date);
        this.karma = view.findViewById(R.id.karma);
        this.id.setText(this.user.getId());
        this.name.setText(this.user.getName());
        this.surname.setText(this.user.getSurnames());
        this.email.setText(this.user.getMail());
        this.dateBorn.setText(this.user.getDate_born().toDate().toString());
        this.karma.setText(Integer.toString(this.user.getKarma()));
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
