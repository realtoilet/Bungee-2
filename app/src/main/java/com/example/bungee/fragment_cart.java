package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragment_cart extends Fragment {
    RecyclerView rv;
    RV_CartAdapter rca;
    DBClass db;
    Button btn;
    TextView total;
    OrderList od;
    String email;

    public fragment_cart(String email, OrderList od) {
        this.email = email;
        this.od = od;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        rv = v.findViewById(R.id.rv);
        db = new DBClass(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rca = new RV_CartAdapter(db.getCart(readTxt()), getContext(), db, od, this);
        rv.setAdapter(rca);

        btn = v.findViewById(R.id.checkout);
        total = v.findViewById(R.id.total);

        total.setText(String.valueOf(rca.getTotal()));

        btn.setOnClickListener(vi->{
            boolean succ = db.addToOrders(od, email);
            removeCart();

            if(succ){
                Toast.makeText(getContext(), "Succiss", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Feyld", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void removeCart() {
        for (AccountCart item : od.selectedOrders) {
            db.deleteFromCart(email, item.getProductName(), item.getSeller());
        }

        od.selectedOrders.clear();
        rca = new RV_CartAdapter(db.getCart(readTxt()), getContext(), db, od, this);
        rv.setAdapter(rca);
        fragment_accepted f = new fragment_accepted();
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