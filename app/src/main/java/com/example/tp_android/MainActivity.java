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
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);
        categorySpinner = findViewById(R.id.categorySpinner);

        setupCategorySpinner();
        setupWelcomeButton();

        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);
    }

    private void setupCategorySpinner() {
        // TODO: Replace with actual category IDs or names
        String[] categories = {"jewelery", "men's clothing", "women's clothing"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                fetchProductsByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void fetchProductsByCategory(String category) {
        progressBar.setVisibility(View.VISIBLE);
        FakeStoreRepository.getProductsByCategory(category, new FakeStoreRepository.ProductsCallback() {
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
        });
    }

    private void setupWelcomeButton() {
        Button goToWelcomeButton = findViewById(R.id.goToWelcomeButton);
        goToWelcomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });
    }
}

