package com.compas.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compas.R;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Ticket;
import com.compas.model.User;
import com.compas.view.adapters.TicketNotServedAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTiketsNotServed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTiketsNotServed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTiketsNotServed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //Interface Elements
    private RecyclerView list;
    //Data
    private User user;
    private ArrayList<Ticket> tickets;
    private OnFragmentInteractionListener mListener;

    public ViewTiketsNotServed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewTiketsNotServed.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTiketsNotServed newInstance(String param1, String param2) {
        ViewTiketsNotServed fragment = new ViewTiketsNotServed();
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
        View view = inflater.inflate(R.layout.fragment_view_tikets_not_served, container, false);
        this.list = view.findViewById(R.id.recycler);
        this.tickets = new ArrayList<>();
        TicketNotServedAdapter adapter = new TicketNotServedAdapter(this.tickets,this.user);
        this.list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FireStoreController controller = new FireStoreController();
        controller.getTikectsNotServed().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                   if (queryDocumentSnapshots != null){
                       tickets.clear();
                       for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                           Ticket tk = snapshot.toObject(Ticket.class);
                           if (!tk.getTagTransmitter().equals(user.getId())){
                               tickets.add(tk);
                           }
                       }
                       adapter.notifyDataSetChanged();
                   }

            }
        });

        this.list.setAdapter(adapter);

        return view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
