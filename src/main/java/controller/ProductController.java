package controller;

import com.google.gson.Gson;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;
import util.ResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private ProductService productService = new ProductService();
    private final Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());

        Gson gson = new Gson();
        Product product = gson.fromJson(jsonString, Product.class);

        if (product.getName() == null || product.getQuantity() == 0 || product.getPrice() == 0.0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing parameters");
            return;
        }

        Product savedProduct = productService.save(product);

        if (savedProduct != null) {
            ResponseModel responseModel = new ResponseModel(true, "Product saved successfully", savedProduct);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        } else {
            ResponseModel responseModel = new ResponseModel(false, "Failed to save product", null);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productLikeIdStr = request.getParameter("productLikeId");

        if (productLikeIdStr == null || productLikeIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Missing productLikeId parameter\"}");
            return;
        }

        try {
            int productLikeId = Integer.parseInt(productLikeIdStr);
            List<Product> products = productService.findAllByProductLike(productLikeId);

            if (products != null && !products.isEmpty()) {
                ResponseModel responseModel = new ResponseModel(true, "Products fetched successfully", products);
                response.setStatus(HttpServletResponse.SC_OK); // OK status
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(responseModel));
            } else {
                ResponseModel responseModel = new ResponseModel(false, "No products found", null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Not found status
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(responseModel));
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid productLikeId format\"}");
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = request.getReader().lines().collect(Collectors.joining());

        Gson gson = new Gson();
        Product product = gson.fromJson(jsonString, Product.class);

        if (product.getId() == 0 || product.getName() == null || product.getQuantity() == 0 || product.getPrice() == 0.0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing parameters or invalid id");
            return;
        }

        Product updatedProduct = productService.update(product.getId(), product);

        if (updatedProduct != null) {
            ResponseModel responseModel = new ResponseModel(true, "Product updated successfully", updatedProduct);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        } else {
            ResponseModel responseModel = new ResponseModel(false, "Failed to update product", null);
            response.setContentType("application/json");
            response.getWriter().write(responseModel.toString());
        }
    }

}
