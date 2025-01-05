package service;

import entity.Product;
import manager.SingletonManager;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository = SingletonManager.getBean(ProductRepositoryImpl.class);

    public List<Product> findAllByProductLike(String name, int supplierId) {
        return productRepository.findAllByProductLike(name, supplierId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(int productId, Product product) {
        return productRepository.update(productId, product);
    }

    public List<Product> findAllByIdIn(List<Integer> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    public double calculatePrice(Product product, int amount) {
        double realPrice = product.getPrice() * amount;

        double realDiscount = realPrice * product.getDiscount();

        return Math.max(realPrice - realDiscount, 0);
    }

    public void reduceAmount(int id, int amount) {
        productRepository.updateAmount(id, amount);
    }
}
