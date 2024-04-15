package com.example.bungee;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class fragment_login extends Fragment {

    EditText et_email, et_password;
    Button btn_login, btn_signup;
    DBClass db;
    File f;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        et_email = v.findViewById(R.id.email);
        et_password = v.findViewById(R.id.password);
        btn_login = v.findViewById(R.id.login);
        btn_signup = v.findViewById(R.id.signup);

        db = new DBClass(getContext());

        btn_login.setOnClickListener(listener->{
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();
            if(!email.isEmpty() && !password.isEmpty()){
                Boolean found = db.checkCredentials(email, password);
                if(found){
                    addToTxt(email);
                    Intent i = new Intent(getActivity(), dashboard.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "User not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill up all the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_signup.setOnClickListener(listener->{
            account_views ac = (account_views) getActivity();
            ac.vp.setCurrentItem(1);
        });

        return v;
    }
    public void addToTxt(String username){
        try {
            File file = new File(getContext().getFilesDir(), "email.txt");
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(username);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error writing to user.txt", Toast.LENGTH_SHORT).show();
        }
    }
}