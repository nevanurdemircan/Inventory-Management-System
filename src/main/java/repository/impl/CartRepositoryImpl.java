package repository.impl;

import entity.Cart;
import repository.CartRepository;
import repository.DbConnection;
import java.sql.*;

public class CartRepositoryImpl implements CartRepository {

    private static Connection getConnection() throws SQLException {
        return DbConnection.getConnection();
    }

    public Cart findActiveCart() {
        String sql = "SELECT * FROM cart WHERE confirmed = false LIMIT 1";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return mapToCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public Cart findById(int cartId) {
        String sql = "SELECT * FROM cart WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapToCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Cart cart) {
        String sql = "INSERT INTO cart (confirmed) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, cart.isConfirmed());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cart cart) {
        String sql = "UPDATE cart SET confirmed = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, cart.isConfirmed());
            stmt.setInt(2, cart.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cart mapToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setConfirmed(rs.getBoolean("confirmed"));
        return cart;
    }
}
