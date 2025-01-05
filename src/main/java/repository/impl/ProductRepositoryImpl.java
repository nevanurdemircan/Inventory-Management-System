package repository.impl;

import entity.Product;
import repository.DbConnection;
import repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAllByProductLike(String name, int supplierId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ? AND supplier_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String productLikePattern = "%%" + name + "%%";
            preparedStatement.setString(1, productLikePattern);
            preparedStatement.setInt(2, supplierId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setDiscount(resultSet.getDouble("discount"));
                    product.setSupplierId(resultSet.getInt("supplier_id"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product (id, name, quantity, price, discount, supplier_id) VALUES (?,?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getDiscount());
            preparedStatement.setInt(6,product.getSupplierId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Product update(int productId, Product product) {
        String sql = "UPDATE product SET id =? , name=?, quantity=?, price=?, discount=?, supplier_id=? WHERE id=?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getDiscount());
            preparedStatement.setInt(6,product.getSupplierId());
            preparedStatement.setInt(7, productId);
            preparedStatement.executeUpdate();
            product.setId(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findAllByIdIn(List<Integer> productIds) {
        List<Product> products = new ArrayList<>();

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < productIds.size(); i++) {
            placeholders.append("?");
            if (i < productIds.size() - 1) {
                placeholders.append(", ");
            }
        }

        String query = "SELECT * FROM product WHERE retailer_id IN (" + placeholders + ")";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setDiscount(resultSet.getDouble("discount"));
                    product.setSupplierId(resultSet.getInt("supplier_id"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void updateAmount(int id, int amount) {
        String query = "update product set amount = ? where id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, amount);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
