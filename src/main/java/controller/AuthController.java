package controller;

import com.google.gson.Gson;
import dto.LoginDto;
import dto.RegisterDto;
import entity.Users;
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
    private final AuthService authService =  SingletonManager.getBean(AuthService.class);;
    private final Gson gson = new Gson();

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
        RegisterDto registerDto = gson.fromJson(req.getReader(), RegisterDto.class);
        Users user = authService.register(registerDto);
        JsonResponse.send(resp, user, HttpServletResponse.SC_OK);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginDto loginDto = gson.fromJson(req.getReader(), LoginDto.class);

        try {
            Users user = authService.login(loginDto);

            if (user == null) {
                JsonResponse.send(resp, "", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            JsonResponse.send(resp, user, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }
}
