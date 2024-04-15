package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class selleraccept extends Fragment {
    RecyclerView rv;
    DBClass db;
    String email;

    public selleraccept(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selleraccept, container, false);

        rv = v.findViewById(R.id.rv);
        db = new DBClass(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new RV_AcceptAdapter(db.retrievePendingSeller(email), db, "accepted", getContext()));
        return v;
    }
}