package com.example.tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private CartState cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        cart = new CartState(getApplicationContext());
        // Retrieve the Product object as Parcelable
        product = getIntent().getParcelableExtra("product");

        ImageView productImageView = findViewById(R.id.productImageView);
        TextView productNameTextView = findViewById(R.id.productNameTextView);
        TextView productPriceTextView = findViewById(R.id.productPriceTextView);
        TextView productDescriptionTextView = findViewById(R.id.productDescriptionTextView);

        // Check for null to avoid NullPointerException
        if (product != null) {
            productNameTextView.setText(product.getTitle());
            productPriceTextView.setText(String.format("€ %s", product.getPrice()));
            productDescriptionTextView.setText(product.getDescription());

            productImageView.setImageResource(product.getImageResourceId());
            String imageUrl = product.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(productImageView);
            } else {
                Toast.makeText(this, "Product Image not available.", Toast.LENGTH_LONG).show();
                // Optionally, set text in your TextViews to indicate no data is available
/*                productNameTextView.setText("No product name available");
                productPriceTextView.setText("No price available");
                productDescriptionTextView.setText("No description available");
*/
            }
        } else {
            Toast.makeText(this, "Product details not available.", Toast.LENGTH_LONG).show();
            // Optionally, set text in your TextViews to indicate no data is available
            productNameTextView.setText("No product name available");
            productPriceTextView.setText("No price available");
            productDescriptionTextView.setText("No description available");
        }

        Button backButton = findViewById(R.id.backButton);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        Button buyButton = findViewById(R.id.buyButton);

        backButton.setOnClickListener(view -> finish());

        addToCartButton.setOnClickListener(this::addToCartFunction);

        buyButton.setOnClickListener(this::buyFunction);
    }

    public void addToCartFunction(View view){
        cart.addProduct(product);
        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
    }
    public void buyFunction(View view){
        cart.addProduct(product);
        Intent intent = new Intent(ProductDetailActivity.this, Cart.class);
        startActivity(intent);
    }
}
