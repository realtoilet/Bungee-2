package com.example.bungee;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RV_CartAdapter extends RecyclerView.Adapter<RV_CartAdapter.ViewHolder> {

    private List<AccountCart> ac;
    private Context c;
    DBClass db;
    OrderList od;
    fragment_cart f;

    public RV_CartAdapter(List<AccountCart> ac, Context c, DBClass db, OrderList od, fragment_cart f) {
        this.ac = ac;
        this.c = c;
        this.db = db;
        this.od = od;
        this.f = f;
    }

    @NonNull
    @Override
    public RV_CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart, parent, false);
        return new RV_CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_CartAdapter.ViewHolder h, int position) {
        AccountCart curr = ac.get(position);

        h.img.setImageBitmap(BitmapFactory.decodeByteArray(curr.getImg(), 0, curr.getImg().length));
        h.name.setText(String.valueOf(curr.getProductName()));
        h.count.setText(String.valueOf(curr.getProductQuantity()));
        h.price.setText(String.valueOf(curr.getProductPrice() * curr.getProductQuantity()));
        h.seller.setText("From: " + db.getUser(curr.getSeller()));

        h.add.setOnClickListener(v->{
            curr.setProductQuantity(curr.getProductQuantity() + 1);
            h.count.setText(String.valueOf(curr.getProductQuantity()));
            h.price.setText(String.valueOf(curr.getProductPrice() * curr.getProductQuantity()));
        });

        h.minus.setOnClickListener(v->{
            if(curr.getProductQuantity() != 1){
                curr.setProductQuantity(curr.getProductQuantity() - 1);
                h.count.setText(String.valueOf(curr.getProductQuantity()));
                h.price.setText(String.valueOf(curr.getProductPrice() * curr.getProductQuantity()));
            }
        });

        h.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                od.selectedOrders.add(new AccountCart(curr.getProductName(), curr.getSeller(),
                        curr.getProductPrice(), curr.getProductQuantity(), curr.getImg()));
                f.total.setText(String.valueOf(getTotal()));
            } else {
                for (int i = 0; i < od.selectedOrders.size(); i++) {
                    AccountCart item = od.selectedOrders.get(i);
                    if (item.getProductName().equals(curr.getProductName()) &&
                            item.getSeller().equals(curr.getSeller())) {
                        od.selectedOrders.remove(i);
                        break;
                    }
                }
                f.total.setText(String.valueOf(getTotal()));
            }
        });
    }
    public int getTotal(){
        int currtotal = 0;
        for (int i = 0; i < od.selectedOrders.size(); i++){
            currtotal += od.selectedOrders.get(i).getProductPrice() * od.selectedOrders.get(i).getProductQuantity();
        }

        return currtotal;
    }

    @Override
    public int getItemCount() {
        return ac.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView count, name, price, add, minus, seller;
        CheckBox cb;
        public ViewHolder(@NonNull View v) {
            super(v);

            img = v.findViewById(R.id.img);
            add = v.findViewById(R.id.add);
            minus = v.findViewById(R.id.minus);
            count = v.findViewById(R.id.count);
            name = v.findViewById(R.id.itemname);
            price = v.findViewById(R.id.itemprice);
            seller = v.findViewById(R.id.itemseller);
            cb = v.findViewById(R.id.checkBox);
        }
    }
}
