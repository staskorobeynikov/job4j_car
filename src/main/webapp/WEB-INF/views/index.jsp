<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>All Adverts</title>
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
<style>
    .account {
        position: fixed;
        right: 1px;
        top: 1px;
    }
</style>
<body>
<script>
    function showWithPhoto() {
        let selector = $("#fPhoto");
        if (selector.prop("checked")) {
            document.location.href = "${pageContext.servletContext.contextPath}/withphoto";
        } else {
            document.location.href = "${pageContext.servletContext.contextPath}/";
        }
    }

    function showLastDay() {
        let selector = $("#lDay");
        if (selector.prop("checked")) {
            document.location.href = "${pageContext.servletContext.contextPath}/lastday";
        } else {
            document.location.href = "${pageContext.servletContext.contextPath}/";
        }
    }
</script>
<script>
    function enter() {
        window.location.href = "${pageContext.servletContext.contextPath}/authorization";
    }
</script>
<div class="container">
    <div class="form-inline">
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" value="lDay" name="lDay" id="lDay" onchange="showLastDay()">
            <label class="form-check-label" for="lDay" style="font-weight: 900">Show last day</label>
            <c:if test="${day}">
                <script>
                    document.getElementById("lDay").checked = ${day};
                </script>
            </c:if>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" value="fPhoto" name="fPhoto" id="fPhoto" onchange="showWithPhoto()">
            <label class="form-check-label" for="fPhoto" style="font-weight: 900">Show with photo</label>
            <c:if test="${photo}">
                <script>
                    document.getElementById("fPhoto").checked = ${photo};
                </script>
            </c:if>
        </div>
        <label class="col-form-label col-sm-2" for="mark" style="font-weight: 900">Show cars mark</label>
        <div class="col">
            <select class="custom-select" id="mark" name="mark">
                <option value></option>
                <option value="Toyota">Toyota</option>
                <option value="Mercedes">Mercedes</option>
                <option value="Renault">Renault</option>
                <option value="Kia">Kia</option>
                <option value="Volvo">Volvo</option>
            </select>
            <script>
                $(".custom-select").change(function () {
                    let selectMark = $(this).val();
                    if (selectMark !== '') {
                        document.location.href = "${pageContext.servletContext.contextPath}/marks/" + selectMark;
                    }
                });
            </script>
            <c:if test="${mark != ''}">
                <script>
                    document.getElementById("mark").value = "${mark}";
                </script>
            </c:if>
        </div>
    </div>
    <div class="account">
        <button id="add" type="button" class="btn btn-outline-dark" onclick="enter()">Add</button>
    </div>
</div>
<div class="container">
    <table class="table table-striped, table-bordered" id="allAdverts">
        <thead class="thead-dark">
        <tr align="middle">
            <th scope="col">Car</th>
            <th scope="col">Price</th>
            <th scope="col">Seller</th>
            <th scope="col">Photo</th>
            <th scope="col">Date add</th>
            <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adverts}" var="advert">
            <tr>
                <td>
                        ${advert.car.mark.name} ${advert.car.model.name} <br><br>
                    <button id="${advert.id}" type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#carInfo">Details</button>
                    <script>
                        $("#${advert.id}").click(function () {
                            let id = $(this).attr("id");
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.servletContext.contextPath}/cars/' + id,
                                dataType: 'json',
                                success: function (data) {
                                    addCarInfo(data);
                                }
                            })
                        });

                        function addCarInfo(data) {
                            let car = data;
                            document.getElementById("infoLabel").innerHTML = car.mark.name + "\n" + car.model.name;
                            let result = "<tr><td>Mile age, km</td><td>" + car.mileAge + "</td></tr>";
                            result += "<tr><td>Date created</td><td>" + car.created + "</td></tr>";
                            result += "<tr><td>Gear box</td><td>" + car.transmission.gearBox + "</td></tr>";
                            result += "<tr><td>Gear type</td><td>" + car.transmission.gearType + "</td></tr>";
                            result += "<tr><td>Car body type</td><td>" + car.carBody.type + "</td></tr>";
                            result += "<tr><td>Car body color</td><td>" + car.carBody.color + "</td></tr>";
                            result += "<tr><td>Count door</td><td>" + car.carBody.countDoor + "</td></tr>";
                            result += "<tr><td>Engine volume, l</td><td>" + car.engine.volume + "</td></tr>";
                            result += "<tr><td>Engine power, hp</td><td>" + car.engine.power + "</td></tr>";
                            result += "<tr><td>Engine type</td><td>" + car.engine.type + "</td></tr>";
                            document.getElementById("info").innerHTML = result;
                        }
                    </script>
                </td>
                <td>${advert.price}</td>
                <td>${advert.owner.name} <br><br>
                    <button id="user${advert.id}" type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#userInfo">Details</button>
                    <script>
                        $("#user${advert.id}").click(function () {
                            let id = ${advert.owner.id};
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.servletContext.contextPath}/users/' + id,
                                dataType: 'json',
                                success: function (data) {
                                    addUserInfo(data);
                                }
                            })
                        });

                        function addUserInfo(data) {
                            let user = data;
                            document.getElementById("userInfoLabel").innerHTML = user.name;
                            let result = "<tr><td>Phone</td><td>" + user.phone + "</td></tr>";
                            result += "<tr><td>Address</td><td>" + user.address + "</td></tr>";
                            document.getElementById("userTable").innerHTML = result;
                        }
                    </script>
                </td>
                <td>
                    <c:if test="${advert.imageName != ''}">
                        <img src="${pageContext.servletContext.contextPath}/download?name=${advert.imageName}" width="200px" height="150px">
                    </c:if>
                </td>
                <td>${advert.createdDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${advert.status}">
                            Продано
                        </c:when>
                        <c:otherwise>
                            В продаже
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal fade" id="carInfo" tabindex="-1" role="dialog" aria-labelledby="infoLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-striped, table-bordered">
                    <tbody id="info">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="userInfo" tabindex="-1" role="dialog" aria-labelledby="userInfoLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userInfoLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-striped, table-bordered">
                    <tbody id="userTable">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
