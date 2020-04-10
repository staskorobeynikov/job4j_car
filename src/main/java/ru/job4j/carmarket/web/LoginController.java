package ru.job4j.carmarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
