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
    public List<Product> findAllByProductLike(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String productLikePattern = "%%" + name + "%%";
            preparedStatement.setString(1, productLikePattern);

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
        String sql = "INSERT INTO product (name, quantity, price, discount, supplier_id) VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getDiscount());
            preparedStatement.setInt(5, product.getSupplierId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));

                        List<String> imageUrls = product.getImageUrlList();
                        if (imageUrls != null && !imageUrls.isEmpty()) {
                            String imageSql = "INSERT INTO product_images (product_id, image_url) VALUES (?, ?)";
                            try (PreparedStatement imageStatement = connection.prepareStatement(imageSql)) {
                                for (String imageUrl : imageUrls) {
                                    imageStatement.setInt(1, product.getId());
                                    imageStatement.setString(2, imageUrl);
                                    imageStatement.addBatch();
                                }
                                imageStatement.executeBatch();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
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

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        String imageSql = "SELECT image_url FROM product_images WHERE product_id = ?";

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setDiscount(resultSet.getDouble("discount"));
                product.setSupplierId(resultSet.getInt("supplier_id"));

                try (PreparedStatement imageStatement = connection.prepareStatement(imageSql)) {
                    imageStatement.setInt(1, product.getId());
                    try (ResultSet imageResultSet = imageStatement.executeQuery()) {
                        List<String> imageUrlList = new ArrayList<>();
                        while (imageResultSet.next()) {
                            imageUrlList.add(imageResultSet.getString("image_url"));
                        }
                        product.setImageUrlList(imageUrlList);
                    }
                }

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
