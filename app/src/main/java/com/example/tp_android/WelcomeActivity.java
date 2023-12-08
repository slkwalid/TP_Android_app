package com.example.tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button seeProductsButton = findViewById(R.id.seeProductsButton);
        seeProductsButton.setOnClickListener(v -> {
            // Open the ProductsActivity when the button is clicked
            Intent intent;
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
