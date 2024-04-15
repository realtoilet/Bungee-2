package com.example.bungee;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RV_StatesAdapter extends RecyclerView.Adapter<RV_StatesAdapter.ViewHolder> {
    List<OrderData> od;
    Context c;
    DBClass db;

    public RV_StatesAdapter(List<OrderData> od, Context c, DBClass db) {
        this.od = od;
        this.c = c;
        this.db = db;
    }

    @NonNull
    @Override
    public RV_StatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_states, parent, false);
        return new RV_StatesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_StatesAdapter.ViewHolder h, int position) {
        OrderData order = od.get(position);

        h.name.setText(order.getName());
        h.price.setText("PHP: " + String.valueOf(order.getPrice()));
        h.quantity.setText("Amount: " + String.valueOf(order.getQuantity()));
        h.seller.setText("Seller: " + db.getUser(order.getSeller()));
        h.img.setImageBitmap(BitmapFactory.decodeByteArray(order.getImg(), 0,order.getImg().length));
    }

    @Override
    public int getItemCount() {
        return od.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, quantity, price, seller;
        public ViewHolder(@NonNull View v) {
            super(v);

            name = v.findViewById(R.id.itemname);
            quantity = v.findViewById(R.id.itemquantity);
            price = v.findViewById(R.id.itemprice);
            seller = v.findViewById(R.id.itemseller);
            img = v.findViewById(R.id.img);
        }
    }
}
