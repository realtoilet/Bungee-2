package com.example.bungee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RV_ApplyAdapter extends RecyclerView.Adapter<RV_ApplyAdapter.ViewHolder> {
    List<UserType> list;
    DBClass db;
    Context c;
    static String[] a = {"Rider", "Seller"};

    public RV_ApplyAdapter(List<UserType> list, DBClass db, Context c) {
        this.list = list;
        this.db = db;
        this.c = c;
    }

    @NonNull
    @Override
    public RV_ApplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_requests, parent, false);
        return new RV_ApplyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_ApplyAdapter.ViewHolder h, int position) {
        UserType ut = list.get(position);

        h.spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                h.spn.setSelection(position);
                ut.setRequestType(h.spn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        h.btn.setOnClickListener(v->{
            if(!ut.getRequestType().isEmpty()){
                db.sendRequest(new UserType(ut.getEmail(), ut.getRequestType(), ut.getCurrentType()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Spinner spn;
        Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spn = itemView.findViewById(R.id.spinner);
            btn = itemView.findViewById(R.id.apply);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, a);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spn.setAdapter(adapter);
        }
    }
}
