package com.example.bungee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bungee.databinding.ActivityDashboardBinding;
import com.example.bungee.databinding.FragmentDashboardBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fragment_dashboard extends Fragment {
    FragmentDashboardBinding binding;
    GridView grid;
    DBClass db;
    RV_DashboardAdapter gridAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        grid = view.findViewById(R.id.grid);
        db = new DBClass(getContext());

       gridAdapter = new RV_DashboardAdapter(requireContext(), db.getAllProducts(), readTxt());
        grid.setAdapter(gridAdapter);
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

    public interface OnDatasetChangedListener {
        void onDatasetChanged();
    }
    public void refreshData() {
        gridAdapter = new RV_DashboardAdapter(requireContext(), db.getAllProducts(), readTxt());
        grid.setAdapter(gridAdapter);
    }

}
