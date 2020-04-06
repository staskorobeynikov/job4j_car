package ru.job4j.carmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getView(@RequestParam(value = "error", required = false) String error, Model model) {
        String result = "login";
        if (error != null) {
            model.addAttribute("error", "User not found. Check your login and password.");
        }
        return result;
    }

    @RequestMapping(value = "/redirecting", method = RequestMethod.GET)
    public String redirect() {
        return "registration";
    }
}
