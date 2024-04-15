package com.example.bungee;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bungee.databinding.ActivityDashboardBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class dashboard extends AppCompatActivity implements fragment_dashboard.OnDatasetChangedListener {
    ActivityDashboardBinding adb;
    GridView grid;
    DBClass db;
    ImageView paw;
    String email, type;
    RV_DashboardAdapter gridAdapter;

    Uri img;
    Bitmap pb;
    ActivityResultLauncher<Intent> startAct;
    OrderList od;
    String currReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Change status bar color if API level is Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.darkblue));
        }

        replaceFragment(new fragment_dashboard());

        paw = findViewById(R.id.paw);
        db = new DBClass(getApplicationContext());
        email = readTxt();
        type = db.getType(email);

        od  = new OrderList();

        start();
        paw.setOnClickListener(v->{
            showDiag();
        });
    }

    // Rest of your code remains unchanged...


    public void showApplyDialog(){
        Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_apply);
        d.setCancelable(true);
        d.getWindow().setGravity(Gravity.CENTER);
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();

        Button btn = d.findViewById(R.id.apply);
        Spinner spn = d.findViewById(R.id.spinner);
        String[] a = {"rider", "seller"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currReq = spn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn.setOnClickListener(v->{
            if(!currReq.isEmpty()){
                db.sendRequest(new UserType(email, currReq, db.retrieveCurrentType(email)));
                Toast.makeText(this, "Successfully applied. + curr type is " + db.retrieveCurrentType(email), Toast.LENGTH_SHORT).show();
                d.dismiss();
            } else {
                Toast.makeText(this, "Request type is empty", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showSellerDialog(){

        Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_seller);
        d.setCancelable(true);
        d.getWindow().setGravity(Gravity.CENTER);
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText name = d.findViewById(R.id.name);
        EditText price = d.findViewById(R.id.price);
        EditText quantity = d.findViewById(R.id.quantity);
        Button getimg = d.findViewById(R.id.addimage);
        Button confirm = d.findViewById(R.id.confirm);
        gridAdapter= new RV_DashboardAdapter(getApplicationContext(), db.getAllProducts(), readTxt());
        getimg.setOnClickListener(v->{
            Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);
            startAct.launch(i);
        });

        confirm.setOnClickListener(v->{
            byte[] imgData = convertUriToByteArray(img);
            if(email.isEmpty() || price == null || Float.parseFloat(price.getText().toString()) <= 0 || quantity == null || Integer.parseInt(quantity.getText().toString()) <= 0|| imgData == null){
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
            } else {
                db.addProduct(email, name.getText().toString(), Float.parseFloat(price.getText().toString()), Integer.parseInt(quantity.getText().toString()), 0, 0, imgData);
            }
            img = null;

            gridAdapter.notifyDataSetChanged();
            if (dashboard.this instanceof fragment_dashboard.OnDatasetChangedListener) {
                ((fragment_dashboard.OnDatasetChangedListener) dashboard.this).onDatasetChanged();
            }
            onDatasetChanged();
        });

        d.show();
    }

    public void start(){
        startAct = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                img = o.getData().getData();
            }
        });
    }


    public void showDiag(){
        final Dialog dg = new Dialog(this);
        dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dg.setContentView(R.layout.dashboard_bottomsheet);
        LinearLayout dashboard = dg.findViewById(R.id.todashboard);
        LinearLayout apply = dg.findViewById(R.id.apply);
        LinearLayout logout = dg.findViewById(R.id.logout);
        LinearLayout add = dg.findViewById(R.id.add);
        LinearLayout admin = dg.findViewById(R.id.admin);
        LinearLayout view = dg.findViewById(R.id.vieworders);
        LinearLayout cart = dg.findViewById(R.id.cart);

        ViewGroup parent = (ViewGroup) add.getParent();

        add.setOnClickListener(v->{
            showSellerDialog();
            dg.dismiss();
        });
        if(type.equals("buyer")){
            parent.removeView(add);
            parent.removeView(admin);
            parent.removeView(view);
        } else if(type.equals("seller")){
            parent.removeView(apply);
            parent.removeView(cart);
            parent.removeView(admin);
        } else if(type.equals("rider")){
            parent.removeView(apply);
            parent.removeView(add);
            parent.removeView(cart);
            parent.removeView(admin);
        } else {
            parent.removeView(apply);
            parent.removeView(cart);
        }
        admin.setOnClickListener(l->{
            replaceFragment(new fragments_requests());
            dg.dismiss();
        });

        dashboard.setOnClickListener(l->{
            replaceFragment(new fragment_dashboard());
            dg.dismiss();
        });
        cart.setOnClickListener(l->{
            replaceFragment(new fragment_buyerOrder(email, od));
            dg.dismiss();
        });
        view.setOnClickListener(l->{
            if(type.equals("admin")){
                replaceFragment(new fragment_deliveries());
                dg.dismiss();
            } else if(type.equals("seller")){
                replaceFragment(new fragment_orders(email));
                dg.dismiss();
            } else if(type.equals("rider")){
                replaceFragment(new fragment_riderhost(email));
                dg.dismiss();
            }
        });
        logout.setOnClickListener(l->{
            Intent i = new Intent(this, account_views.class);
            startActivity(i);
            finish();
        });
        apply.setOnClickListener(l->{
            showApplyDialog();
            dg.dismiss();
        });


        dg.show();
        dg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dg.getWindow().getAttributes().windowAnimations = R.style.bottomsheetanim;
        dg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dg.getWindow().setGravity(Gravity.BOTTOM);

        Animation rotationin = AnimationUtils.loadAnimation(this, R.anim.rotate_paw_in);
        paw.startAnimation(rotationin);


        dg.setOnDismissListener(ds->{
            Animation rotation = AnimationUtils.loadAnimation(dashboard.this, R.anim.rotate_paw_out);
            paw.startAnimation(rotation);
        });
    }
    private byte[] convertUriToByteArray(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String readTxt() {
        StringBuilder c = new StringBuilder();

        try {
            File file = new File(getApplicationContext().getFilesDir(), "email.txt");

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
    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.changeView, fragment);
        ft.commit();
    }
    @Override
    public void onDatasetChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.changeView);
        if (fragment instanceof fragment_dashboard) {
            ((fragment_dashboard) fragment).refreshData();
        }
    }

}