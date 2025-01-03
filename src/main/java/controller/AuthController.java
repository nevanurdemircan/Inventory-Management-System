package controller;

import com.google.gson.Gson;
import dto.LoginDto;
import dto.RegisterDto;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.AuthService;

import java.io.IOException;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {
    private AuthService authService = new AuthService();
    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();

        try {
            if (path.equals("/register")) {
                handleRegister(req, resp);
            } else if (path.equals("/login")) {
                handleLogin(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Invalid endpoint.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private Users handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        RegisterDto registerDto = gson.fromJson(req.getReader(), RegisterDto.class);

        Users user = new Users();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setType(registerDto.getType());

        return authService.register(registerDto);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        LoginDto loginDto = gson.fromJson(req.getReader(), LoginDto.class);

        try {
            Users user = authService.login(loginDto);

            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"Invalid email or password.\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Login successful\",\"userId\":" + user.getId() + "}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
