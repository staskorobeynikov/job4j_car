package ru.job4j.carmarket.domain;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AdvertTest {

    @Test
    public void whenTestMethodToString() {
        Mark mark = new Mark();
        mark.setId(1);
        mark.setMarkName("mercedes");

        Model model = new Model();
        model.setId(1);
        model.setModelName("S600");

        Engine engine = new Engine();
        engine.setId(1);
        engine.setEngineType("petrol");
        engine.setPower(535);
        engine.setVolume(6.0);

        Transmission transmission = new Transmission();
        transmission.setId(1);
        transmission.setGearBox("automatic");
        transmission.setGearType("four-wheel");

        CarBody carBody = new CarBody();
        carBody.setId(1);
        carBody.setColor("black");
        carBody.setCountDoor(5);
        carBody.setType("sedan");

        Car car = new Car();
        car.setId(1);
        car.setMileAge(25000);
        car.setCarBody(carBody);
        car.setEngine(engine);
        car.setMark(mark);
        car.setModel(model);
        car.setTransmission(transmission);

        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");

        Advert advert = new Advert();
        advert.setId(0);
        advert.setPrice(5700000);
        advert.setCar(car);
        advert.setOwner(user);
        advert.setStatus(false);

        String expected = "Advert: id=0, owner=User: id=1, name=Stas, phone=+375296666666, "
                + "address=Slutsk, username=user, password=123, role=USER, car=Car: id=1, "
                + "mark=Mark: id=1, name=mercedes., model=Model: id=1, name=S600, mileAge=25000, "
                + "created=null, transmission=Transmission: id=1, gearBox=automatic, gearType=four-wheel., "
                + "carBody=CarBody: id=1, type=sedan, color=black, countDoor=5., engine=Engine: id=1, volume=6.0, "
                + "power=535, type=petrol., imageName=null, price=5700000, createdDate=null, status=false";
        assertThat(advert.toString(), is(expected));
    }

    @Test
    public void whenTestAllMethodsEqualsAndHashCode() {
        Mark mark = new Mark();
        mark.setId(1);
        mark.setMarkName("mercedes");

        Mark newMark = mark;

        Model model = new Model();
        model.setId(1);
        model.setModelName("S600");

        Model newModel = model;

        Engine engine = new Engine();
        engine.setId(1);
        engine.setEngineType("petrol");
        engine.setPower(535);
        engine.setVolume(6.0);

        Engine newEngine = engine;

        Transmission transmission = new Transmission();
        transmission.setId(1);
        transmission.setGearBox("automatic");
        transmission.setGearType("four-wheel");

        Transmission newTransmission = transmission;

        CarBody carBody = new CarBody();
        carBody.setId(1);
        carBody.setColor("black");
        carBody.setCountDoor(5);
        carBody.setType("sedan");

        CarBody newCarBody = carBody;

        Car car = new Car();
        car.setId(1);
        car.setMileAge(25000);
        car.setCarBody(carBody);
        car.setEngine(engine);
        car.setMark(mark);
        car.setModel(model);
        car.setTransmission(transmission);

        Car newCar = car;

        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");

        User newUser = user;

        Advert advert = new Advert();
        advert.setId(0);
        advert.setPrice(5700000);
        advert.setCar(car);
        advert.setOwner(user);
        advert.setStatus(false);

        Advert newAdvert = advert;

        Map<Object, String> map = new HashMap<>();
        map.put(advert, "first");
        map.put(newAdvert, "second");
        map.put(user, "first");
        map.put(newUser, "second");
        map.put(car, "first");
        map.put(newCar, "second");
        map.put(carBody, "first");
        map.put(newCarBody, "second");
        map.put(transmission, "first");
        map.put(newTransmission, "second");
        map.put(engine, "first");
        map.put(newEngine, "second");
        map.put(mark, "first");
        map.put(newMark, "second");
        map.put(model, "first");
        map.put(newModel, "second");

        assertThat(map.size(), is(8));
    }
}