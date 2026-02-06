package com.example.shop;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    @DisplayName("Should add item to cart")
    void shouldAddItemToCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);

        cart.addItem(apple, 1);

        assertThat(cart.getItems())
                .size()
                .isEqualTo(1);

        assertThat(cart.getItems())
                .containsKey(apple);
    }

    @Test
    @DisplayName("Should remove item from cart")
    void shouldRemoveItemFromCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        cart.addItem(apple, 1);
        cart.removeItem(apple);

        assertThat(cart.getItems())
                .isEmpty();
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

        assertThat(cart.calculateTotalPrice())
                .isEqualTo(59.0);
    }

    @Test
    @DisplayName("Should apply discount to item in cart")
    void shouldApplyDiscount() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0, 0.1);

        cart.addItem(apple, 2);

        assertThat(cart.calculateWithDiscount())
                .isEqualTo(18.0);
    }

    @Test
    @DisplayName("Should update quantity in cart")
    void shouldUpdateQuantity() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        cart.addItem(apple, 1);
        cart.addItem(apple, 2);

        assertThat(cart.getItems())
                .containsEntry(apple, 3);
    }

    @Test
    void totalPriceOfEmptyCartShouldBeZero() {
        ShoppingCart cart = new ShoppingCart();

        assertThat(cart.calculateTotalPrice()).isEqualTo(0.0);
    }

    @Test
    void removingNonExistingItemShouldNotThrow() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);

        assertThatCode(() -> cart.removeItem(apple))
                .doesNotThrowAnyException();
    }

    @Test
    void addingZeroQuantityShouldNotChangeCart() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);
        cart.addItem(apple, 0);

        assertThat(cart.getItems())
                .isEmpty();
    }

    @Test
    void addingNegativeQuantityShouldThrow() {
        ShoppingCart cart = new ShoppingCart();
        Item apple = new Item("Apple", 10.0);

        assertThatThrownBy(() -> cart.addItem(apple, -1))
        .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Quantity cannot be negative");
    }
}
