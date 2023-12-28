package com.example.tp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        CartState items = new CartState(getApplicationContext());
        TextView tvTotalItems = findViewById(R.id.tvTotalItems);
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);

        tvTotalItems.setText("Total Items: " + items.getTotalItems());
        tvTotalAmount.setText("Total Amount: € " + items.getTotalAmount());

        RecyclerView recyclerView = findViewById(R.id.rvCartItems);

    }
    public void viewCart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        // Simply finish the current activity to go back
        finish();
    }

}
