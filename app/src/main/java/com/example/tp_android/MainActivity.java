package com.example.tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Set up the "Go to Welcome Activity" button click listener
        Button goToWelcomeButton = findViewById(R.id.goToWelcomeButton);
        goToWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the WelcomeActivity
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        // Fetch products using FakeStoreRepository
        FakeStoreRepository.getProducts(new FakeStoreRepository.ProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                productList.clear();
                productList.addAll(products);
                populateListView();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(TAG, "Data not available");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void populateListView() {
        ProductAdapter adapter = new ProductAdapter(this, productList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
    /*
    private void parseJsonAndPopulateListView(String jsonResult) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Product product = new Product(jsonObject.getLong("id"),
                        jsonObject.getString("title"),
                        jsonObject.getDouble("price"),
                        jsonObject.getString("description"),
                        jsonObject.getString("image")
                );
                productList.add(product);
            }
            // Update ListView through the adapter
            ProductAdapter adapter = new ProductAdapter(this, productList);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
            // Sample static data (for testing purposes)
            String[] products = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6"};
            double[] prices = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0};

            // Populate the list view with static data
            populateListView(products, prices);
            // Handle JSON parsing error
        }
    }

    private void populateListView(String[] products, double[] prices) {
        for (int i = 0; i < products.length; i++) {
            Product product = new Product((long) i,products[i], prices[i], "This is a description");
            productList.add(product);
        }

        // Populate the list view with products
        ProductAdapter adapter = new ProductAdapter(this, productList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

     */
}
