package com.example.bungee;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RV_AcceptAdapter extends RecyclerView.Adapter<RV_AcceptAdapter.ViewHolder> {
    List<OrderData> od;
    DBClass db;
    String action;
    Context c;

    public RV_AcceptAdapter(List<OrderData> od, DBClass db, String action, Context c) {
        this.od = od;
        this.db = db;
        this.action = action;
        this.c = c;
    }

    @NonNull
    @Override
    public RV_AcceptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orders, parent, false);
        return new RV_AcceptAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_AcceptAdapter.ViewHolder h, int position) {
        OrderData o = od.get(position);

        if(action.equals("todeliver")){
            h.accept.setText("DELIVER NOW");
        }
        h.img.setImageBitmap(BitmapFactory.decodeByteArray(o.getImg(), 0, o.getImg().length));
        h.name.setText(o.getName());
        h.price.setText("PHP: " + o.getPrice());
        h.quantity.setText("AMOUNT: " + o.getQuantity());
        h.email.setText(o.getEmail());
        if(action.equals("done")){
           h.accept.setOnClickListener(v->{
               dialog(position, h.getAdapterPosition());
           });

        } else {
            h.accept.setOnClickListener(v -> {
                int adapterPosition = h.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    OrderData clickedItem = od.get(adapterPosition);
                    db.updateState(clickedItem.getEmail(), clickedItem.getId(), clickedItem.getSeller(), action);
                    od.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            });
        }
    }

    public void dialog(int pos, int clickPos){
        Dialog d = new Dialog(c);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.ratings_dialog);
        d.setCancelable(true);
        d.getWindow().setGravity(Gravity.CENTER);
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();
        RatingBar rb = d.findViewById(R.id.ratingBar);
        ImageView iv = d.findViewById(R.id.ivcard);
        TextView name = d.findViewById(R.id.itemName);
        TextView price = d.findViewById(R.id.itemPrice);
        Button btn = d.findViewById(R.id.button);

        OrderData od1 = od.get(pos);
        iv.setImageBitmap(BitmapFactory.decodeByteArray(od1.getImg(), 0, od1.getImg().length));
        name.setText(od1.getName());
        price.setText("PHP: " + od1.getPrice());
        btn.setOnClickListener(v->{
            db.addRating(od1.getName(), rb.getRating());
            if (clickPos != RecyclerView.NO_POSITION) {
                OrderData clickedItem = od.get(clickPos);
                db.updateState(clickedItem.getEmail(), clickedItem.getId(), clickedItem.getSeller(), action);
                od.remove(clickPos);
                notifyItemRemoved(clickPos);
            }
            d.dismiss();
        });

    }
    @Override
    public int getItemCount() {
        return od.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, quantity, price, email;
        Button accept;
        public ViewHolder(@NonNull View v) {
            super(v);

            name = v.findViewById(R.id.itemname);
            quantity = v.findViewById(R.id.quantity);
            price = v.findViewById(R.id.itemprice);
            email = v.findViewById(R.id.email);
            img = v.findViewById(R.id.img);
            accept = v.findViewById(R.id.accept);
        }
    }
}
