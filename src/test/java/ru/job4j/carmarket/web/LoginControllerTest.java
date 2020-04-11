package ru.job4j.carmarket.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carmarket.service.CrudAdvertsService;
import ru.job4j.carmarket.service.MyUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudAdvertsService service;

    @MockBean
    private MyUserDetailsService userService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenRedirectRegistrationPageThanViewRegistration() throws Exception {
        this.mockMvc.perform(get("/redirecting").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenGetViewThanViewLogin() throws Exception {
        this.mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}