package com.example.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ShoppingCartTest {

    @Test
    @DisplayName("Should add item to cart")
    void shouldAddItemToCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);

        cart.addItem(apple, 1);

        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().containsKey(apple));
    }

    @Test
    @DisplayName("Should remove item from cart")
    void shouldRemoveItemFromCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        cart.addItem(apple, 1);

        cart.removeItem(apple);
        assertFalse(cart.getItems().containsKey(apple));
    }

    @Test
    @DisplayName("Should calculate total price of items in cart")
    void shouldGetTotalPrice() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        Item banana = new Item("Banana", 5.0);
        Item pear = new Item("Pear", 2.0);

        cart.addItem(apple, 4);
        cart.addItem(banana, 3);
        cart.addItem(pear, 2);

        assertEquals(59.0, cart.calculateTotalPrice());
    }

    @Test
    @DisplayName("Should apply discount to item in cart")
    void shouldApplyDiscount() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0, 0.1);

        cart.addItem(apple, 2);

        assertEquals(18.0, cart.calculateWithDiscount());
    }
}
