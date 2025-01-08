package controller;

import entity.Product;
import exception.handler.AppExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.ProductService;
import util.JsonResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private final ProductService productService =  SingletonManager.getBean(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Product> products = productService.findAll();

            if (products == null || products.isEmpty()) {
                JsonResponse.send(response, "{\"message\": \"No products found.\"}", HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JsonResponse.send(response, products, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(response, e);
        }
    }
}
