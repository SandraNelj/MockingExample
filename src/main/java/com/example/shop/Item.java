package com.example.shop;

import java.util.Objects;

public class Item {

    private String name;
    private double price;
    private double discount;

    public Item(String name, double price) {
        this(name, price, 0.0);
    }

    public Item(String name, double price, double discount) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (discount < 0 || discount > 1) {
            throw new IllegalArgumentException("Discount must be between 0 and 1");
        }
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 &&
                Objects.equals(name, item.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

}
