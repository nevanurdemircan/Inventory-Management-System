<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Supplier Orders</title>
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
  </style>
</head>
<body>
<div class="container">
  <h2 class="text-center mb-4">Supplier Orders</h2>
  <div class="table-responsive">
    <table class="table table-bordered text-center align-middle" id="orders-table">
      <thead class="table-dark">
      <tr>
        <th>Order ID</th>
        <th>Retailer ID</th>
        <th>Product Name</th>
        <th>Quantity</th>
        <th>Status</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  const ordersTableBody = document.querySelector("#orders-table tbody");

  async function getSupplierId() {
    try {
      const response = await fetch("/api/bill");
      const supplier = await response.json();

      if (supplier && supplier.id) {
        return supplier.id;
      } else {
        throw new Error("Failed to get supplier ID.");
      }
    } catch (error) {
      console.error("Error fetching supplier ID:", error);
      alert("Failed to fetch supplier details. Please log in again.");
    }
  }

  async function fetchOrders(supplierId) {
    try {
      const response = await fetch(`/api/bill?supplierId=${supplierId}`);
      const orders = await response.json();
      populateOrdersTable(orders);
    } catch (error) {
      console.error("Failed to fetch orders:", error);
    }
  }

  function populateOrdersTable(orders) {
    ordersTableBody.innerHTML = "";
    if (orders.length === 0) {
      ordersTableBody.innerHTML = "<tr><td colspan='6'>No orders found for this supplier.</td></tr>";
      return;
    }

    orders.forEach(order => {
      const row = document.createElement("tr");
      row.innerHTML = `
                <td>${order.id}</td>
                <td>${order.retailerId}</td>
                <td>${order.productName}</td>
                <td>${order.quantity}</td>
                <td>${order.status}</td>
                <td>
                  ${order.status === "PENDING"
                      ? `<button class="btn btn-success btn-sm" onclick="confirmOrder(${order.id})">Confirm</button>`
                      : `<span class="text-muted">Completed</span>`}
                </td>
            `;
      ordersTableBody.appendChild(row);
    });
  }

  async function confirmOrder(orderId) {
    try {
      const response = await fetch(`/api/bill?id=${orderId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        }
      });
      if (response.ok) {
        alert("Order confirmed successfully!");
        const supplierId = await getSupplierId();
        fetchOrders(supplierId);
      } else {
        const error = await response.text();
        console.error("Failed to confirm order:", error);
        alert("Failed to confirm the order.");
      }
    } catch (error) {
      console.error("Error confirming order:", error);
    }
  }

  document.addEventListener("DOMContentLoaded", async () => {
    const supplierId = await getSupplierId();
    if (supplierId) {
      fetchOrders(supplierId);
    }
  });
</script>
</body>
</html>
