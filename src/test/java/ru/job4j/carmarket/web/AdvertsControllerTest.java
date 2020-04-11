package ru.job4j.carmarket.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carmarket.domain.*;
import ru.job4j.carmarket.service.CrudAdvertsService;
import ru.job4j.carmarket.service.MyUserDetailsService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AdvertsController.class)
public class AdvertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudAdvertsService service;

    @MockBean
    private MyUserDetailsService userService;

    private List<Advert> list = new ArrayList<>();

    @Before
    public void init() {
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
        advert.setId(0);
        advert.setPrice(5700000);
        advert.setImageName("");
        advert.setCar(car);
        advert.setOwner(user);
        advert.setStatus(false);
        advert.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        list.add(advert);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenRedirectAddPageThanViewNewAdvert() throws Exception {
        int userId = 1;

        given(this.service.getUser(userId)).willReturn(list.get(0).getOwner());

        this.mockMvc.perform(get("/add/1").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("newadvert"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenUpdateStatusPostThanViewAdverts() throws Exception {
        int userId = 1;

        given(this.service.getUser(userId)).willReturn(list.get(0).getOwner());
        given(this.service.getAdvertsUser(this.service.getUser(userId))).willReturn(list);

        this.mockMvc.perform(post("/update/0/true/owner/1").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("adverts"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenShowAdvertsUserGetThanViewAdverts() throws Exception {
        String userName = "user";

        User user = list.get(0).getOwner();

        given(this.service.findByUsername(userName)).willReturn(list.get(0).getOwner());
        given(this.service.getAdvertsUser(user)).willReturn(list);

        this.mockMvc.perform(get("/authorization").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("adverts"));
    }
}