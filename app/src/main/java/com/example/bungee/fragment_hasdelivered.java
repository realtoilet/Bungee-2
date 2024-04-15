package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_hasdelivered extends Fragment {
    String email;
    DBClass db;
    RV_StatesAdapter rva;
    RecyclerView rv;
    public fragment_hasdelivered(String email) {
        this.email = email;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hasdelivered, container, false);
        db = new DBClass(getContext());
        rva = new RV_StatesAdapter(db.retrieveDeliveredForRider(email), getContext(), db);
        rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(rva);


        return v;
    }
    public void refreshRecycleview(){
        rva = new RV_StatesAdapter(db.retrieveDeliveredForRider(email), getContext(), db);
        rv.setAdapter(rva);

    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecycleview();
    }
}