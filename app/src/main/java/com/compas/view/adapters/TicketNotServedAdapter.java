package com.compas.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.compas.R;
import com.compas.controller.connectionFirebase.FireStoreController;
import com.compas.model.Ticket;
import com.compas.model.User;

import java.util.ArrayList;

public class TicketNotServedAdapter extends RecyclerView.Adapter<TicketNotServedAdapter.MyView> {
    private ArrayList<Ticket> tickets;
    private User user;

    public TicketNotServedAdapter(ArrayList<Ticket> tickets,User user) {
        this.tickets = tickets;
        this.user = user;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_not_served_layout, viewGroup, false);
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
                tickets.get(i).setIdResponsable(user.getId());
                FireStoreController controller = new FireStoreController();
                controller.setTicketAtended(tickets.get(i));
                tickets.remove(i);
                notifyDataSetChanged();
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
        Button button;

        public MyView(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.header);
            tagEmisor = view.findViewById(R.id.tagEmisor);
            date = view.findViewById(R.id.date);
            importance = view.findViewById(R.id.importance);
            descripcion = view.findViewById(R.id.descripcion);
            button = view.findViewById(R.id.agregar);
        }
    }

}
