package repository.impl;

import entity.BilledProduct;
import repository.BilledProductRepository;
import repository.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BilledProductRepositoryImpl implements BilledProductRepository {

    @Override
    public BilledProduct save(BilledProduct billedProduct) {
        String sql = "INSERT INTO billed_product (product_id, current_price, amount, total_price, bill_id) VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ){

            preparedStatement.setInt(1, billedProduct.getProductId());
            preparedStatement.setDouble(2, billedProduct.getCurrentPrice());
            preparedStatement.setInt(3, billedProduct.getAmount());
            preparedStatement.setDouble(4, billedProduct.getTotalPrice());
            preparedStatement.setInt(5, billedProduct.getBillId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        billedProduct.setId(generatedKeys.getInt("id"));
                    }
                }
            } else {
                throw new SQLException("Inserting bill failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println("Error during saving bill: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error while saving bill: " + e.getMessage());
        }
        return billedProduct;
    }

    @Override
    public List<BilledProduct> findAllByBillId(int billId) {
        List<BilledProduct> billedProducts = new ArrayList<>();
        String sql = "SELECT * FROM billed_product WHERE bill_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            preparedStatement.setInt(1, billId);

            while (resultSet.next()) {
                BilledProduct billedProduct = new BilledProduct();
                billedProduct.setId(resultSet.getInt("id"));
                billedProduct.setBillId(resultSet.getInt("bill_id"));
                billedProduct.setProductId(resultSet.getInt("product_id"));
                billedProduct.setCurrentPrice(resultSet.getDouble("current_price"));
                billedProduct.setAmount(resultSet.getInt("amount"));
                billedProduct.setTotalPrice(resultSet.getDouble("total_price"));

                billedProducts.add(billedProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billedProducts;
    }
}
