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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.compas.R;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Message;
import com.compas.model.Ticket;
import com.compas.model.User;
import com.compas.view.adapters.MssgAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewDataTicket.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewDataTicket#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDataTicket extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //Interface
    private TextView title;
    private TextView tagEmisor;
    private TextView date;
    private TextView importance;
    private TextView descripcion;
    private RecyclerView list;
    //Data
    private User user;
    private Ticket ticket;
    private ArrayList<Message> messages;
    //New Mssg
    private EditText text;
    private ImageButton button;
    private MssgAdapter adapter;
    //Firestore
    private FireStoreController controller;

    private OnFragmentInteractionListener mListener;

    public ViewDataTicket() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewDataTicket.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDataTicket newInstance(String param1, String param2) {
        ViewDataTicket fragment = new ViewDataTicket();
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
        View view = inflater.inflate(R.layout.fragment_view_data_ticket, container, false);
        title = view.findViewById(R.id.header);
        tagEmisor = view.findViewById(R.id.tagEmisor);
        date = view.findViewById(R.id.date);
        importance = view.findViewById(R.id.importance);
        descripcion = view.findViewById(R.id.descripcion);
        this.text = view.findViewById(R.id.mmsg);
        this.button = view.findViewById(R.id.send);
        this.button.setOnClickListener(this);
        this.messages = new ArrayList<>();
        this.title.setText(this.ticket.getTitle());
        this.tagEmisor.setText(this.ticket.getTagTransmitter());
        this.date.setText(this.ticket.getDate().toDate().toString());
        this.importance.setText(Integer.toString(this.ticket.getImportance()));
        this.descripcion.setText(this.ticket.getDescription());
        if (ticket.getMessages()==null){
            ticket.setMessages(new ArrayList<>());
        }
        for (Message message : this.ticket.getMessages()) {
            this.messages.add(message);
        }
        this.list = view.findViewById(R.id.recycler);
        this.adapter = new MssgAdapter(this.messages, this.user);
        this.list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.controller = new FireStoreController();
        controller.getTicket(this.ticket.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    Ticket ticket = documentSnapshot.toObject(Ticket.class);
                    if (ticket != null){
                        if (ticket.getMessages() != null){
                            messages.clear();
                            for (Message message : ticket.getMessages()) {
                                messages.add(message);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    moveToLast();
                }
            }
        });
        moveToLast();
        this.list.setAdapter(adapter);
        return view;
    }
    public void moveToLast(){
        this.list.getLayoutManager().scrollToPosition(this.messages.size()-1);
    }
    public User getUser() {
        return user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.send: {
                sendMesage();
                break;
            }
        }
    }

    public void sendMesage() {
        if (!this.text.getText().toString().equals("")) {
            Message message = new Message(this.user.getId(), Timestamp.now(), this.text.getText().toString());
            this.messages.add(message);
            this.ticket.getMessages().add(message);
            controller.setTicketAtended(this.ticket);
            this.adapter.notifyDataSetChanged();
            moveToLast();
        }


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
