package com.example.tp_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button seeProductsButton = findViewById(R.id.seeProductsButton);
        seeProductsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Checking connection", Toast.LENGTH_SHORT).show();
            if (!isNetworkAvailable()) {
                // No connection
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show();
                // Or use an AlertDialog for a more prominent message
            } else {
                // Open the ProductsActivity when the button is clicked
                Intent intent;
                intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
