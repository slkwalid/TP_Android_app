package com.example.tp_android;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private final Long id;
    private final String title;
    private final Double price;
    private final String description;
    private final int imageResourceId;
    private final String image;

    public Product(Long id, String title, Double price, String description, int imageResourceId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.image = null;
    }
    public Product(Long id, String title, Double price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResourceId = R.drawable.nofile_yet;
        this.image = null;
    }

    public Product(Long id, String title, Double price, String description, String image)
    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResourceId = R.drawable.nofile_yet;
        this.image = image;
    }

    protected Product(Parcel in) {
        id = in.readLong();
        title = in.readString();
        price = in.readDouble();
        description = in.readString();
        imageResourceId = in.readInt();
        image = in.readString();
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

    public String getTitle() {
        return title;
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

    public String getImage(){ return this.image; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeInt(imageResourceId);
        parcel.writeString(image);
    }
}
