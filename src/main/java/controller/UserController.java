package controller;

import com.google.gson.Gson;
import entity.Users;
import enums.UserType;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.UserService;
import util.JsonResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users")
public class UserController extends HttpServlet {

    private final UserService userService =  SingletonManager.getBean(UserService.class);
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());
        Users user = gson.fromJson(jsonString, Users.class);

        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getPhone() == null || user.getType() == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Users savedUser = userService.save(user);

        if (savedUser == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
        }

        JsonResponse.send(response, savedUser, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") == null) {
            List<Users> usersList = userService.findAll();
            JsonResponse.send(response, usersList, HttpServletResponse.SC_OK);
        } else {
            int userId = Integer.parseInt(request.getParameter("id"));
            Users user = userService.findById(userId);

            if (user == null) {
                JsonResponse.send(response, "", HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JsonResponse.send(response, user, HttpServletResponse.SC_OK);
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String typeStr = request.getParameter("type");

        if (name == null || email == null || password == null || phone == null || typeStr == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Users user = new Users(userId, UserType.valueOf(typeStr), name, email, password, phone);

        Users updatedUser = userService.update(userId, user);

        if (updatedUser == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        JsonResponse.send(response, user, HttpServletResponse.SC_OK);
    }
}
