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
    <h2 class="text-center my-5">Add Product</h2>
    <form action="products/add" method="post">
        <div class="form-group">
            <label for="name">Product Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="quantity">Quantity</label>
            <input type="number" class="form-control" id="quantity" name="quantity" required>
        </div>
        <div class="form-group">
            <label for="price">Price</label>
            <input type="number" step="0.01" class="form-control" id="price" name="price" required>
        </div>
        <div class="form-group">
            <label for="discount">Discount</label>
            <input type="number" step="0.01" class="form-control" id="discount" name="discount" required>
        </div>
        <div class="form-group">
            <label for="supplierId">Supplier Id</label>
            <input type="number" class="form-control" id="supplierId" name="supplierId" required>
        </div>
        <div class="form-group">
            <label for="imageUrls">Image URLs (Comma Separated)</label>
            <input type="text" class="form-control" id="imageUrls" name="imageUrls" required>
        </div>
        <button type="submit" class="btn btn-success">Add Product</button>
    </form>

    <div id="responseMessage" class="mt-4"></div>

    <script>
        document.getElementById('productForm').addEventListener('submit', function (event) {
            event.preventDefault();

            const formData = new FormData(this);

            fetch('/products/add',{
                method: 'POST',
                body: formData,
            })
                .then(response =>response.json())
                .then(data =>{
                    if (data.error) {
                        alert("product add failed");
                    } else {
                        alert("Product add successful");
                        window.location.href = '/searchProduct.jsp';
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        })
    </script>

    <%@ include file="/include/footer.jsp" %>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
