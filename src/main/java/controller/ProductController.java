package controller;

import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
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
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private final ProductService productService =  SingletonManager.getBean(ProductService.class);
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());

        Product product = gson.fromJson(jsonString, Product.class);

        if (product.getName() == null || product.getQuantity() == 0 || product.getPrice() == 0.0) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Product savedProduct = productService.save(product);

        if (savedProduct == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        JsonResponse.send(response, savedProduct, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String supplierIdStr = request.getParameter("supplierId");

        if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(supplierIdStr)) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int supplierId = Integer.parseInt(supplierIdStr);

        try {
            List<Product> products = productService.findAllByProductLike(name, supplierId);

            if (products == null || products.isEmpty()) {
                JsonResponse.send(response, "", HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JsonResponse.send(response, products, HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            AppExceptionHandler.handle(response, e);
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());

        Product product = gson.fromJson(jsonString, Product.class);

        if (product.getId() == 0 || product.getName() == null || product.getQuantity() == 0 || product.getPrice() == 0.0) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Product updatedProduct = productService.update(product.getId(), product);

        if (updatedProduct == null) {
            JsonResponse.send(response, "", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        JsonResponse.send(response, updatedProduct, HttpServletResponse.SC_OK);
    }

}
