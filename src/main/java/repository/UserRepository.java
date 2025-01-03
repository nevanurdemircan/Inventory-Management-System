package repository;

import entity.Users;

import java.util.List;

public interface UserRepository {
    Users save(Users users);

    Users update(int userId, Users user);

    List<Users> findAll();

    Users findById(int userId);

    Users findByEmail(String email);
}
