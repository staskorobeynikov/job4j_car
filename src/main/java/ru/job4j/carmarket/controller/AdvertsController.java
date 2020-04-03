package ru.job4j.carmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

import java.util.Map;

@Controller
public class AdvertsController {

    private final Service<Advert, Car, User> service;

    @Autowired
    public AdvertsController(@Qualifier("crud") Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateStatus(@RequestParam Map<String, String> map, ModelMap modelMap) {
        String id = map.get("id");
        String status = map.get("status");
        String userId = map.get("user");
        Advert advert = new Advert();
        advert.setId(Integer.parseInt(id));
        advert.setStatus(Boolean.parseBoolean(status));
        service.updateStatus(advert);
        modelMap.addAttribute("adverts", service.getAdvertsUser(service.getUser(Integer.parseInt(userId))));
        return "adverts";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String redirectAdd(@RequestParam Map<String, String> map, ModelMap modelMap) {
        String id = map.get("user");
        User user = service.getUser(Integer.parseInt(id));
        modelMap.addAttribute("addUser", user);
        return "newadvert";
    }
}
