package repository;

import entity.Cart;

public interface CartRepository {
    Cart findActiveCart();

    Cart findById(int cartId);

    void save(Cart cart);

    void update(Cart cart);
}
