package service;

import entity.Users;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    public Users save(Users user) {
        return userRepository.save(user);
    }

    public Users update(int userId, Users user) {
        return userRepository.update(userId, user);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(int userId) {
        return userRepository.findById(userId);
    }
}
