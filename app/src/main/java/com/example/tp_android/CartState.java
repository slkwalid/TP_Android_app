package com.example.tp_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CartState {
    private Map<Long, Integer> products = new HashMap<>();
    private Map<Long, Double> prices = new HashMap<>();
    private static final String CART_PREFERENCES = "cartPreferences";
    private static final String CART_KEY_COUNT = "cart_count";
    private static final String CART_KEY_PRICE = "cart_price";
    private static final String TAG = "CartState"; // Etiqueta para los registros
    private Context context;

    public CartState(Context context) {
        this.context = context;
        loadCart();
    }

    public void addProduct(Product product) {
        if (product == null) {
            Log.d(TAG, "Intento de agregar un producto nulo.");
            return;
        }
        int quantity = products.getOrDefault(product.getId(),  0);
        products.put(product.getId(),quantity + 1);
        prices.put(product.getId(), product.getPrice());
        Log.d(TAG, "Producto agregado: " + product.getTitle() + ", Cantidad: " + (quantity + 1) + ", Precio: EUR " + product.getPrice());
        saveCart();
    }

    public void removeProduct(Product product) {
        if (!products.containsKey(product.getId())) {
            Log.d(TAG, "Intento de eliminar un producto que no está en el carrito: " + product.getTitle());
            return;
        }
        int quantity = products.get(product);
        if (quantity > 1) {
            products.put(product.getId(), quantity - 1);
            Log.d(TAG, "Disminuida la cantidad de producto: " + product.getTitle() + ", Nueva Cantidad: " + (quantity - 1));
        } else {
            products.remove(product.getId());
            prices.remove(product.getId());
            Log.d(TAG, "Producto eliminado del carrito: " + product.getTitle());
        }
        saveCart();
    }

    public int getTotalItems() {
        int total = 0;
        for (int quantity : products.values()) {
            total += quantity;
        }
        Log.d(TAG, "Total de ítems en el carrito: " + total);
        return total;
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            total += prices.get(entry.getKey()) * entry.getValue();
        }
        Log.d(TAG, "Cantidad total en el carrito: $" + total);
        return total;
    }

    private void saveCart() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(products);
        Log.d(TAG, json);
        editor.putString(CART_KEY_COUNT, json);
        json = gson.toJson(prices);
        Log.d(TAG, json);
        editor.putString(CART_KEY_PRICE, json);
        editor.apply();
        Log.d(TAG, "Carrito guardado en SharedPreferences.");
    }

    private void loadCart() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY_COUNT, null);
        if (json != null) Log.d(TAG, json);
        Type type = new TypeToken<Map<Long, Integer>>() {}.getType();

        try {
            Map<Long, Integer> tempProducts = gson.fromJson(json, type);
            if (tempProducts != null) {
                products = tempProducts;
            }
            Log.d(TAG, "Carrito cargado desde SharedPreferences.");
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar el carrito desde SharedPreferences.", e);
            products = new HashMap<>();
        }

        json = sharedPreferences.getString(CART_KEY_PRICE, null);
        if (json != null) Log.d(TAG, json);
        type = new TypeToken<Map<Long, Double>>() {}.getType();

        try {
            Map<Long, Double> tempProducts = gson.fromJson(json, type);
            if (tempProducts != null) {
                prices = tempProducts;
            }
            Log.d(TAG, "Carrito cargado desde SharedPreferences.");
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar el carrito desde SharedPreferences.", e);
            prices = new HashMap<>();
        }
    }
}
