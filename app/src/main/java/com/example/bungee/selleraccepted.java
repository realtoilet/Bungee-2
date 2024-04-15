package com.example.bungee;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class selleraccepted extends Fragment {

    RecyclerView rv;
    DBClass db;
    String email;

    public selleraccepted(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selleraccepted, container, false);

        rv = v.findViewById(R.id.rv);
        db = new DBClass(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new RV_StatesAdapter(db.retrieveAcceptedSeller(email), getContext(), db));
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }
    public void refreshRecyclerView() {
        if (rv != null) {
            rv.setAdapter(new RV_StatesAdapter(db.retrieveAcceptedSeller(email), getContext(), db));
        }
    }
}