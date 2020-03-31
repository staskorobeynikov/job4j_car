<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>AddNewAdvert</title>
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
<body>
<form action="${pageContext.servletContext.contextPath}/adds" method="post" class="container" enctype="multipart/form-data">
    <div class="col-sm-5">
        <h3>Form for add new advert</h3>
    </div>
    <div>
        <br/>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="mark" style="font-weight: 900">Mark car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="mark" name="mark" placeholder="Enter mark car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="model" style="font-weight: 900">Model car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="model" name="model" placeholder="Enter model car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="mile_age" style="font-weight: 900">Mile age car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="mile_age" name="mile_age" placeholder="Enter mile age car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="created" style="font-weight: 900">Date created car</label>
        <div class="col-sm-5">
            <input type="date" class="form-control" id="created" name="created" placeholder="Enter date created car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="price" style="font-weight: 900">Price car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="price" name="price" placeholder="Enter price car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="gear_type" style="font-weight: 900">Gear type car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="gear_type" name="gear_type" required>
                <option value="front">Front</option>
                <option value="rear">Rear</option>
                <option value="four-wheel">Four-wheel</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="gear_box" style="font-weight: 900">Gear box car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="gear_box" name="gear_box" required>
                <option value="automatic">Automatic</option>
                <option value="mechanic">Mechanic</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="car_body_type" style="font-weight: 900">Body type car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="car_body_type" name="car_body_type" required>
                <option value="sedan">Sedan</option>
                <option value="hatchback">Hatchback</option>
                <option value="station wagon">Station wagon</option>
                <option value="minivan">Minivan</option>
                <option value="coupe">Coupe</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="car_body_color" style="font-weight: 900">Body color car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="car_body_color" name="car_body_color" required>
                <option value="black">Black</option>
                <option value="red">Red</option>
                <option value="blue">Blue</option>
                <option value="white">White</option>
                <option value="yellow">Yellow</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="car_body_doors" style="font-weight: 900">Body doors car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="car_body_doors" name="car_body_doors" required>
                <option value="3">Three</option>
                <option value="5">Five</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="engine_volume" style="font-weight: 900">Engine volume car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="engine_volume" name="engine_volume" placeholder="Enter engine volume car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="engine_power" style="font-weight: 900">Engine power car</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="engine_power" name="engine_power" placeholder="Enter engine power car" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="engine_type" style="font-weight: 900">Engine type car</label>
        <div class="col-sm-5">
            <select class="custom-select" id="engine_type" name="engine_type" required>
                <option value="petrol">Petrol</option>
                <option value="diesel">Diesel</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" for="photo" style="font-weight: 900">Photo car</label>
        <div class="col-sm-5">
            <input type="file" class="form-control" id="photo" name="photo">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" style="font-weight: 900"></label>
        <div class="col-sm-5">
            <input type="hidden" class='form-control' id='id' name='id' value="${addUser.id}">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-form-label col-sm-2" style="font-weight: 900"></label>
        <div class="col-sm-5">
            <button type="submit" class="btn btn-dark">Add advert</button>
        </div>
    </div>
</form>
</body>
</html>
