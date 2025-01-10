<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/include/navbar.jsp" %>

<div class="container">
    <div class="row justify-content-center my-5">
        <div class="col-md-6">
            <h2 class="text-center">Login</h2>

            <form action="/api/auth/login" method="post">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <div id="error-message" style="color: red; <% if(request.getParameter("error") != null) { %> display: block; <% } else { %> display: none; <% } %>">
                    Invalid credentials, please try again.
                </div>

                <button type="submit" class="btn btn-primary">Login</button>
            </form>
        </div>
    </div>
</div>

<script>
    window.onload = function() {
        $('#loginModal').modal('show');
    };
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(this);

        fetch('/api/auth/login', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    document.getElementById('error-message').style.display = 'block';
                } else {
                    if (data.type === 'RETAILER') {
                        window.location.href = '/retailerdashboard.jsp';
                    } else if (data.type === 'SUPPLIER') {
                        window.location.href = '/supplierdashboard.jsp';
                    }
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
