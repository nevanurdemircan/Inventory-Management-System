<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Shopping Cart</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container my-5">
  <h2 class="text-center">Shopping Cart</h2>

  <div class="row">
    <c:forEach var="product" items="${cartProducts}">
      <div class="col-md-4">
        <div class="card">
          <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
          <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">${product.description}</p>
            <p class="card-text">$${product.price}</p>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <div class="text-center">
    <a href="checkout" class="btn btn-success">Proceed to Checkout</a>
  </div>
</div>

<%@ include file="/include/footer.jsp" %>
</body>
</html>
