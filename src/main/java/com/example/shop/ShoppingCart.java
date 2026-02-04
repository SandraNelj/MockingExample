package com.example.shop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.merge(item, quantity, Integer::sum);
    }
    public Map<Item, Integer> getItems() {
        return items;
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
}
