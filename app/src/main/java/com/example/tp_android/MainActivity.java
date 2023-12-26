package com.example.tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Spinner categorySpinner;
    private ListView listView;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    private CartState cart;
    private static final String TAG = MainActivity.class.getSimpleName();

    private final FakeStoreRepository.ProductsCallback callback =
            new FakeStoreRepository.ProductsCallback() {
                @Override
                public void onProductsLoaded(List<Product> products) {
                    productList.clear();
                    productList.addAll(products);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onDataNotAvailable() {
                    Log.e(TAG, "Data not available");
                    progressBar.setVisibility(View.GONE);
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cart = new CartState(getApplicationContext());

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);
        categorySpinner = findViewById(R.id.categorySpinner);
        adapter = new ProductAdapter(this, productList, this.cart);
        listView.setAdapter(adapter);

        setupCategorySpinner();
        setupWelcomeButton();
    }

    private void setupCategorySpinner() {
        String[] categories = {"all","electronics", "jewelery", "men's clothing", "women's clothing"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                if (selectedCategory.equals("all")) getProducts();
                else fetchProductsByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void getProducts() {
        progressBar.setVisibility(View.VISIBLE);
        FakeStoreRepository.getProducts(callback);
    }

    private void fetchProductsByCategory(String category) {
        progressBar.setVisibility(View.VISIBLE);
        FakeStoreRepository.getProductsByCategory(category, callback);
    }

    private void setupWelcomeButton() {
        Button goToWelcomeButton = findViewById(R.id.goToWelcomeButton);
        goToWelcomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });
    }
}