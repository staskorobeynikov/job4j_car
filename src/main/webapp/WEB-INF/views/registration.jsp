<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous">
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).on("keyup", ":password", function () {
        let first = $("#password").val();
        let second = $("#cor_password").val();
        if (first !== second) {
            $("#cor_password").attr("style", "background-color: red");
            $("#submit").attr("disabled", "disabled");
            $(".error").html("Passwords do not match");
        } else {
            $("#submit").removeAttr("disabled");
            $("#cor_password").removeAttr("style");
            $(".error").html("");
        }
    });
</script>
<body>
<div class="error" style="text-align: center">
    <div style="background-color:red" id="invalid">
    </div>
    <div style="background-color:lightgreen" id="valid">
    </div>
</div>
<form action="${pageContext.servletContext.contextPath}/addUser" method="post" class="container">
    <div class="col-sm-5">
        <h3>Fill the form for registration</h3>
    </div>
    <div>
        <br/>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="name" style="font-weight: 900">Your name</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="phone" style="font-weight: 900">Your phone number</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter phone number" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="address" style="font-weight: 900">Your address</label>
        <div class="col-sm-5">
            <textarea rows="3" class="form-control" id="address" name="address" placeholder="Enter address" required></textarea>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="login" style="font-weight: 900">Your login</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="login" name="login" placeholder="Enter login" required>
        </div>
        <script>
            $("#login").focusout(function () {
                let selectLogin = $(this).val();
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.servletContext.contextPath}/logins/' + selectLogin,
                    dataType: 'json',
                    success: function (data) {
                        let valid;
                        if (data !== '') {
                            $('#valid').empty();
                            valid = "<c:out value="Invalid user login"/>";
                            $("#login").val('');
                            $('#invalid').html(valid);
                        } else {
                            $('#invalid').empty();
                            valid = "<c:out value="Correctly user login"/>";
                            $('#valid').html(valid);
                        }
                    }
                })
            })
        </script>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="password" style="font-weight: 900">Your password</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" id="password" name="password" placeholder="Enter password">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="cor_password" style="font-weight: 900">Repeat password</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" id="cor_password" name="cor_password" placeholder="Enter password again">
        </div>
        <div class="error">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" style="font-weight: 900"></label>
        <div class="col-sm-5">
            <button type="submit" id="submit" class="btn btn-dark">Add new user</button>
        </div>
    </div>
</form>
</body>
</html>
