package com.example.bungee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RV_AcceptTypeAdapter extends RecyclerView.Adapter<RV_AcceptTypeAdapter.ViewHolder> {
    List<UserType> list;
    DBClass db;
    Context c;
    String action;

    public RV_AcceptTypeAdapter(List<UserType> list, DBClass db, Context c, String action) {
        this.list = list;
        this.db = db;
        this.c = c;
        this.action = action;
    }

    @NonNull
    @Override
    public RV_AcceptTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_requests, parent, false);
        return new RV_AcceptTypeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_AcceptTypeAdapter.ViewHolder h, int position) {
        UserType ut = list.get(position);

        h.username.setText(ut.getEmail());
        h.request.setText("Request Type: " + ut.getRequestType());

        h.accept.setOnClickListener(v->{
            int adapterPosition = h.getAdapterPosition();
            if(adapterPosition != RecyclerView.NO_POSITION){
                UserType clicked = list.get(adapterPosition);
                db.changeType(action, clicked.getEmail());
                list.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, request, current;
        Button accept;
        public ViewHolder(@NonNull View v) {
            super(v);

            username = v.findViewById(R.id.username);
            request = v.findViewById(R.id.request);
            accept = v.findViewById(R.id.accept);
        }
    }
}
