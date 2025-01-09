package controller;

import com.google.gson.Gson;
import dto.CartItemDto;
import exception.handler.AppExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.CartService;
import util.JsonResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/cart")
public class CartController extends HttpServlet {
    private final CartService cartService = SingletonManager.getBean(CartService.class);
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        CartItemDto cartItem = gson.fromJson(reader, CartItemDto.class);

        if (cartItem == null) {
            JsonResponse.send(resp, "Invalid cart item", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            cartService.addItemToCart(cartItem);
            JsonResponse.send(resp, "Item added to cart", HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if ("confirm".equalsIgnoreCase(action)) {
            handleConfirmOrder(req, resp);
        } else {
            JsonResponse.send(resp, "Invalid action parameter", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleConfirmOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cartIdParam = req.getParameter("cartId");

        if (cartIdParam == null || cartIdParam.trim().isEmpty()) {
            JsonResponse.send(resp, "Missing cart ID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int cartId = Integer.parseInt(cartIdParam);
            cartService.confirmCart(cartId);
            JsonResponse.send(resp, "Order confirmed successfully", HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }
}

