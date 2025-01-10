<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f7fc;
        }

        .container {
            max-width: 600px;
            margin-top: 50px;
        }

        .form-group input, .form-group select {
            font-size: 1.1rem;
        }

        .form-control {
            border-radius: 0.375rem;
        }

        .alert-info {
            font-size: 1rem;
            margin-bottom: 30px;
        }

        hr {
            border-color: #ddd;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            font-size: 1rem;
        }

        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #545b62;
        }

        .form-text {
            font-size: 0.9rem;
            color: #888;
        }
    </style>
</head>
<body>
<%@ include file="/include/navbar.jsp" %>
<div class="container">
    <h2 class="text-center">Register</h2>

    <%
        String loggedInUser = (String) session.getAttribute("user");

        if (loggedInUser != null) {

    } else {
    %>
    <form id="registerForm" action="/api/auth/register" method="post">
        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" class="form-control" required placeholder="Enter your full name">
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" class="form-control" required placeholder="Enter your email address">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required placeholder="Enter your password">
        </div>

        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" class="form-control" required placeholder="Enter your phone number">
        </div>

        <div class="form-group">
            <label for="type">User Type</label>
            <select id="type" name="type" class="form-control" required>
                <option value="RETAILER">Retailer</option>
                <option value="SUPPLIER">Supplier</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Register</button>
    </form>

    <hr>

    <p>Already have an account?  <a href="/login.jsp">Login here</a></p>
    <%
        }
    %>
</div>

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
