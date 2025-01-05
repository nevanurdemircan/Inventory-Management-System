package service;

import entity.Users;
import manager.SingletonManager;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = SingletonManager.getBean(UserRepositoryImpl.class);

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
