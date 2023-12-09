package com.example.tp_android;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private final Long id;
    private final String name;
    private final Double price;
    private final String description;
    private final int imageResourceId;

    public Product(Long id, String name, Double price, String description, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    public Product(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = R.drawable.nofile_yet;
    }
    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        imageResourceId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeInt(imageResourceId);
    }
}
