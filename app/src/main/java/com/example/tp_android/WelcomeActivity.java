package com.example.tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button seeProductsButton = findViewById(R.id.seeProductsButton);
        seeProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the ProductsActivity when the button is clicked
                Intent intent = new Intent(WelcomeActivity.this, Product.class);
                startActivity(intent);
            }
        });
    }
}
