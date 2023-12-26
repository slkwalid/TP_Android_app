package com.example.tp_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private CartState cart;

    public ProductAdapter(Context context, List<Product> products, CartState cart) {
        super(context, 0, products);
        this.cart = cart;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_product, parent, false);
        }

        Product currentProduct = getItem(position);

        ImageView productImageView = listItemView.findViewById(R.id.productImage);
        TextView productNameTextView = listItemView.findViewById(R.id.productName);
        TextView productPriceTextView = listItemView.findViewById(R.id.productPrice);
        TextView productDescriptionTextView = listItemView.findViewById(R.id.productDescription);
        Button addToCartButton = listItemView.findViewById(R.id.addToCartButton);

        // Load image, name, price, and description into the views
        if (currentProduct != null) {
            productImageView.setImageResource(currentProduct.getImageResourceId());
            String img = currentProduct.getImage();
            if (img != null  && !img.isEmpty()) {Picasso.get().load(img).into(productImageView);}
            productNameTextView.setText(currentProduct.getTitle());
            productPriceTextView.setText("€ " + currentProduct.getPrice());
            productDescriptionTextView.setText(currentProduct.getDescription());

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart.addProduct(currentProduct);
                    Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                // Correctly pass the Parcelable Product object
                intent.putExtra("product", getItem(position));
                context.startActivity(intent);
            }
        });


        return listItemView;
    }
}