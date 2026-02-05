package com.example.shop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        items.merge(item, quantity, Integer::sum);
    }
    public Map<Item, Integer> getItems() {
        return items;
    }
    public void removeItem(Item item) {
        items.remove(item);
    }

    public double calculateTotalPrice() {
        return items.entrySet()
                .stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public double calculateWithDiscount() {
        return items.entrySet().stream()
                .mapToDouble(e-> e.getKey().getPrice() * e.getValue() *
                        (1 - e.getKey().getDiscount())).sum();
    }
}
