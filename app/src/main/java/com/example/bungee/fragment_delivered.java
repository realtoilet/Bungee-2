package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class fragment_delivered extends Fragment {

    RecyclerView rv;
    DBClass db;
    RV_AcceptAdapter rva;
    String email;

    public fragment_delivered(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delivered, container, false);

        rv = v.findViewById(R.id.rv);
        db = new DBClass(getContext());
        rva=  new RV_AcceptAdapter(db.retrieveDeliveredAsUser(email), db, "done", getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(rva);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerview();
    }
    public void refreshRecyclerview(){
        rva=  new RV_AcceptAdapter(db.retrieveDeliveredAsUser(email), db, "done", getContext());
        rv.setAdapter(rva);
    }
}