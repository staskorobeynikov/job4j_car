package ru.job4j.carmarket.web;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carmarket.domain.User;
import ru.job4j.carmarket.service.CrudAdvertsService;
import ru.job4j.carmarket.service.MyUserDetailsService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudAdvertsService service;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private MyUserDetailsService userService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenAddUserPostThanAddNewUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");

        given(this.encoder.encode(user.getPassword())).willReturn("123");

        this.mockMvc.perform(
            post("/addUser").flashAttr("user", user)
        ).andExpect(status().is3xxRedirection());

        verify(this.service, times(1)).addUser(user);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenValidateLoginPostThanReturnContentJSONType() throws Exception {
        String response = "Enter another login.";

        User user = new User();
        user.setUsername("user");

        given(this.service.validateUser(user)).willReturn(false);

        this.mockMvc.perform(
                post("/logins/user").accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(new Gson().toJson(response))
        );
    }

}