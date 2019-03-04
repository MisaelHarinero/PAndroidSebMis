package com.compas.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compas.R;
import com.compas.model.Message;
import com.compas.model.Ticket;
import com.compas.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MssgAdapter extends RecyclerView.Adapter<MssgAdapter.MyView> {
    private ArrayList<Message> menssages;
    private User user;

    public MssgAdapter(ArrayList<Message> messages, User user) {
        this.menssages = messages;
        this.user = user;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.menssages.get(position).getUidTransmitter().equals(this.user.getId())){
            return 0;
        }else{
            return 1;
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (i == 0){
            view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_messages, viewGroup, false);
        }else{
           view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_msg, viewGroup, false);

        }
        MyView myView = new MyView(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
       myView.mssg.setText(this.menssages.get(i).getMmsg());
       myView.idUser.setText(this.menssages.get(i).getUidTransmitter());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       myView.date.setText(format.format(this.menssages.get(i).getDate().toDate()));
    }

    @Override
    public int getItemCount() {
        return menssages.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView mssg;
        TextView idUser;
        TextView date;



        public MyView(@NonNull View view) {
            super(view);
            mssg = view.findViewById(R.id.msg);
            idUser = view.findViewById(R.id.iduser);
            date = view.findViewById(R.id.date);
        }
    }

}
