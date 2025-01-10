<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Register</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/include/navbar.jsp" %>
<div class="container mt-5">
  <h2>Register</h2>
  <form id="registerForm" action="/api/auth/register" method="post">
    <div class="form-group">
      <label for="name">Full Name</label>
      <input type="text" id="name" name="name" class="form-control" required>
    </div>
    <div class="form-group">
      <label for="email">Email Address</label>
      <input type="email" id="email" name="email" class="form-control" required>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" class="form-control" required>
    </div>
    <div class="form-group">
      <label for="phone">Phone</label>
      <input type="text" id="phone" name="phone" class="form-control" required>
    </div>
    <div class="form-group">
      <label for="type">User Type</label>
      <select id="type" name="type" class="form-control" required>
        <option value="RETAILER">Retailer</option>
        <option value="SUPPLIER">Supplier</option>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Register</button>
  </form>
</div>
</body>
</html>
<script>
  document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    fetch("/api/auth/register", {
      method: "POST",
      body: new URLSearchParams(formData),
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    })
            .then(response => {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error("Registration failed.");
              }
            })
            .then(data => {
              if (data.message === "Registration successful") {
                window.location.href = "/login.jsp";
              } else {
                alert("Registration failed: " + data.message);
              }
            })
            .catch(error => {
              console.error("Error during form submission:", error);
              window.location.href = "/login.jsp";
            });
  });
</script>

<%@ include file="/include/footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
