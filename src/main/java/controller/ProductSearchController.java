package controller;

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

@WebServlet("/products/search")
public class ProductSearchController extends HttpServlet {
    private final ProductService productService = SingletonManager.getBean(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        if (StringUtils.isNullOrEmpty(name)) {
            JsonResponse.send(response, "{\"message\": \"Product name and supplierId are required!\"}", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {

            List<Product> products = productService.findAllByProductLike(name);

            if (products == null || products.isEmpty()) {
                JsonResponse.send(response, "{\"message\": \"No products found matching the criteria.\"}", HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            StringBuilder htmlResponse = new StringBuilder("<h4>Results:</h4><ul class='list-group'>");

            for (Product product : products) {
                htmlResponse.append("<li class='list-group-item d-flex justify-content-between align-items-center'>")
                        .append("<img src='imageas/").append(product.getName()).append(".jpg' alt='").append(product.getName()).append("' style='width: 50px; height: 50px;'>") // Resim ekleniyor
                        .append("<span>").append(product.getName()).append("</span>")
                        .append("<span> Price: ").append(product.getPrice()).append(" $</span>")
                        .append("<button class='btn btn-warning addToCartBtn'>Add to Cart</button>")
                        .append("</li>");
            }

            htmlResponse.append("</ul>");

            response.setContentType("text/html");
            response.getWriter().write(htmlResponse.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(response, e);
        }
    }
}