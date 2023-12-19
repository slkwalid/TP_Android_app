package com.example.tp_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Retrieve the Product object as Parcelable
        Product product = getIntent().getParcelableExtra("product");

        ImageView productImageView = findViewById(R.id.productImageView);
        TextView productNameTextView = findViewById(R.id.productNameTextView);
        TextView productPriceTextView = findViewById(R.id.productPriceTextView);
        TextView productDescriptionTextView = findViewById(R.id.productDescriptionTextView);

        // Check for null to avoid NullPointerException
        if (product != null) {
            productNameTextView.setText(product.getTitle());
            productPriceTextView.setText(String.format("€ %s", product.getPrice()));
            productDescriptionTextView.setText(product.getDescription());

            String imageUrl = product.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(productImageView);
            } else {
                Toast.makeText(this, "Product details not available.", Toast.LENGTH_LONG).show();
                // Optionally, set text in your TextViews to indicate no data is available
                productNameTextView.setText("No product name available");
                productPriceTextView.setText("No price available");
                productDescriptionTextView.setText("No description available");
            }
        } else {
            Toast.makeText(this, "Product details not available.", Toast.LENGTH_LONG).show();
            // Optionally, set text in your TextViews to indicate no data is available
            productNameTextView.setText("No product name available");
            productPriceTextView.setText("No price available");
            productDescriptionTextView.setText("No description available");
        }

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());
    }
}
