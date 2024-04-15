package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_todeliver extends Fragment {

    DBClass db;
    RV_AcceptAdapter rva;
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todeliver, container, false);

        db = new DBClass(getContext());
        rva = new RV_AcceptAdapter(db.retrieveAllAcceptedForAdmin(), db, "todeliver", getContext());
        rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(rva);

        return v;
    }
}