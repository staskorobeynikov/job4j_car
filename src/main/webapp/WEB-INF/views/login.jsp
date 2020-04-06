<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Login</title>
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
    function registration() {
        setTimeout(function () {
            window.location.href = "${pageContext.servletContext.contextPath}/redirecting";
        }, 500);
    }
</script>
<body>
<div>
    <c:if test="${error != ''}">
        <div style="background-color:red; text-align:center">
            <c:out value="${error}"/>
        </div>
    </c:if>
</div>
<form class="container" action="${pageContext.servletContext.contextPath}/login" method="post">
    <div class="form-group row">
        <label class="col-form-label col-sm-1" for="username" style="font-weight: 900">Login</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="username" name="username" placeholder="Enter login" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-1" for="password" style="font-weight: 900">Password</label>
        <div class="col-sm-5">
            <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-1" style="font-weight: 900"></label>
        <div class="col">
            <button type="submit" class="btn btn-dark">Login account</button>
        </div>
        <div class="col-sm-9">
            <button type="button" class="btn btn-dark" onclick="registration()">Registration</button>
        </div>
    </div>
</form>
</body>
</html>
