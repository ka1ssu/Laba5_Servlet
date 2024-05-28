package com.example.laba5;

import org.json.simple.JSONObject;

public class Cars {
    private String brand;
    private String model;
    private int mileage;
    private String color;
    private int price;

    public Cars(String brand, String model, int mileage, String color, int price) {
        this.brand = brand;
        this.model = model;
        this.mileage = mileage;
        this.color = color;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public int getMileage() {
        return mileage;
    }
    public String getColor() {
        return color;
    }
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Cars [brand=" + brand + ", model=" + model + ", mileage=" + mileage + ", color=" + color + ", price=" + price + "]";
    }
}
