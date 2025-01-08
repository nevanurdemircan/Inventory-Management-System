<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container">
    <div class="row my-5">
        <div class="col-md-6">
            <h2>Search Products</h2>
            <form id="searchForm">
                <div class="form-group">
                    <label for="productName">Product Name</label>
                    <input type="text" class="form-control" id="productName" name="productName" required>
                </div>
                <div class="form-group">
                    <label for="supplierId">Supplier ID</label>
                    <input type="text" class="form-control" id="supplierId" name="supplierId" required>
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
            <div id="productResults" class="mt-3"></div>
        </div>

        <div class="container">
            <div class="row justify-content-center my-5">
                <div class="col-md-6">
                    <h2 class="text-center">Add Product</h2>
                    <form action="/products" method="get">

                        <div class="form-group">
                            <label for="newProductName">Product Name</label>
                            <input type="text" class="form-control" id="newProductName" name="newProductName" required>
                        </div>
                        <div class="form-group">
                            <label for="newProductQuantity">Quantity</label>
                            <input type="newProductQuantity" class="form-control" id="newProductQuantity"
                                   name="newProductQuantity"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="newProductPrice">Price</label>
                            <input type="newProductPrice" step="0.01" class="form-control" id="newProductPrice"
                                   name="newProductPrice"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="newDiscount">Discount</label>
                            <input type="newDiscount" step="0.01" class="form-control" id="newDiscount"
                                   name="newDiscount" required>
                        </div>
                        <div class="form-group">
                            <label for="newSupplierId">Supplier Id</label>
                            <input type="newSupplierId" step="0.01" class="form-control" id="newSupplierId"
                                   name="newSupplierId" required>
                        </div>
                        <div class="form-group">
                            <label for="newImagesUrls">Image URLs (Comma Separated)</label>
                            <input type="newImagesUrls" class="form-control" id="newImagesUrls" name="newImagesUrls"
                                   required>
                        </div>
                        <button type="submit" class="btn btn-success">Add Product</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            // Product Search functionality
            document.getElementById('searchForm').addEventListener('submit', function (event) {
                event.preventDefault();

                const name = document.getElementById('productName').value;
                const supplierId = document.getElementById('supplierId').value;

                if (!name || !supplierId) {
                    alert("Please fill in all fields");
                    return;
                }

                fetch(`/products?name=${name}&supplierId=${supplierId}`, {
                    method: 'GET'
                })
                    .then(response => response.json())
                    .then(products => {
                        if (products.length > 0) {
                            let html = '<h4>Results:</h4><ul class="list-group">';
                            products.forEach(product => {
                                html += `
                <li class="list-group-item d-flex justify-content-between align-items-center">
                  ${product.name} - $${product.price}
                  <input type="number" class="quantity" data-id="${product.id}" value="1" min="1" max="${product.quantity}" />
                  <button class="btn btn-warning addToCartBtn" data-id="${product.id}">Add to Cart</button>
                </li>
              `;
                            });
                            html += '</ul>';
                            document.getElementById('productResults').innerHTML = html;
                        } else {
                            document.getElementById('productResults').innerHTML = '<p>No products found</p>';
                        }
                    })
                    .catch(error => {
                        alert("Error fetching products");
                        console.error(error);
                    });
            });


            document.getElementById('productForm').addEventListener('submit', function (event) {
                event.preventDefault();

                const formData = new FormData(this);
                fetch('/products', {
                    method: 'POST',
                    body: formData,
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.error) {
                            alert("Add Product failed");
                        } else {
                            alert("Add Product successful");
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            });

        </script>
        <%@ include file="/include/footer.jsp" %>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
