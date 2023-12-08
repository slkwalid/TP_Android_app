package com.example.tp_android;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiFetcher extends AsyncTask<Void, Void, String> {

    private static final String TAG = ApiFetcher.class.getSimpleName();
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
