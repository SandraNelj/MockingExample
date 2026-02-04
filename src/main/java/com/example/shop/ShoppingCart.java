package com.example.shop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.put(item, quantity);
    }
    public Map<Item, Integer> getItems() {
        return items;
    }
}
