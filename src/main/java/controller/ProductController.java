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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private final ProductService productService =  SingletonManager.getBean(ProductService.class);
    private final Gson gson = new Gson();

    @WebServlet("/products")
    public class ProductServlet extends HttpServlet {

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // Parametreleri almak
            String name = request.getParameter("name");
            String quantityStr = request.getParameter("quantity");
            String priceStr = request.getParameter("price");
            String discountStr = request.getParameter("discount");
            String supplierIdStr = request.getParameter("supplierId");
            String imageUrlsStr = request.getParameter("imageUrls");

            // Verilerin boş olup olmadığını kontrol etme
            if (name == null || name.isEmpty() || quantityStr == null || quantityStr.isEmpty() ||
                    priceStr == null || priceStr.isEmpty() || discountStr == null || discountStr.isEmpty() ||
                    supplierIdStr == null || supplierIdStr.isEmpty()) {
                JsonResponse.send(response, "Eksik veri", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                // Sayısal verilere dönüştürme
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);
                double discount = Double.parseDouble(discountStr);
                int supplierId = Integer.parseInt(supplierIdStr);

                // İmaj URL'lerini ayıklama
                String[] imageUrlsArray = imageUrlsStr.split(",");
                List<String> imageUrls = Arrays.asList(imageUrlsArray);

                // Ürünü kaydetme
                Product product = new Product(name, quantity, price, discount, supplierId, imageUrls);
                Product savedProduct = productService.save(product);

                // Response gönderme
                if (savedProduct != null) {
                    JsonResponse.send(response, "Ürün başarıyla kaydedildi", HttpServletResponse.SC_OK);
                } else {
                    JsonResponse.send(response, "Ürün kaydedilemedi", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (NumberFormatException e) {
                JsonResponse.send(response, "Geçersiz sayısal değer", HttpServletResponse.SC_BAD_REQUEST);
                e.printStackTrace();
            }
        }
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
