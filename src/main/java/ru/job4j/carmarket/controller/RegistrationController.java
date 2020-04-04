package ru.job4j.carmarket.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carmarket.model.Account;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegistrationController {

    private final Service<Advert, Car, User> service;

    @Autowired
    public RegistrationController(@Qualifier("crud") Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute Account account, @ModelAttribute User user) {
        service.addUser(user, account);
        return "redirect:/authorization";
    }

    @RequestMapping(value = "/logins/{login}", method = RequestMethod.POST)
    public void validateLogin(@ModelAttribute Account account, HttpServletResponse resp) throws IOException {
        String response = "";
        if (!service.validateAccount(account)) {
            response = "Enter another login.";
        }

        String json = new Gson().toJson(response);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
