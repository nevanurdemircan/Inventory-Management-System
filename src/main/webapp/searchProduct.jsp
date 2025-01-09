<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Search</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container my-5">
    <h2>Search Products</h2>
    <div class="form-group">
        <input type="text" class="form-control" id="productName" placeholder="Enter product name">
    </div>
    <button class="btn btn-primary" id="searchBtn">Search</button>
    <div id="productResults" class="mt-3"></div>
</div>

<script>
    document.getElementById("searchBtn").addEventListener("click", function () {
        const productName = document.getElementById("productName").value.trim();

        if (!productName) {
            alert("Please enter a product name.");
            return;
        }

        console.log("Product Name Sent:", productName);

        fetch(`/products/search?name=${encodeURIComponent(productName)}`)
            .then(response => response.text())
            .then(data => {
                if (data !== "No products found") {
                    document.getElementById('productResults').innerHTML = data;
                } else {
                    document.getElementById('productResults').innerHTML = '<p>No products found</p>';
                }
            })
            .catch(error => {
                alert("Error fetching products");
                console.error(error);
            });
    });

    function addToCart(product) {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];

        const existingProductIndex = cart.findIndex(item => item.productId === product.productId);

        if (existingProductIndex > -1) {
            cart[existingProductIndex].quantity = parseInt(cart[existingProductIndex].quantity) + 1;
        } else {
            cart.push({
                productId: product.productId,
                productName: product.name,
                productPrice: product.price,
                quantity: 1
            });
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        console.log('Sepet güncellendi:', cart);
    }

</script>

<%@ include file="/include/footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>