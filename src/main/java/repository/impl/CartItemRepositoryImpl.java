package repository.impl;

import entity.CartItem;
import repository.CartItemRepository;
import repository.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartItemRepositoryImpl implements CartItemRepository {

    private static Connection getConnection() throws SQLException {
        return DbConnection.getConnection();
    }

    public void save(CartItem cartItem) {
        String sql = "INSERT INTO cart_item (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setDouble(4, cartItem.getPrice());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(CartItem cartItem) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getId());
            stmt.setInt(2, cartItem.getProductId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
