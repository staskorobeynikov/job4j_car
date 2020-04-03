package ru.job4j.carmarket.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carmarket.model.Account;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class RegistrationController {

    private final Service<Advert, Car, User> service;

    @Autowired
    public RegistrationController(@Qualifier("crud") Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);

        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);

        service.addUser(user, account);

        return "redirect:/authorization";
    }

    @RequestMapping(value = "/logins", method = RequestMethod.POST)
    public void validateLogin(@RequestParam Map<String, String> map, HttpServletResponse resp) throws IOException {
        String response = "";
        String login = map.get("login");

        Account account = new Account();
        account.setLogin(login);

        if (!service.validateAccount(account)) {
            response = "Enter another login.";
        }

        String json = new Gson().toJson(response);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
