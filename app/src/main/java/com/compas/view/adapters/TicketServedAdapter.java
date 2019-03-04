package com.compas.view.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.compas.MainActivity;
import com.compas.R;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Ticket;
import com.compas.model.User;
import com.compas.view.ViewDataTicket;

import java.util.ArrayList;

public class TicketServedAdapter extends RecyclerView.Adapter<TicketServedAdapter.MyView> {
    private ArrayList<Ticket> tickets;
    private User user;
    private MainActivity activity;

    public TicketServedAdapter(ArrayList<Ticket> tickets, User user,MainActivity activity) {
        this.tickets = tickets;
        this.user = user;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_layout, viewGroup, false);
        MyView myView = new MyView(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
        myView.title.setText(this.tickets.get(i).getTitle());
        myView.tagEmisor.setText(this.tickets.get(i).getTagTransmitter());
        myView.descripcion.setText(this.tickets.get(i).getDescription());
        myView.importance.setText(Integer.toString(this.tickets.get(i).getImportance()));
        myView.date.setText(this.tickets.get(i).getDate().toDate().toString());
        myView.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDataTicket dataTicket = new ViewDataTicket();
                dataTicket.setTicket(tickets.get(i));
                dataTicket.setUser(user);
                activity.startFragmentTicket(dataTicket);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView title;
        TextView tagEmisor;
        TextView date;
        TextView importance;
        TextView descripcion;
        View view;

        public MyView(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.header);
            tagEmisor = view.findViewById(R.id.tagEmisor);
            date = view.findViewById(R.id.date);
            importance = view.findViewById(R.id.importance);
            descripcion = view.findViewById(R.id.descripcion);
            this.view = view;
        }
    }

}
