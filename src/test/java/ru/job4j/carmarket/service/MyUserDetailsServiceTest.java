package ru.job4j.carmarket.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.carmarket.domain.User;
import ru.job4j.carmarket.repository.UserRepository;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyUserDetailsServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private MyUserDetailsService service;

    private User user = new User();

    @Before
    public void setUp() {
        user.setId(1);
        user.setName("Stas");
        user.setPassword("123");
        user.setRole("USER");
        user.setAddress("Slutsk");
        user.setPhone("+375296666666");
        user.setUsername("user");
    }

    @Test
    public void whenLoadUserByUsernameThanDetailsNotNull() {
        given(this.repository.findByUsername(user.getUsername())).willReturn(user);

        UserDetails details = service.loadUserByUsername("user");

        assertNotNull(details);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenLoadUserThanUsernameNotFoundException() {
        given(this.repository.findByUsername(user.getUsername())).willReturn(null);

        service.loadUserByUsername("user");
    }
}