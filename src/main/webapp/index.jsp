<%@ page import="entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String baseImage = "https://default-image-url.com/default-product.jpg";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Management System</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .product-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }
        .carousel-inner img {
            width: 100%;
            height: 400px;
        }
        .search-bar {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<%@ include file="include/navbar.jsp" %>

<div class="container mt-4">
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
</div>

<div id="productCarousel" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#productCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#productCarousel" data-slide-to="1"></li>
        <li data-target="#productCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <%
            if (products != null && products.size() > 0) {
                for (int i = 0; i < Math.min(3, products.size()); i++) {
                    Product product = products.get(i);
                    String imageUrl = (product.getImageUrlList() != null && !product.getImageUrlList().isEmpty())
                            ? product.getImageUrlList().get(0)
                            : baseImage;
        %>
        <div class="carousel-item <%= i == 0 ? "active" : "" %>">
            <img class="d-block w-100" src="<%= imageUrl %>" alt="<%= product.getName() %>">
            <div class="carousel-caption d-none d-md-block">
                <h5><%= product.getName() %></h5>
                <p>Price: $<%= product.getPrice() %></p>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
    <a class="carousel-control-prev" href="#productCarousel" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#productCarousel" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<div class="container mt-5">
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

<%@ include file="include/footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function filterProducts() {
        var input = document.getElementById('searchInput').value.toLowerCase();
        var productCards = document.querySelectorAll('.product-card');

        productCards.forEach(function(card) {
            var productName = card.getAttribute('data-name');
            if (productName.includes(input)) {
                card.style.display = "";
            } else {
                card.style.display = "none";
            }
        });
    }

    function sortProducts() {
        var sortValue = document.getElementById('sortOptions').value;
        var productCards = Array.from(document.querySelectorAll('.product-card'));

        productCards.sort(function(a, b) {
            var priceA = parseFloat(a.getAttribute('data-price'));
            var priceB = parseFloat(b.getAttribute('data-price'));

            if (sortValue === "priceLowHigh") return priceA - priceB;
            if (sortValue === "priceHighLow") return priceB - priceA;
            return 0;
        });

        var container = document.getElementById('productContainer');
        container.innerHTML = '';
        productCards.forEach(function(card) {
            container.appendChild(card);
        });
    }
</script>

</body>
</html>
