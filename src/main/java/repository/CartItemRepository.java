package repository;

import entity.CartItem;

public interface CartItemRepository {
    void save(CartItem cartItem);
}
