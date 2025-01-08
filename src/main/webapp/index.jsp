<%@ page import="entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String baseImage = "https://default-image-url.com/default-product.jpg";
%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="include/header.jsp" %>
    <title>Inventory Management System</title>
    <style>
        .search-bar {
            margin-bottom: 20px;
        }
        .product-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }
    </style>
</head>
<body>
<%@include file="include/navbar.jsp" %>
<div class="container">
    <div class="card-header my-3">Products</div>

    <div class="search-bar d-flex justify-content-between">
        <input type="text" id="searchInput" class="form-control w-50" placeholder="Search products by name..." onkeyup="filterProducts()">
        <select id="sortOptions" class="form-select w-25" onchange="sortProducts()">
            <option value="">Sort by</option>
            <option value="priceLowHigh">Price: Low to High</option>
            <option value="priceHighLow">Price: High to Low</option>
            <option value="nameAZ">Name: A to Z</option>
            <option value="nameZA">Name: Z to A</option>
        </select>
    </div>

    <div class="row" id="productContainer">
        <%
            if (products != null && !products.isEmpty()) {
                for (Product product : products) {
        %>
        <div class="col-md-3 product-card" data-name="<%= product.getName().toLowerCase() %>" data-price="<%= product.getPrice() %>">
            <div class="card w-100 mb-3" style="width: 18rem;">
                <%
                    String imageUrl = (product.getImageUrlList() != null && !product.getImageUrlList().isEmpty())
                            ? product.getImageUrlList().get(0)
                            : baseImage;
                %>
                <img class="card-img-top" src="<%= imageUrl %>" alt="<%= product.getName() %>">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="<%= request.getContextPath() %>/product-detail?id=<%= product.getId() %>" class="text-decoration-none">
                            <%= product.getName() %>
                        </a>
                    </h5>
                    <div class="d-flex justify-content-between">
                        <h6 class="price">Price: $<%= product.getPrice() %></h6>
                        <h6 class="discount text-danger">Discount: <%= Math.round(product.getDiscount() * 100) %>%</h6>
                    </div>
                    <h6 class="total-price">Total Price: <%= String.format("%.2f", product.getPrice() * (1 - product.getDiscount())) %></h6>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>No products available.</p>
        <%
            }
        %>
    </div>
</div>

<%@include file="include/footer.jsp" %>
</body>
</html>
