package repository;

import entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAllByProductLike(int productLikeId);

    Product save(Product product);

    Product update(int productId, Product product);
}
