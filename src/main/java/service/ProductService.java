package service;

import entity.Product;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;

import java.util.List;

public class ProductService {
    ProductRepository productRepository = new ProductRepositoryImpl();

    public List<Product> findAllByProductLike(int productLikeId) {
        return productRepository.findAllByProductLike(productLikeId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(int productId, Product product) {
        return productRepository.update(productId, product);
    }
}
