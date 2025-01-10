<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Product List</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

  <style>
    .product-card {
      border: 1px solid #eaeaea;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      overflow: hidden;
      transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .product-card:hover {
      transform: scale(1.03);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    }

    .product-card img {
      height: 200px;
      object-fit: cover;
    }

    .product-price {
      color: #28a745;
      font-size: 1.2rem;
      font-weight: bold;
    }

    .empty-message {
      text-align: center;
      color: #6c757d;
      font-size: 1.2rem;
      margin-top: 30px;
    }

    .btn-add-to-cart {
      background-color: #6c757d !important;
      color: white !important;
      border: none;
      padding: 0.5rem 1rem;
      font-size: 0.9rem;
      border-radius: 5px;
    }

    .btn-add-to-cart:hover {
      background-color: #6c757d !important;
      color: white !important;
    }

    @media (max-width: 768px) {
      .product-card img {
        height: auto;
      }

      .btn-add-to-cart {
        font-size: 1rem;
      }
    }
  </style>
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container mt-5">
  <h2 class="text-center mb-4">Product List</h2>
  <div id="product-container" class="row">
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>
  window.onload = function () {
    fetch('/products')
            .then(response => response.json())
            .then(data => {
              const productContainer = document.getElementById('product-container');

              if (data && Array.isArray(data) && data.length > 0) {
                productContainer.innerHTML = '';

                data.forEach(product => {
                  const productCard = document.createElement('div');
                  productCard.className = 'col-md-4 mb-4';

                  productCard.innerHTML = `
                            <div class="card product-card">
                                <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                                <div class="card-body">
                                    <h5 class="card-title">${product.name}</h5>
                                    <p class="card-text">${product.description}</p>
                                    <p class="product-price">Price: $${product.price}</p>
                                    <button class="btn btn-add-to-cart" onclick="addToCart(${product.id}, '${product.name}', ${product.price})">Add to Cart</button>
                                </div>
                            </div>
                        `;

                  productContainer.appendChild(productCard);
                });
              } else {
                productContainer.innerHTML = '<p class="empty-message">No products available.</p>';
              }
            })
            .catch(error => {
              console.error('Error fetching products:', error);
              const productContainer = document.getElementById('product-container');
              productContainer.innerHTML = '<p class="text-danger text-center">Failed to load products. Please try again later.</p>';
            });
  };

  function addToCart(productId, productName, price) {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const existingProductIndex = cart.findIndex(item => item.productId === productId);

    if (existingProductIndex > -1) {
      cart[existingProductIndex].quantity += 1;
    } else {
      cart.push({
        productId: productId,
        name: productName,
        price: price,
        quantity: 1
      });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    alert(`${productName} added to cart!`);
  }
</script>

<%@ include file="/include/footer.jsp" %>
</body>
</html>