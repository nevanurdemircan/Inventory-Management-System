<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .btn-remove {
            color: white;
            background-color: #dc3545;
        }
        .btn-remove:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mb-4">Your Cart</h2>
    <div class="table-responsive">
        <table class="table table-bordered text-center align-middle" id="cart-table">
            <thead class="table-dark">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="d-flex justify-content-between align-items-center mt-4">
        <h4 class="fw-bold">
            Total: <span id="cart-total" class="text-success">$0.00</span>
        </h4>
        <button class="btn btn-primary btn-lg" onclick="checkout()">Proceed to Checkout</button>
    </div>
</div>

<div class="container mt-5" id="billing-container" style="display: none;">
    <h2 class="text-center mb-4">Checkout</h2>
    <div id="billing-summary"></div>
    <button class="btn btn-success btn-lg w-100 mt-4" onclick="confirmBill()">Confirm and Pay</button>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    let cartItems = JSON.parse(localStorage.getItem('cart')) || [];

    function updateCart() {
        const tableBody = document.querySelector("#cart-table tbody");
        tableBody.innerHTML = "";

        let cartTotal = 0;
        cartItems.forEach(item => {
            const totalPrice = item.quantity * item.price;
            item.total = totalPrice;

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${item.productName}</td>
                <td>$${item.price.toFixed(2)}</td>
                <td>
                    <input type="number" value="${item.quantity}" min="1"
                           class="form-control w-50 mx-auto text-center"
                           onchange="updateItemQuantity(${item.productId}, this.value)">
                </td>
                <td>$${totalPrice.toFixed(2)}</td>
                <td>
                    <button class="btn btn-remove btn-sm" onclick="removeFromCart(${item.productId})">Remove</button>
                </td>
            `;
            tableBody.appendChild(row);
            cartTotal += totalPrice;
        });

        document.getElementById("cart-total").textContent = `$${cartTotal.toFixed(2)}`;
        const checkoutButton = document.querySelector('button[onclick="checkout()"]');
        checkoutButton.innerHTML = `Proceed to Checkout ($${cartTotal.toFixed(2)})`;
    }

    function updateItemQuantity(productId, quantity) {
        if (quantity <= 0) return;
        const item = cartItems.find(item => item.productId === productId);
        item.quantity = parseInt(quantity, 10);
        item.total = item.quantity * item.price;
        localStorage.setItem('cart', JSON.stringify(cartItems));
        updateCart();
    }

    function removeFromCart(productId) {
        cartItems = cartItems.filter(item => item.productId !== productId);
        localStorage.setItem('cart', JSON.stringify(cartItems));
        updateCart();
    }

    function checkout() {
        document.querySelector(".container").style.display = "none";
        document.getElementById("billing-container").style.display = "block";

        const billingSummary = document.getElementById("billing-summary");
        let billHtml = '<div class="card p-4"><h3>Billing Summary</h3><ul class="list-group">';
        let totalAmount = 0;

        cartItems.forEach(item => {
            billHtml += `<li class="list-group-item d-flex justify-content-between align-items-center">
                          ${item.productName}
                          <span>$${item.total.toFixed(2)}</span>
                         </li>`;
            totalAmount += item.total;
        });

        billHtml += `
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <strong>Total</strong>
                <span class="fw-bold text-success">$${totalAmount.toFixed(2)}</span>
            </li>
        </ul></div>`;
        billingSummary.innerHTML = billHtml;
    }

    function confirmBill() {
        alert("Invoice successfully created. Payment Successful!");
        cartItems = [];
        localStorage.setItem("cart", JSON.stringify(cartItems));
        updateCart();
        document.getElementById("billing-summary").innerHTML =
            '<div class="alert alert-success text-center">Thank you for your purchase!</div>';
    }

    document.addEventListener("DOMContentLoaded", updateCart);
</script>
</body>
</html>
