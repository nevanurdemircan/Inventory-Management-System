<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tedarikçi - Sipariş Yönetimi</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <h3>Tedarikçi - Sipariş Görüntüleme</h3>

  <!-- Siparişler Listesi -->
  <div class="row mb-3">
    <div class="col">
      <label for="supplierId" class="form-label">Tedarikçi ID:</label>
      <input type="number" class="form-control" id="supplierId" placeholder="Tedarikçi ID girin">
    </div>
    <div class="col">
      <button class="btn btn-primary mt-4" onclick="viewOrders()">Siparişleri Görüntüle</button>
    </div>
  </div>

  <h4>Onay Bekleyen Siparişler</h4>
  <table class="table">
    <thead>
    <tr>
      <th>Sipariş ID</th>
      <th>Ürünler</th>
      <th>Toplam Miktar</th>
      <th>Onayla</th>
    </tr>
    </thead>
    <tbody id="orderList"></tbody>
  </table>
</div>

<script>
  function viewOrders() {
    const supplierId = $("#supplierId").val();

    $.ajax({
      url: `/api/bill?supplierId=${supplierId}`,
      method: 'GET',
      success: function(response) {
        if (response && response.length > 0) {
          let orderHtml = "";
          response.forEach(order => {
            orderHtml += `<tr>
                                <td>${order.id}</td>
                                <td>${order.products.map(p => p.name).join(", ")}</td>
                                <td>${order.products.reduce((sum, p) => sum + p.quantity, 0)}</td>
                                <td><button class="btn btn-success" onclick="confirmOrder(${order.id})">Onayla</button></td>
                            </tr>`;
          });
          $("#orderList").html(orderHtml);
        } else {
          alert('Sipariş bulunamadı!');
        }
      },
      error: function(error) {
        alert("Siparişleri görüntüleme hatası: " + error);
      }
    });
  }

  function confirmOrder(orderId) {
    $.ajax({
      url: `/api/bill?action=confirm&id=${orderId}`,
      method: 'PUT',
      success: function(response) {
        alert('Sipariş başarıyla onaylandı!');
        viewOrders(); // Sipariş listesini güncelle
      },
      error: function(error) {
        alert("Sipariş onaylama hatası: " + error);
      }
    });
  }
</script>
</body>
</html>
