
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
        String typeString = req.getParameter("type");

        if (name == null || email == null || password == null || phone == null || typeString == null) {
            JsonResponse.send(resp, "All fields are required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!UserType.isValidType(typeString)) {
            JsonResponse.send(resp, "Invalid user type. Must be 'RETAILER' or 'SUPPLIER'.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserType type = UserType.valueOf(typeString.toUpperCase());

        RegisterDto registerDto = new RegisterDto(email, password, name, type, phone);
        Users user = authService.register(registerDto);

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            JsonResponse.send(resp, "Registration failed", HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Missing credentials");
            return;
        }

        LoginDto loginDto = new LoginDto(email, password);
        Users user = authService.login(loginDto);
        if (user != null) {
            UserType userType = UserType.valueOf(user.getType().name());

            req.getSession().setAttribute("user", user);

            if ("RETAILER".equalsIgnoreCase(String.valueOf(userType))) {
                resp.sendRedirect(req.getContextPath() + "/retailerdashboard.jsp");
            } else if ("SUPPLIER".equalsIgnoreCase(String.valueOf(userType))) {
                resp.sendRedirect(req.getContextPath() + "/supplierdashboard.jsp");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid credentials");
        }
    }
}