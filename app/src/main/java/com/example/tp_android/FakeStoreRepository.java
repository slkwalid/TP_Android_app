package com.example.tp_android;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FakeStoreRepository {
    public interface ProductsCallback {
        void onProductsLoaded(List<Product> products);
        void onDataNotAvailable();
    }

    public static void getProducts(final ProductsCallback callback) {
        FakeStoreApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(FakeStoreApiService.class);

        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onProductsLoaded(response.body());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    public static void getProductById(long id, final ProductsCallback callback) {
        FakeStoreApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(FakeStoreApiService.class);

        Call<Product> call = apiService.getProductById(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> singleProductList = new ArrayList<>();
                    singleProductList.add(response.body());
                    callback.onProductsLoaded(singleProductList);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    public static void getProductsByCategory(String categoryId, final ProductsCallback callback) {
        FakeStoreApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(FakeStoreApiService.class);

        Call<List<Product>> call = apiService.getProductsByCategory(categoryId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onProductsLoaded(response.body());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
