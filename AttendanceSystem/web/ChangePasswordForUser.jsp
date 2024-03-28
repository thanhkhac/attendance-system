<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="author" content="Muhamad Nauval Azhar">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="This is a login page template based on Bootstrap 5">
        <title>Thay đổi mật khẩu</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
        <!-- Bootstrap Font Icon CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <style>
            body {
                background-image: url('assets/img/image007_9.jpg');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                height: 100vh;
                overflow: hidden;
            }
            .card.shadow-lg {
                border: 2px solid #323539;
                box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.1);
            }
            .label-input-group {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                margin-bottom: 1rem;
            }
            .label-input-group label {
                margin-bottom: 0.5rem;
            }
        </style>
    </head>

    <body>
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-sm-center h-100">
                    <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                        <div class="text-center my-5">                                                                                                                                                                                                                                      
                            <div class="card shadow-lg" style="margin-top: 100px">
                                <div class="card-body p-5">
                                    <h1 class="fs-4 card-title fw-bold mb-4">Change Password</h1>
                                    <form method="POST" class="needs-validation" novalidate="" autocomplete="off" action="changepassforuser">
                                        <div class="mb-3 label-input-group">
                                            <label class="text-muted" for="currentPassword">Your Password: </label>
                                            <div class="input-group">
                                                <input id="currentPassword" type="password" class="form-control" name="currentPassword" required>
                                                <button class="btn btn-outline-secondary" type="button" onclick="togglePasswordVisibility('currentPassword')">
                                                    <i class="bi bi-eye"></i>
                                                </button>
                                            </div>
                                            <div class="invalid-feedback">
                                                Password is required
                                            </div>
                                        </div>
                                        <div class="mb-3 label-input-group">
                                            <label class="text-muted" for="newPassword">New Password:</label>
                                            <div class="input-group">
                                                <input id="newPassword" type="password" class="form-control" name="newPassword" required>
                                                <button class="btn btn-outline-secondary" type="button" onclick="togglePasswordVisibility('newPassword')">
                                                    <i class="bi bi-eye"></i>
                                                </button>
                                            </div>
                                            <div class="invalid-feedback">
                                                New Password is required
                                            </div>
                                        </div>
                                        <div class="mb-3 label-input-group">
                                            <label class="text-muted" for="confirmNewPassword">Confirm New Password: </label>
                                            <div class="input-group">
                                                <input id="confirmNewPassword" type="password" class="form-control" name="confirmNewPassword" required>
                                                <button class="btn btn-outline-secondary" type="button" onclick="togglePasswordVisibility('confirmNewPassword')">
                                                    <i class="bi bi-eye"></i>
                                                </button>
                                            </div>
                                            <div class="invalid-feedback">
                                                Password confirmation is required
                                            </div>
                                        </div>
                                        <c:if test="${not empty errorCurrentPassword}">
                                            <div class="alert alert-danger" role="alert">
                                                ${errorCurrentPassword}
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty errorInvalidPassword}">
                                            <div class="alert alert-danger" role="alert">
                                                ${errorInvalidPassword}
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty errorResetPassword}">
                                            <div class="alert alert-danger" role="alert">
                                                ${errorResetPassword}
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty successResetPassword}">
                                            <div class="alert alert-success" role="alert">
                                                ${successResetPassword}
                                            </div>
                                        </c:if>

                                        <div class="d-flex align-items-center">
                                            <button type="submit" class="btn btn-primary ms-auto">
                                                Reset Password	
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="text-center mt-5 text-muted">
                            Copyright &copy; 2017-2021 &mdash; Your Company 
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-pzjw8o+znN1aD7eD8ETvTcOzQFg9AdwC2UihgC+deR+U4IqAsM85viWoWiHMEU98" crossorigin="anonymous"></script>
        <script>
                                                    function togglePasswordVisibility(id) {
                                                        const passwordInput = document.getElementById(id);

                                                        if (passwordInput.type === 'password') {
                                                            passwordInput.type = 'text';
                                                        } else {
                                                            passwordInput.type = 'password';
                                                        }
                                                    }

                                                    document.addEventListener('DOMContentLoaded', function () {
                                                        const form = document.querySelector('form');

                                                        form.addEventListener('submit', function (event) {
                                                            const currentPassword = document.getElementById('currentPassword').value;
                                                            const newPassword = document.getElementById('newPassword').value;
                                                            const confirmNewPassword = document.getElementById('confirmNewPassword').value;

                                                            if (currentPassword.trim() === '' || newPassword.trim() === '' || confirmNewPassword.trim() === '') {
                                                                alert('Please fill in all the required fields');
                                                                event.preventDefault();
                                                            } else if (newPassword !== confirmNewPassword) {
                                                                alert('New password and confirm password do not match');
                                                                event.preventDefault();
                                                            } else {
                                                                // Ask for confirmation before submitting the form
                                                                const userConfirmed = confirm('Are you sure you want to submit the form?');

                                                                if (!userConfirmed) {
                                                                    event.preventDefault();
                                                                }
                                                            }
                                                        });
                                                    });
        </script>

    </body>
</html>
