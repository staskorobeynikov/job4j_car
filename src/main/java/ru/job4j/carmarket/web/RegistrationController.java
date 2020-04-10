package ru.job4j.carmarket.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carmarket.domain.Advert;
import ru.job4j.carmarket.domain.Car;
import ru.job4j.carmarket.domain.User;
import ru.job4j.carmarket.service.ServiceInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegistrationController {

    private final ServiceInterface<Advert, Car, User> service;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(ServiceInterface<Advert, Car, User> service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.addUser(user);
        return "redirect:/authorization";
    }

    @RequestMapping(value = "/logins/{username}", method = RequestMethod.POST)
    public void validateLogin(@ModelAttribute User user, HttpServletResponse resp) throws IOException {
        String response = "";
        if (!service.validateUser(user)) {
            response = "Enter another login.";
        }
        String json = new Gson().toJson(response);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
