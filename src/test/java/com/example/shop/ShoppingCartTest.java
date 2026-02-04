package com.example.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ShoppingCartTest {

    @Test
    void shouldAddItemToCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);

        cart.addItem(apple, 1);

        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().containsKey(apple));
    }

    @Test
    void shouldRemoveItemFromCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        cart.addItem(apple, 1);

        cart.removeItem(apple);
        assertFalse(cart.getItems().containsKey(apple));
    }
}
