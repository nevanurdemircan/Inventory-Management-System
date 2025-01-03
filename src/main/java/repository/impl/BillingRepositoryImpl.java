package repository.impl;

import entity.Bill;
import repository.BillingRepository;
import repository.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillingRepositoryImpl implements BillingRepository {
    @Override
    public List<Bill> findAlBySupplierId(int supplierId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE supplier_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, supplierId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = new Bill();
                    bill.setId(resultSet.getInt("id"));
                    bill.setRetailerId(resultSet.getInt("retailer_id"));
                    bill.setTotalPrice(resultSet.getDouble("total_price"));
                    bill.setDate(resultSet.getDate("date"));
                    bill.setSupplierId(resultSet.getInt("supplier_id"));
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }

    @Override
    public Bill save(Bill bill) {
        String sql = "INSERT INTO bill (retailer_id, supplier_id, total_price, date) VALUES (?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, bill.getRetailerId());
            preparedStatement.setInt(2, bill.getSupplierId());
            preparedStatement.setDouble(3, bill.getTotalPrice());
            preparedStatement.setDate(4, new Date(bill.getDate().getTime()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bill.setId(generatedKeys.getInt(1));
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
        return bill;
    }
}
