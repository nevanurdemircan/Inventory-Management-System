package controller;

import entity.Product;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.ProductService;
import util.JsonResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/products/add")
public class ProductAddController extends HttpServlet {
    private final ProductService productService = SingletonManager.getBean(ProductService.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String quantityStr = request.getParameter("quantity");
        String priceStr = request.getParameter("price");
        String discountStr = request.getParameter("discount");
        String supplierIdStr = request.getParameter("supplierId");
        String imageUrlsStr = request.getParameter("imageUrls");

        if (name == null || name.isEmpty() || quantityStr == null || quantityStr.isEmpty() ||
                priceStr == null || priceStr.isEmpty() || discountStr == null || discountStr.isEmpty() ||
                supplierIdStr == null || supplierIdStr.isEmpty()) {
            JsonResponse.send(response, "Eksik veri", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            double discount = Double.parseDouble(discountStr);
            int supplierId = Integer.parseInt(supplierIdStr);

            String[] imageUrlsArray = imageUrlsStr.split(",");
            List<String> imageUrls = Arrays.asList(imageUrlsArray);

            Product product = new Product(name, quantity, price, discount, supplierId, imageUrls);
            Product savedProduct = productService.save(product);

            String redirectPath = request.getContextPath();

            if (savedProduct != null) {
                response.sendRedirect(redirectPath + "/searchProduct.jsp");
            } else {
                JsonResponse.send(response, "Ürün kaydedilemedi", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (NumberFormatException e) {
            JsonResponse.send(response, "Geçersiz sayısal değer", HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}

