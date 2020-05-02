package ru.job4j.carmarket.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.carmarket.domain.*;
import ru.job4j.carmarket.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudAdvertsServiceTest {

    @MockBean
    private AdvertRepository advertRepository;

    @MockBean
    private CarBodyRepository carBodyRepository;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private EngineRepository engineRepository;

    @MockBean
    private MarkRepository markRepository;

    @MockBean
    private ModelRepository modelRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TransmissionRepository transmissionRepository;

    @Autowired
    private CrudAdvertsService service;

    private List<Advert> list = new ArrayList<>();

    @Before
    public void init() {
        Mark mark = new Mark();
        mark.setId(1);
        mark.setMarkName("mercedes");

        Model model = new Model();
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
        car.setCreated(new Timestamp(1483272000000L));
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
        advert.setId(1);
        advert.setPrice(5700000);
        advert.setImageName("");
        advert.setCar(car);
        advert.setOwner(user);
        advert.setStatus(false);
        advert.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        list.add(advert);
    }

    @Test
    public void whenTestMethodFindAll() {
        given(this.advertRepository.findAll()).willReturn(list);

        List<Advert> list = service.findAll();

        assertThat(list.get(0).getOwner().getName(), is("Stas"));
    }

    @Test
    public void whenCallMethodShowWithPhoto() {
        given(this.advertRepository.findByImageNameIsNot("")).willReturn(list);

        List<Advert> list = service.showWithPhoto();

        assertThat(list.get(0).getPrice(), is(5700000));
    }

    @Test
    public void whenCallMethodShowLastDay() {
        Timestamp findDate = new Timestamp(System.currentTimeMillis() - 86400000L);
        given(this.advertRepository.findByCreatedDateAfter(findDate)).willReturn(list);

        List<Advert> adverts = service.showLastDay();

        assertThat(adverts.size(), is(1));
    }

    @Test
    public void whenCallMethodShowWithSpecificMark() {
        String markName = "mercedes";
        given(this.advertRepository.findByCar_Mark_MarkName(markName)).willReturn(list);

        Mark mark = new Mark();
        mark.setMarkName(markName);
        List<Advert> adverts = service.showWithSpecificMark(mark);

        assertThat(adverts.get(0).getCar().getCarBody().getColor(), is("black"));
    }

    @Test
    public void whenTestMethodGetCarById() {
        int id = 1;
        given(this.carRepository.findCarById(id)).willReturn(list.get(0).getCar());

        Car findCar = service.getCar(id);

        assertThat(findCar.getCarBody().getType(), is("sedan"));
        assertThat(findCar.getEngine().getEngineType(), is("petrol"));
    }

    @Test
    public void whenTestMethodGetUserById() {
        int id = 1;
        given(this.userRepository.findUserById(id)).willReturn(list.get(0).getOwner());

        User findUser = service.getUser(id);

        assertThat(findUser.getPhone(), is("+375296666666"));
        assertThat(findUser.getRole(), is("USER"));
    }

    @Test
    public void whenMethodGetAdvertsUser() {
        int id = 1;
        String userName = "user";
        given(this.userRepository.findByUsername(userName)).willReturn(list.get(0).getOwner());
        given(this.advertRepository.findAdvertByOwner_Id(id)).willReturn(list);

        User user = new User();
        user.setId(id);
        user.setUsername(userName);
        List<Advert> adverts = service.getAdvertsUser(user);

        assertThat(adverts.get(0).getCar().getMileAge(), is(25000));
        assertThat(adverts.get(0).getCar().getTransmission().getGearBox(), is("automatic"));
        assertThat(adverts.get(0).getCar().getTransmission().getGearType(), is("four-wheel"));
    }

    @Test
    public void whenMethodAddUserIsCall() {
        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");
        given(this.userRepository.save(user)).willReturn(list.get(0).getOwner());

        User saveUser = service.addUser(user);

        assertThat(saveUser.getAddress(), is("Slutsk"));
    }

    @Test
    public void whenCallMethodValidateUserIsFalse() {
        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");
        given(this.userRepository.findByUsername(user.getUsername())).willReturn(list.get(0).getOwner());

        boolean result = service.validateUser(user);

        assertThat(result, is(false));
    }

    @Test
    public void whenCallMethodFindByUsername() {
        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");

        given(this.userRepository.findByUsername(user.getUsername())).willReturn(list.get(0).getOwner());

        User findUser = service.findByUsername(user.getUsername());

        assertThat(findUser.getPhone(), is("+375296666666"));
    }

    @Test
    public void whenTestMethodGetAllMarks() {
        List<Mark> marks = new ArrayList<>();
        marks.add(list.get(0).getCar().getMark());
        given(this.markRepository.findAll()).willReturn(marks);

        List<Mark> result = service.findAllMarks();

        assertThat(result.get(0).getMarkName(), is("mercedes"));
    }

    @Test
    public void whenTestMethodAddNewAdvert() {
        int id = 1;

        given(this.markRepository.findMarkByMarkName("mercedes")).willReturn(null);
        given(this.markRepository.save(list.get(0).getCar().getMark())).willReturn(list.get(0).getCar().getMark());

        given(this.modelRepository.findModelByModelName("S600")).willReturn(null);
        given(this.modelRepository.save(list.get(0).getCar().getModel())).willReturn(list.get(0).getCar().getModel());

        given(this.engineRepository.findEngineByVolumeAndEngineTypeAndPower(
                6.0, "petrol", 535
        )).willReturn(null);
        given(this.engineRepository.save(list.get(0).getCar().getEngine())).willReturn(list.get(0).getCar().getEngine());

        given(this.transmissionRepository.findTransmissionByGearBoxAndGearType(
                "automatic", "four-wheel"
        )).willReturn(null);
        given(this.transmissionRepository.save(list.get(0).getCar().getTransmission())).willReturn(list.get(0).getCar().getTransmission());

        given(this.carBodyRepository.findCarBodyByColorAndTypeAndCountDoor(
                "black", "sedan", 5
        )).willReturn(null);
        given(this.carBodyRepository.save(list.get(0).getCar().getCarBody())).willReturn(list.get(0).getCar().getCarBody());

        given(this.carRepository.save(list.get(0).getCar())).willReturn(list.get(0).getCar());

        given(this.userRepository.findUserById(id)).willReturn(list.get(0).getOwner());

        given(this.advertRepository.save(list.get(0))).willReturn(list.get(0));

        service.addNewAdvert(
                list.get(0).getCar().getCarBody(),
                list.get(0).getCar().getEngine(),
                list.get(0).getCar().getTransmission(),
                list.get(0).getCar().getMark(),
                list.get(0).getCar().getModel(),
                list.get(0).getOwner(),
                list.get(0).getCar(),
                list.get(0)
        );

        verify(advertRepository).save(any(Advert.class));
    }

    @Test
    public void whenTestMethodUpdateStatusAdvert() {
        Advert advert = mock(Advert.class);

        given(this.advertRepository.findAdvertById(advert.getId())).willReturn(list.get(0));
        given(advert.isStatus()).willReturn(true);
        given(this.advertRepository.save(advert)).willReturn(list.get(0));

        service.updateStatus(advert);

        verify(this.advertRepository).save(any(Advert.class));
    }
}