package ru.job4j.carmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carmarket.model.Advert;
import ru.job4j.carmarket.model.Car;
import ru.job4j.carmarket.model.User;
import ru.job4j.carmarket.service.Service;

@Controller
public class AdvertsController {

    private final Service<Advert, Car, User> service;

    @Autowired
    public AdvertsController(@Qualifier("crud") Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/update/{id}/{status}/owner/{ownerId}", method = RequestMethod.POST)
    public String updateStatus(@ModelAttribute Advert advert, @PathVariable("ownerId") String ownerId, ModelMap modelMap) {
        service.updateStatus(advert);
        modelMap.addAttribute("adverts", service.getAdvertsUser(service.getUser(Integer.parseInt(ownerId))));
        return "adverts";
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String redirectAdd(@ModelAttribute User user, ModelMap modelMap) {
        modelMap.addAttribute("addUser", service.getUser(user.getId()));
        return "newadvert";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String showAdvertsUser(ModelMap modelMap) {
        String result = "login";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")) {
            User user = new User();
            user.setUsername(username);
            modelMap.addAttribute("adverts", service.getAdvertsUser(user));
            modelMap.addAttribute("findUser", service.findByUsername(username));
            result = "adverts";
        }
        return result;
    }
}
