package controller;

import dto.LoginDto;
import dto.RegisterDto;
import entity.Users;
import enums.UserType;
import exception.handler.AppExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.AuthService;
import util.JsonResponse;

import java.io.IOException;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {
    private final AuthService authService = SingletonManager.getBean(AuthService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        try {
            if (path.equals("/register")) {
                handleRegister(req, resp);
            } else if (path.equals("/login")) {
                handleLogin(req, resp);
            } else {
                JsonResponse.send(resp, "", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        UserType type = UserType.valueOf(req.getParameter("type"));
        if (type == null) {
            throw new IllegalArgumentException("Invalid or missing user type.");
        }
        RegisterDto registerDto = new RegisterDto(email, password, name, type, phone);
        Users user = authService.register(registerDto);

        if (user != null) {
            resp.sendRedirect("/login.jsp");
        } else {
            JsonResponse.send(resp, "Registration failed", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        LoginDto loginDto = new LoginDto(email, password);
        Users user = authService.login(loginDto);
        if (user != null) {
            UserType userType = user.getType();

            req.getSession().setAttribute("user", user);

            String redirectPath = req.getContextPath();
            if ("RETAILER".equalsIgnoreCase(String.valueOf(userType))) {
                resp.sendRedirect(redirectPath + "/retailerdashboard.jsp");
            } else if ("SUPPLIER".equalsIgnoreCase(String.valueOf(userType))) {
                resp.sendRedirect(redirectPath + "/supplierdashboard.jsp");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid credentials");
        }
    }

}
