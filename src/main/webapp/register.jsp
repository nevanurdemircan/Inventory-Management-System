<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Register</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container">
  <div class="row justify-content-center my-5">
    <div class="col-md-6">
      <h2 class="text-center">Register</h2>
      <form action="api/auth/register" method="post">
        <div class="form-group">
          <label for="name">Full Name</label>
          <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
          <label for="email">Email address</label>
          <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="form-group">
          <label for="phone">Phone Number</label>
          <input type="text" class="form-control" id="phone" name="phone" required>
        </div>
        <div class="form-group">
          <label for="type">User Type</label>
          <select class="form-control" id="type" name="type">
            <option value="RETAILER">Retailer</option>
            <option value="SUPPLIER">Supplier</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Register</button>
      </form>
    </div>
  </div>
</div>
<script>
  document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);

    fetch('/api/auth/register', {
      method: 'POST',
      body: formData,
    })
            .then(response => response.json())
            .then(data => {
              if (data.error) {
                alert("Registration failed");
              } else {
                alert("Registration successful");
                window.location.href = '/login.jsp';
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
