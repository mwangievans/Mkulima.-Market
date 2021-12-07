package com.example.mkulima.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopItem implements Parcelable {
    private String title, description, price, imageUrl, dateAdded;
    private int stock;

    public ShopItem(String title, String description, String price, String imageUrl, String dateAdded, int stock) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.dateAdded = dateAdded;
        this.stock = stock;
    }

    protected ShopItem(Parcel in) {
        title = in.readString();
        description = in.readString();
        price = in.readString();
        imageUrl = in.readString();
        dateAdded = in.readString();
        stock = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(imageUrl);
        dest.writeString(dateAdded);
        dest.writeInt(stock);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopItem> CREATOR = new Creator<ShopItem>() {
        @Override
        public ShopItem createFromParcel(Parcel in) {
            return new ShopItem(in);
        }

        @Override
        public ShopItem[] newArray(int size) {
            return new ShopItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
