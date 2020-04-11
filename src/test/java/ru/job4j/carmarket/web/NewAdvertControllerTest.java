package ru.job4j.carmarket.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.job4j.carmarket.domain.*;
import ru.job4j.carmarket.service.CrudAdvertsService;
import ru.job4j.carmarket.service.MyUserDetailsService;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NewAdvertController.class)
public class NewAdvertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudAdvertsService service;

    @MockBean
    private MyUserDetailsService userService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenPostAdds() throws Exception {
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

        Map<String, Object> attrs = new LinkedHashMap<>();
        attrs.put("mark", mark);
        attrs.put("model", model);
        attrs.put("carBody", carBody);
        attrs.put("engine", engine);
        attrs.put("transmission", transmission);
        attrs.put("car", car);
        attrs.put("authentication", user);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("price", "5700000");
        params.add("createdDate", "2017-01-01");

        String userName = user.getUsername();
        given(this.service.findByUsername(userName)).willReturn(user);

        this.mockMvc.perform(
                post("/adds").flashAttrs(attrs).params(params)
        ).andExpect(status().is3xxRedirection());

        verify(this.service, times(1)).addNewAdvert(carBody, engine, transmission, mark, model, user, car, advert);
    }
}