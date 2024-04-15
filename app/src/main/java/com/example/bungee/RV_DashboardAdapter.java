package com.example.bungee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RV_DashboardAdapter extends BaseAdapter {

    Context c;
    List<ProductData> pdata;
    Dialog d;
    DBClass db;
    String email;

    public RV_DashboardAdapter(Context c, List<ProductData> pdata, String email) {
        this.c = c;
        this.pdata = pdata;
        this.email = email;
    }

    @Override
    public int getCount() {
        return pdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(c).inflate(R.layout.dashboard_container, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView price = convertView.findViewById(R.id.price);
        TextView sold = convertView.findViewById(R.id.sold);
        TextView rating = convertView.findViewById(R.id.ratings);
        ImageView iv = convertView.findViewById(R.id.iv);
        ProductData productData = pdata.get(position);

        db = new DBClass(c);

        name.setText(productData.getName());
        price.setText("PHP" + String.valueOf(productData.getPrice()));
        sold.setText("Sold: " + String.valueOf(productData.getSold()));
        iv.setImageBitmap(BitmapFactory.decodeByteArray(productData.getImage(), 0, productData.getImage().length));
        rating.setText(String.valueOf(productData.getRating() / productData.getBought()));
        Log.d("Image URL", productData.getImage().toString());

        convertView.setOnClickListener(v->{
            ProductData productDataCopy = pdata.get(position);
            d = new Dialog(c);
            d.setContentView(R.layout.card_dialog);
            d.setCancelable(true);

            TextView dgname = d.findViewById(R.id.itemName);
            TextView dgprice = d.findViewById(R.id.itemPrice);
            TextView dgstock = d.findViewById(R.id.itemStock);
            ImageView pos = d.findViewById(R.id.add);
            ImageView minus = d.findViewById(R.id.minus);
            TextView count = d.findViewById(R.id.stocknum);
            ImageView img = d.findViewById(R.id.ivcard);
            Button tocart = d.findViewById(R.id.tocart);
            final int[] currCount = {1};
            tocart.setOnClickListener(vi->{
                boolean result = db.toCart(email, productDataCopy.getName(), productDataCopy.getPrice(), currCount[0], productDataCopy.getSeller(), productDataCopy.getImage());
                if(result) Toast.makeText(c, "Added to cart.", Toast.LENGTH_SHORT).show();
            });
            img.setImageBitmap(BitmapFactory.decodeByteArray(productDataCopy.getImage(), 0, productDataCopy.getImage().length));
            dgname.setText(productDataCopy.getName());
            dgprice.setText(String.valueOf("PHP" + productDataCopy.getPrice()));
            dgstock.setText(String.valueOf("Sold: " + productDataCopy.getSold()));

            count.setText(String.valueOf(currCount[0]));

            pos.setOnClickListener(vi -> {
                if (currCount[0] <= productDataCopy.getQuantity() - 1) {
                    currCount[0]++;
                    count.setText(String.valueOf(currCount[0]));
                }
            });

            minus.setOnClickListener(vi -> {
                if (currCount[0] != 1) {
                    currCount[0]--;
                    count.setText(String.valueOf(currCount[0]));
                }
            });

            d.show();
        });

        return convertView;
    }
    public String readTxt() {
        StringBuilder ca = new StringBuilder();

        try {
            File file = new File(c.getFilesDir(), "email.txt");

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    ca.append(line);
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
