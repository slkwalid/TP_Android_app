package com.example.tp_android;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FakeStoreApiService {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") long id);

    @GET("products/category/{category_id}")
    Call<List<Product>> getProductsByCategory(@Path("category_id") String categoryId);

}
