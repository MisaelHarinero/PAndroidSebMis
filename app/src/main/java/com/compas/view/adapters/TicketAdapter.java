package com.compas.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.compas.MainActivity;
import com.compas.R;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Ticket;
import com.compas.model.User;
import com.compas.view.ViewDataTicket;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyView> {
    private ArrayList<Ticket> tickets;
    private User user;
    private MainActivity activity;

    public TicketAdapter(ArrayList<Ticket> tickets,User user, MainActivity activity) {
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
        myView.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireStoreController controller = new FireStoreController();
                controller.deleteTicket(tickets.get(i).getUid());
                tickets.remove(i);
                notifyDataSetChanged();
            }
        });
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
        ImageButton button;
        View view;

        public MyView(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.header);
            tagEmisor = view.findViewById(R.id.tagEmisor);
            date = view.findViewById(R.id.date);
            importance = view.findViewById(R.id.importance);
            descripcion = view.findViewById(R.id.descripcion);
            button = view.findViewById(R.id.deleteTicket);
            this.view = view;
        }
    }

}
