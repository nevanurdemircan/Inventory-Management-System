package controller;

import com.google.gson.Gson;
import entity.Users;
import enums.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import util.ResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users")
public class UserController extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());
        Gson gson = new Gson();
        Users user = gson.fromJson(jsonString, Users.class);


        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getPhone() == null || user.getType() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing parameters");
            return;
        }
        Users savedUser = userService.save(user);

        if (savedUser != null) {
            ResponseModel responseModel = new ResponseModel(true, "User saved successfully", savedUser);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        } else {
            ResponseModel responseModel = new ResponseModel(false, "Failed to save user", null);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            List<Users> usersList = userService.findAll();
            ResponseModel responseModel = new ResponseModel(true, "Users fetched successfully", usersList);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        } else {
            int userId = Integer.parseInt(request.getParameter("id"));
            Users user = userService.findById(userId);

            if (user != null) {
                ResponseModel responseModel = new ResponseModel(true, "User found", user);
                response.setContentType("application/json");
                response.getWriter().write(responseModel.toString());
            } else {
                ResponseModel responseModel = new ResponseModel(false, "User not found", null);
                response.setContentType("application/json");
                response.getWriter().write(responseModel.toString());
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String typeStr = request.getParameter("type");

        if (name == null || email == null || password == null || phone == null || typeStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing parameters");
            return;
        }

        Users user = new Users(userId, UserType.fromString(typeStr), name, email, password, phone);

        Users updatedUser = userService.update(userId, user);

        if (updatedUser != null) {
            ResponseModel responseModel = new ResponseModel(true, "User updated successfully", updatedUser);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        } else {
            ResponseModel responseModel = new ResponseModel(false, "Failed to update user", null);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        }
    }
}
