package com.example.bungee;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DBClass extends SQLiteOpenHelper {
    public static final String dbname = "AppDB";
    Calendar userCalendar;
    SimpleDateFormat dateFormat;


    Context context;

    //IF COUNT > 0 THEN ITS TRUE, ELSE FALSE
    public DBClass(@Nullable Context context) {
        super(context, dbname, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (email TEXT, user TEXT, password TEXT, type TEXT, isBanned INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS products (seller TEXT, itemName TEXT, itemPrice FLOAT, itemQuantity INTEGER, itemSold INTEGER, boughtCount INTEGER, itemImage BLOB, totalRating FLOAT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (whoOrdered TEXT, productName TEXT, productPrice TEXT, productQuantity INTEGER, seller TEXT, orderDate DATETIME DEFAULT CURRENT_TIMESTAMP, orderState TEXT, itemImage BLOB, orderID TEXT, deliveryRider TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (email TEXT, productName TEXT, productPrice FLOAT, productQuantity INTEGER, seller TEXT, orderDate DATETIME DEFAULT CURRENT_TIMESTAMP, productImage BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS requests (email TEXT, requestType TEXT, currentType TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void sendRequest(UserType ut){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", ut.getEmail());
        cv.put("requestType", ut.getRequestType());
        cv.put("currentType", ut.getCurrentType());
        db.insert("requests", null, cv);
    }
    public void removeFromReq(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("requests", "email = ?", new String[]{email});
    }
    @SuppressLint("Range")
    public List<UserType> retrieveRequests(String wotReq){
        SQLiteDatabase db = this.getReadableDatabase();
        List<UserType> type = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM requests WHERE requestType = ?", new String[]{wotReq});
        if(c.moveToFirst()){
             do{
                 String email = c.getString(c.getColumnIndex("email"));
                 String request = c.getString(c.getColumnIndex("requestType"));
                 String current = c.getString(c.getColumnIndex("currentType"));

                 UserType uta = new UserType(email, request, current);
                 type.add(uta);
             }while (c.moveToNext());
        }


        return type;
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveDeliveriesForRider(String riderName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'todeliver' and deliveryRider = ?", new String[]{riderName});

        List<OrderData> delivered = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                delivered.add(order);
            } while (c.moveToNext());
        }


        return delivered;
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveDeliveredForRider(String riderName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'delivered' and deliveryRider = ?", new String[]{riderName});

        List<OrderData> delivered = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                delivered.add(order);
            } while (c.moveToNext());
        }


        return delivered;
    }
    public void changeType(String type, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", type);

        db.update("users", cv, "email = ?", new String[]{email});
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveAllAcceptedForAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'accepted'", null);

        List<OrderData> todeliver = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                todeliver.add(order);
            } while (c.moveToNext());
        }


        return todeliver;
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveAllDeliveredForAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'todeliver'", null);

        List<OrderData> delivered = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                delivered.add(order);
            } while (c.moveToNext());
        }


        return delivered;
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveDeliveredAsUser(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        List<OrderData> l = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'delivered' AND whoOrdered = ?", new String[]{user});

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                l.add(order);
            } while (c.moveToNext());
        }

        return l;
    }
    @SuppressLint("Range")
    public List<OrderData> retrievePendingSeller(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'ordered' AND seller = ?", new String[]{user});

        List<OrderData> orders = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                orders.add(order);
            } while (c.moveToNext());
        }


        return orders;
    }
    @SuppressLint("Range")
    public List<OrderData> retrievePendingBuyer(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'ordered' AND whoOrdered = ?", new String[]{user});

        List<OrderData> orders = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                orders.add(order);
            } while (c.moveToNext());
        }


        return orders;
    }

    public void updateState(String email, String id, String seller, String action){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("orderState", action);

        String randomRider = null;
        if(action.equals("todeliver")) {
            randomRider = getRandomRiderEmail();
        }

        if (randomRider != null) {
            cv.put("deliveryRider", randomRider);
            db.update("orders", cv, "whoOrdered = ? AND orderID = ? AND seller = ? AND deliveryRider IS NULL", new String[]{email, id, seller});
        }
        else {
            db.update("orders", cv, "whoOrdered = ? AND orderID = ? AND seller = ?", new String[]{email, id, seller});
        }

    }

    @SuppressLint("Range")
    public String getRandomRiderEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String email = "";
        Cursor cursor = db.rawQuery("SELECT email FROM users WHERE type = 'rider' ORDER BY RANDOM() LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
            cursor.close();
        }

        return email;
    }
    public void deleteFromCart(String email, String name, String seller){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("cart", "email = ? AND productName = ? AND seller = ?", new String[]{email, name, seller});
    }
    @SuppressLint("Range")
    public String retrieveCurrentType(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM requests WHERE email = ?", new String[]{email});
        String currType = "";

        if(c.moveToFirst()){
            currType = c.getString(c.getColumnIndex("currentType"));
        }
        return currType;
    }
    @SuppressLint("Range")
    public List<OrderData> retrieveAcceptedSeller(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE (orderState = 'accepted' OR orderState = 'delivered') AND seller = ?", new String[]{user});

        List<OrderData> orders = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                orders.add(order);
            } while (c.moveToNext());
        }

        return orders;
    }

    @SuppressLint("Range")
    public List<OrderData> retrieveAcceptedBuyer(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE orderState = 'accepted' AND whoOrdered = ?", new String[]{user});

        List<OrderData> orders = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String email = c.getString(c.getColumnIndex("whoOrdered"));
                String productName = c.getString(c.getColumnIndex("productName"));
                Float productPrice = c.getFloat(c.getColumnIndex("productPrice"));
                int productQuantity = c.getInt(c.getColumnIndex("productQuantity"));
                String date = c.getString(c.getColumnIndex("orderDate"));
                byte[] img = c.getBlob(c.getColumnIndex("itemImage"));
                String seller = c.getString(c.getColumnIndex("seller"));
                String id = c.getString(c.getColumnIndex("orderID"));
                String rider = c.getString(c.getColumnIndex("deliveryRider"));
                OrderData order = new OrderData(email, productName, date, seller,productPrice, productQuantity, img, id, rider);
                orders.add(order);
            } while (c.moveToNext());
        }

        return orders;
    }

    public boolean addToOrders(OrderList od, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;

        for(int i = 0; i < od.selectedOrders.size(); i++){
            ContentValues cv = new ContentValues(); // Create new ContentValues object for each iteration

            cv.put("whoOrdered", user);
            cv.put("productName", od.selectedOrders.get(i).getProductName());
            cv.put("productPrice", od.selectedOrders.get(i).getProductPrice());
            cv.put("productQuantity", od.selectedOrders.get(i).getProductQuantity());
            cv.put("seller", od.selectedOrders.get(i).getSeller());
            cv.put("itemImage", od.selectedOrders.get(i).getImg());
            cv.put("orderState", "ordered");
            cv.put("orderID", String.valueOf(new Random().nextInt(10000) * new Random().nextInt(10000)));

            long result = db.insert("orders", null, cv);

            ContentValues updateValues = new ContentValues();
            updateValues.put("itemSold", "itemSold + " + od.selectedOrders.get(i).getProductQuantity());
            updateValues.put("itemQuantity", "itemQuantity - " + od.selectedOrders.get(i).getProductQuantity());
            updateValues.put("boughtCount", "boughtCount + 1");

            db.execSQL("UPDATE products SET "
                    + "itemSold = itemSold + " + od.selectedOrders.get(i).getProductQuantity() + ", "
                    + "itemQuantity = itemQuantity - " + od.selectedOrders.get(i).getProductQuantity() + ", "
                    + "boughtCount = boughtCount + 1 "
                    + "WHERE itemName = '" + od.selectedOrders.get(i).getProductName() + "'");

            if (result != -1) {
                success = true;
            }
        }
        return success;
    }



    public Boolean addUser(String user, String password, String email, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if (c.getCount() == 0) {
            cv.put("email", email);
            cv.put("user", user);
            cv.put("password", password);
            cv.put("type", type);
            cv.put("isBanned", 0);

            db.insert("users", null, cv);
        }

        int count = c.getCount();
        return count == 0;
    }

    public boolean checkCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        int count = cursor.getCount();
        return count > 0;
    }

    public boolean addProduct(String seller, String itemName, Float itemPrice, int itemQuantity, int itemSold, int boughtCount, byte[] itemImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM products WHERE itemName = ?", new String[]{itemName});

        if (c.getCount() == 0) {
            cv.put("seller", seller);
            cv.put("itemName", itemName);
            cv.put("itemPrice", itemPrice);
            cv.put("itemQuantity", itemQuantity);
            cv.put("itemSold", itemSold);
            cv.put("boughtCount", boughtCount);
            cv.put("itemImage", itemImage);

            db.insert("products", null, cv);
        }
        int count = c.getCount();
        c.close();
        c.close();
        return count == 0;
    }

    public List<ProductData> searchProduct(String itemName) {
        List<ProductData> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE itemName LIKE ?", new String[]{"%" + itemName + "%"});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int sellerIndex = cursor.getColumnIndex("seller");
                int itemNameIndex = cursor.getColumnIndex("itemName");
                int itemPriceIndex = cursor.getColumnIndex("itemPrice");
                int itemQuantityIndex = cursor.getColumnIndex("itemQuantity");
                int itemSoldIndex = cursor.getColumnIndex("itemSold");
                int boughtCountIndex = cursor.getColumnIndex("boughtCount");
                int itemImageIndex = cursor.getColumnIndex("itemImage");

                if (sellerIndex != -1 && itemNameIndex != -1 && itemPriceIndex != -1 &&
                        itemQuantityIndex != -1 && itemSoldIndex != -1 && boughtCountIndex != -1 &&
                        itemImageIndex != -1) {

                    String seller = cursor.getString(sellerIndex);
                    String itemNameValue = cursor.getString(itemNameIndex);
                    float itemPrice = cursor.getFloat(itemPriceIndex);
                    int itemQuantity = cursor.getInt(itemQuantityIndex);
                    int itemSold = cursor.getInt(itemSoldIndex);
                    int boughtCount = cursor.getInt(boughtCountIndex);
                    byte[] itemImage = cursor.getBlob(itemImageIndex);
                    @SuppressLint("Range") float ratign = cursor.getFloat(cursor.getColumnIndex("totalRating"));

                    result.add(new ProductData(seller, itemNameValue, itemPrice, itemQuantity, itemSold, boughtCount, itemImage, ratign));
                } else {
                    Log.e("DBClass", "Column index is -1 for one or more columns");
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    public String getType(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        String type = null;
        if (c.moveToFirst()) {
            int index = c.getColumnIndex("type");
            type = c.getString(index);
        }
        Log.d("TYPE", "Type is: " + type);
        return type;
    }
    public void addRating(String productName, Float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.execSQL("UPDATE products SET totalRating = totalRating + " + rating + " WHERE itemName = '" + productName + "'");
    }
    @SuppressLint("SimpleDateFormat")
    public boolean toCart(String email, String productName, Float productPrice, int productQuantity, String seller, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM cart WHERE email = ? AND productName = ?", new String[]{email, productName});

        if (c.getCount() == 0) {
            cv.put("email", email);
            cv.put("productName", productName);
            cv.put("productPrice", productPrice);
            cv.put("productQuantity", productQuantity);
            cv.put("seller", seller);
            cv.put("productImage", img);

            long result = db.insert("cart", null, cv);
            return result != -1;
        } else {
            c.moveToFirst();
            @SuppressLint("Range") int currentQuantity = c.getInt(c.getColumnIndex("productQuantity"));
            int updatedQuantity = currentQuantity + productQuantity;

            cv.put("productQuantity", updatedQuantity);

            int rowsAffected = db.update("cart", cv, "email = ? AND productName = ?", new String[]{email, productName});
            return rowsAffected > 0;
        }
    }

    @SuppressLint("Range")
    public List<AccountCart> getCart(String email) {
        List<AccountCart> pd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE email = ?", new String[]{email});

        if (c != null && c.moveToFirst()) {
            do {
                String pname = c.getString(c.getColumnIndex("productName"));
                float pprice = c.getFloat(c.getColumnIndex("productPrice"));
                int pquantity = c.getInt(c.getColumnIndex("productQuantity"));
                String seller = c.getString(c.getColumnIndex("seller"));
                byte[] itemImage = c.getBlob(c.getColumnIndex("productImage"));

                AccountCart ac = new AccountCart(pname, seller, pprice, pquantity, itemImage);
                pd.add(ac);
            } while (c.moveToNext());
        } else {
            Log.d("getCart", "No items found in the cart for email: " + email);
        }

        return pd;
    }


    public Uri getImg(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM products WHERE productName = ?", new String[]{productName});
        int uriIndex = c.getColumnIndex("itemImage");

        return Uri.parse(c.getString(uriIndex));
    }
    @SuppressLint("Range")
    public String getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT user FROM users WHERE email = ?", new String[]{email});

        String user = null;

        if (c.moveToFirst()) {
            user = c.getString(c.getColumnIndex("user"));
        }

        return user;
    }

    public List<ProductData> getAllProducts() {
        List<ProductData> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                int sellerIndex = cursor.getColumnIndex("seller");
                int itemNameIndex = cursor.getColumnIndex("itemName");
                int itemPriceIndex = cursor.getColumnIndex("itemPrice");
                int itemQuantityIndex = cursor.getColumnIndex("itemQuantity");
                int itemSoldIndex = cursor.getColumnIndex("itemSold");
                int boughtCountIndex = cursor.getColumnIndex("boughtCount");
                int itemImageIndex = cursor.getColumnIndex("itemImage");

                if (sellerIndex != -1 && itemNameIndex != -1 && itemPriceIndex != -1 &&
                        itemQuantityIndex != -1 && itemSoldIndex != -1 && boughtCountIndex != -1 &&
                        itemImageIndex != -1) {

                    String seller = cursor.getString(sellerIndex);
                    String itemName = cursor.getString(itemNameIndex);
                    float itemPrice = cursor.getFloat(itemPriceIndex);
                    int itemQuantity = cursor.getInt(itemQuantityIndex);
                    int itemSold = cursor.getInt(itemSoldIndex);
                    int boughtCount = cursor.getInt(boughtCountIndex);
                    byte[] itemImage = cursor.getBlob(itemImageIndex);
                    @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("totalRating"));

                    productList.add(new ProductData(seller, itemName, itemPrice, itemQuantity, itemSold, boughtCount, itemImage, rating));
                } else {
                    Log.e("DBClass", "Column index is -1 for one or more columns");
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return productList;
    }

}
