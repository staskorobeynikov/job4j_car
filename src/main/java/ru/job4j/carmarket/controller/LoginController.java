package ru.job4j.carmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carmarket.model.Account;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

@Controller
public class LoginController {

    private final Service<Advert, Car, User> service;

    @Autowired
    public LoginController(@Qualifier("crud") Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String validateAccount(@ModelAttribute Account account, ModelMap modelMap) {
        String result = "adverts";
        User user = service.isCredential(account);
        if (user == null) {
            modelMap.addAttribute("error", "User not found. Check your login and password.");
            result = "login";
        } else {
            modelMap.addAttribute("findUser", user);
            modelMap.addAttribute("adverts", service.getAdvertsUser(user));
        }
        return result;
    }

    @RequestMapping(value = "/redirecting", method = RequestMethod.GET)
    public String redirect() {
        return "registration";
    }
}
