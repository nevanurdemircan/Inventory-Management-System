<%@ page import="entity.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Users user = (Users) session.getAttribute("user");
    if (user == null || !"SUPPLIER".equalsIgnoreCase(String.valueOf(user.getType()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supplier Dashboard</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #eef2f7;
        }

        .dashboard {
            margin-top: 60px;
            background: #ffffff;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            padding: 20px;
        }

        .dashboard h1 {
            font-size: 2rem;
            color: #007bff;
        }

        .dashboard p {
            color: #6c757d;
        }

        .list-group-item {
            display: flex;
            align-items: center;
            font-size: 1.2rem;
            padding: 15px 20px;
            border: 1px solid #ddd;
            margin-bottom: 10px;
            transition: all 0.3s ease;
        }

        .list-group-item i {
            margin-right: 15px;
            font-size: 1.5rem;
        }

        .list-group-item:hover {
            background-color: #e9ecef;
            border-color: #007bff;
            color: #007bff;
        }

        .navbar {
            background-color: #007bff;
        }

        .navbar a {
            color: white !important;
        }
    </style>
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container">
    <div class="dashboard">
        <h1 class="text-center">Welcome, <%= user.getName() %></h1>
        <hr>
        <div class="list-group">
            <a href="supplierOrder.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-box"></i> Supplier Orders
            </a>
            <a href="cart.jsp" class="list-group-item list-group-item-action">
                <i class="fas fa-shopping-cart"></i> View Cart
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
