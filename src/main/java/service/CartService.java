package service;


import dto.CartItemDto;
import entity.Cart;
import entity.CartItem;
import manager.SingletonManager;
import repository.CartItemRepository;
import repository.CartRepository;

public class CartService {

    private final CartRepository cartRepository = SingletonManager.getBean(CartRepository.class);
    private final CartItemRepository cartItemRepository = SingletonManager.getBean(CartItemRepository.class);

    public void addItemToCart(CartItemDto cartItemDto) throws Exception {
        Cart cart = cartRepository.findActiveCart();

        if (cart == null) {
            cart = new Cart();
            cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem(cart, cartItemDto.getProductId(), cartItemDto.getQuantity(), cartItemDto.getPrice());
        cartItemRepository.save(cartItem);
    }

    public void confirmCart(int cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId);

        if (cart == null) {
            throw new Exception("Cart not found");
        }

        cart.setConfirmed(true);
        cartRepository.update(cart);
    }
}
