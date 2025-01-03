package repository.impl;

import entity.Users;
import enums.UserType;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static repository.DbConnection.getConnection;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public Users save(Users users) {
        String SQL = "INSERT INTO users (id, type, name, email, password, phone) VALUES (?,?,?,?,?,?)";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, users.getId());
            preparedStatement.setString(2, users.getType().getValue());
            preparedStatement.setString(3, users.getName());
            preparedStatement.setString(4, users.getEmail());
            preparedStatement.setString(5, users.getPassword());
            preparedStatement.setString(6, users.getPhone());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        users.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Users update(int userId, Users user) {
        String sql = "UPDATE users SET id =? , type=?, name=?, email=?, password=?, phone=? WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getType().getValue());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.executeUpdate();
            user.setId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Users> findAll() {
        String sql = "SELECT * FROM users";
        List<Users> usersList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setType(UserType.valueOf(resultSet.getString("type")));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                usersList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public Users findById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Users user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setType(UserType.valueOf(resultSet.getString("type")));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Users findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getObject("password") != null ? resultSet.getString("password") : "";

                System.out.println("Password retrieved: " + password);

                if (password == null || password.isEmpty()) {
                    System.err.println("Password is not set for this user.");
                    throw new SQLException("Password is not set for the user.");
                }

                return new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        password
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
