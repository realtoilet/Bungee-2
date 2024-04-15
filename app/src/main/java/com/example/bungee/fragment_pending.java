package com.example.bungee;

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

public class fragment_pending extends Fragment {

    RecyclerView rv;
    DBClass db;
    RV_StatesAdapter rva;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);

        rv = v.findViewById(R.id.rv);
        db = new DBClass(getContext());
        rva = new RV_StatesAdapter(db.retrievePendingBuyer(readTxt()), getContext(), db);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(rva);
        return v;
    }
    public void refreshRecyclerview(){
        rva = new RV_StatesAdapter(db.retrievePendingBuyer(readTxt()), getContext(), db);
        rv.setAdapter(rva);
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerview();
    }

    public String readTxt() {
        StringBuilder c = new StringBuilder();

        try {
            File file = new File(getContext().getFilesDir(), "email.txt");

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    c.append(line);
                }
                reader.close();
            } else {
                Log.d("FILE", "File not found: email.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c.toString();
    }
}