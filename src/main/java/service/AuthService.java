package service;

import dto.LoginDto;
import dto.RegisterDto;
import entity.Users;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

public class AuthService {
    private UserRepository userRepository = new UserRepositoryImpl();


    public Users register(RegisterDto registerDto) {
        Users user = new Users();
        user.setId(registerDto.getId());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setType(registerDto.getType());

        return userRepository.save(user);

    }

    public Users login(LoginDto loginDto) throws Exception {
        Users user = userRepository.findByEmail(loginDto.getEmail());

        if (user == null) {
            throw new Exception("Invalid email or password.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new Exception("Password is not set for this user.");
        }

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new Exception("Invalid email or password.");
        }

        return user;
    }
}


