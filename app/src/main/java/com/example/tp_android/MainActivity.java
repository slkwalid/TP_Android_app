package com.example.tp_android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
/*
        // Sample static data (for testing purposes)
        String[] products = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6"};
        double[] prices = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0};

        // Populate the list view with static data
        populateListView(products, prices);
 */
        ApiFetcher fetcher = new ApiFetcher();
        fetcher.setOnDataFetchedListener(result -> {
            // Handle the result here (parse JSON and update UI)
            parseJsonAndPopulateListView(result);
            progressBar.setVisibility(View.GONE); // Hide ProgressBar when done
        });
        fetcher.execute();
    }

    private void parseJsonAndPopulateListView(String jsonResult) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Product product = new Product(jsonObject.getLong("id"),
                        jsonObject.getString("title"),
                        jsonObject.getDouble("price"),
                        jsonObject.getString("description")
                );
                productList.add(product);
            }
            // Update ListView through the adapter
            ProductAdapter adapter = new ProductAdapter(this, productList);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
            // Handle JSON parsing error
        }
    }

    private void populateListView(String[] products, double[] prices) {
        for (int i = 0; i < products.length; i++) {
            Product product = new Product((long) i,"Example Product", 19.99, "This is a description", R.drawable.logo_souk);
            productList.add(product);
        }

        // Populate the list view with products
        ProductAdapter adapter = new ProductAdapter(this, productList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    private static class ApiFetcher extends AsyncTask<Void, Void, String> {

        private OnDataFetchedListener listener;

        public interface OnDataFetchedListener {
            void onDataFetched(String result);
        }

        public void setOnDataFetchedListener(OnDataFetchedListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String apiUrl = "https://fakestoreapi.com/products";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultJson = null;

            try {
                URL url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Check the HTTP response code
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();

                    if (inputStream == null) {
                        return null; // Nothing to do if the InputStream is empty.
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line).append("\n");
                    }

                    if (buffer.length() == 0) {
                        return null; // Stream was empty. No point in parsing.
                    }

                    resultJson = buffer.toString();
                } else {
                    Log.e(TAG, "HTTP response code: " + responseCode);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error fetching data", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            return resultJson;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (listener != null) {
                listener.onDataFetched(result);
            }
        }
    }
}
