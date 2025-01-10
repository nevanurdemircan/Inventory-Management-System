<%@ page import="entity.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Users user = (Users) session.getAttribute("user");
    if (user == null || !"RETAILER".equalsIgnoreCase(String.valueOf(user.getType()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retailer Dashboard</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f7fc;
        }

        .dashboard {
            margin-top: 60px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            background-color: #fff;
            padding: 20px;
        }

        .dashboard h1 {
            font-size: 2rem;
            margin-bottom: 20px;
        }

        .list-group-item {
            font-size: 1.2rem;
            display: flex;
            align-items: center;
            padding: 15px 20px;
        }

        .list-group-item i {
            margin-right: 15px;
            font-size: 1.5rem;
        }

        .list-group-item:hover {
            background-color: #e2e6ea;
            color: #212529;
            transition: all 0.3s ease-in-out;
        }

        .navbar {
            background-color: #007bff;
        }

        .navbar a {
            color: #fff !important;
        }
    </style>
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container">
    <div class="dashboard">
        <h1 class="text-center text-primary">Welcome, <%= user.getName() %></h1>
        <hr>
        <div class="list-group">
            <a href="addProduct.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-plus-circle text-success"></i> Add a New Product
            </a>
            <a href="searchProduct.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-search text-info"></i> Search Products
            </a>
            <a href="cart.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-shopping-cart text-warning"></i> View and Edit Cart
            </a>
            <a href="productList.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-list text-primary"></i> Product List
            </a>
        </div>
    </div>
</div>

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
