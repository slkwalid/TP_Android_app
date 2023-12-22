package com.example.tp_android;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products = new HashMap<>();
    private static final String CART_PREFERENCES = "cartPreferences";
    private static final String CART_KEY = "cart";
    private Context context;

    public Cart(Context context) {
        this.context = context;
        loadCart();
    }

    public void addProduct(Product product) {
        int quantity = products.containsKey(product) ? products.get(product) : 0;
        products.put(product, quantity + 1);
        saveCart();
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            int quantity = products.get(product);
            if (quantity > 1) {
                products.put(product, quantity - 1);
            } else {
                products.remove(product);
            }
            saveCart();
        }
    }

    public int getTotalItems() {
        int total = 0;
        for (int quantity : products.values()) {
            total += quantity;
        }
        return total;
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    private void saveCart() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(products);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    private void loadCart() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, null);
        Type type = new TypeToken<Map<Product, Integer>>() {}.getType();
        products = gson.fromJson(json, type);

        if (products == null) {
            products = new HashMap<>();
        }
    }
}
