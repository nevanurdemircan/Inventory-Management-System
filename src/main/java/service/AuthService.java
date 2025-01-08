package service;

import dto.LoginDto;
import dto.RegisterDto;
import entity.Users;
import exception.AuthException;
import manager.SingletonManager;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import java.util.Objects;

public class AuthService {
    private final UserRepository userRepository = SingletonManager.getBean(UserRepositoryImpl.class);

    public Users register(RegisterDto registerDto) {
        Users user = new Users();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setType(registerDto.getType());

        return userRepository.save(user);
    }

    public Users login(LoginDto loginDto) {
        Users user = userRepository.findByEmail(loginDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthException("Invalid email or password.");
        }

        if (Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new AuthException("Password is not set for this user.");
        }

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new AuthException("Invalid email or password.");
        }

        return user;
    }
}


