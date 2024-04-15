package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_riders extends Fragment {

    DBClass db;
    RV_AcceptTypeAdapter rva;
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_riders, container, false);
        db = new DBClass(getContext());
        rva = new RV_AcceptTypeAdapter(db.retrieveRequests("rider"), db, getContext(), "rider");
        rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(rva);
        return v;
    }
}