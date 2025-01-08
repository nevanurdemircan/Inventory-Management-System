<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Cart</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
  <div class="row my-5">
    <div class="col-md-12">
      <h2>Your Cart</h2>
      <ul id="cartItems" class="list-group">
      </ul>
    </div>
  </div>
</div>

<script>
  const cart = JSON.parse(localStorage.getItem('cart')) || [];

  fetch('/products')
          .then(response => response.json())
          .then(products => {
            const cartItemsContainer = document.getElementById('cartItems');
            cartItemsContainer.innerHTML = '';

            if (cart.length > 0) {
              cart.forEach(item => {
                const product = products.find(p => p.id === item.productId);
                if (product) {
                  const li = document.createElement('li');
                  li.classList.add('list-group-item', 'cart-item');
                  li.innerHTML = `
              <div class="d-flex justify-content-start align-items-center">
                  <img src="${product.imageUrl}" alt="${product.name}" width="50" height="50">
                  <div class="ml-3">
                      <h6>${product.name}</h6>
                      <p>Price: $${product.price}</p>
                  </div>
              </div>
              <div class="d-flex align-items-center">
                  <p>Quantity: ${item.quantity}</p>
              </div>
            `;
                  cartItemsContainer.appendChild(li);
                }
              });
            } else {
              cartItemsContainer.innerHTML = '<p>Your cart is empty.</p>';
            }
          })
          .catch(error => {
            console.error('Error fetching products:', error);
          });
</script>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
