<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>UserAdverts</title>
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
    function returnStartPage() {
        setTimeout(function () {
            window.location.href = "${pageContext.servletContext.contextPath}/logout";
        }, 1000);
    }

    function redirectAddAdvert() {
        setTimeout(function () {
            window.location.href = "${pageContext.servletContext.contextPath}/add/" + ${findUser.id};
        }, 1000);
    }
</script>
<body>
<h3 style="text-align: center">List our adverts</h3>
<div class="container">
    <table class="table table-striped, table-bordered" id="adverts">
        <thead class="thead-dark">
        <tr style="text-align: center">
            <th style="width: 25%" scope="col">Car</th>
            <th style="width: 15%" scope="col">Price</th>
            <th style="width: 40%" scope="col">Photo</th>
            <th style="width: 20%" scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adverts}" var="advert">
            <tr>
                <td>${advert.car.mark.name} ${advert.car.model.name}</td>
                <td>${advert.price}</td>
                <td>
                    <c:if test="${advert.imageName != ''}">
                        <img src="${pageContext.servletContext.contextPath}/download?name=${advert.imageName}" width="200px" height="150px">
                    </c:if>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${advert.status}">
                            Продано
                        </c:when>
                        <c:otherwise>
                            В продаже
                        </c:otherwise>
                    </c:choose>
                    <br><br>
                    <button type="button" class="btn btn-dark" id="advert${advert.id}">Change status</button>
                    <script>
                        $("#advert${advert.id}").click(function () {
                            let id = ${advert.id};
                            let userId = ${advert.owner.id};
                            $.ajax({
                                type: 'POST',
                                url: "${pageContext.servletContext.contextPath}/update/" + id + "/" + true + "/owner/" + userId,
                                success: function (data) {
                                    location.reload();
                                }
                            })
                        });
                    </script>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="form-group row">
    <label class="col-form-label col-sm-1" style="font-weight: 900"></label>
    <div class="col">
        <button id="add" type="button" class="btn btn-dark" onclick="redirectAddAdvert()">Add new advert</button>
    </div>
    <div class="col-sm-9">
        <button id="return" type="button" class="btn btn-dark" onclick="returnStartPage()">Log out</button>
    </div>
</div>
</body>
</html>
