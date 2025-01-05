package repository;

import entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAllByProductLike(String name, int supplierId);

    Product save(Product product);

    Product update(int productId, Product product);

    List<Product> findAllByIdIn(List<Integer> productIds);

    void updateAmount(int id, int amount);
}
