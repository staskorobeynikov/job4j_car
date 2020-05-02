package ru.job4j.carmarket.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carmarket.domain.Advert;
import ru.job4j.carmarket.domain.Car;
import ru.job4j.carmarket.domain.Mark;
import ru.job4j.carmarket.domain.User;
import ru.job4j.carmarket.service.ServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class IndexController {

    private final ServiceInterface<Advert, Car, User> service;

    @Autowired
    public IndexController(ServiceInterface<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll(ModelMap modelMap) {
        modelMap.addAttribute("adverts", service.findAll());
        modelMap.addAttribute("marks", service.findAllMarks());
        return "index";
    }

    @RequestMapping(value = "/withphoto", method = RequestMethod.GET)
    public String showWithPhoto(ModelMap modelMap) {
        modelMap.addAttribute("adverts", service.showWithPhoto());
        modelMap.addAttribute("photo", true);
        modelMap.addAttribute("marks", service.findAllMarks());
        return "index";
    }

    @RequestMapping(value = "/lastday", method = RequestMethod.GET)
    public String showLastDay(ModelMap modelMap) {
        modelMap.addAttribute("adverts", service.showLastDay());
        modelMap.addAttribute("day", true);
        modelMap.addAttribute("marks", service.findAllMarks());
        return "index";
    }

    @RequestMapping(value = "/marks/{markName}", method = RequestMethod.GET)
    public String showSpecificMark(@ModelAttribute Mark mark, ModelMap modelMap) {
        if (mark.getMarkName().equals("none")) {
            return "redirect:/";
        }
        modelMap.addAttribute("adverts", service.showWithSpecificMark(mark));
        modelMap.addAttribute("marks", service.findAllMarks());
        modelMap.addAttribute("setMark", mark.getMarkName());
        return "index";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("name=" + name);
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        String folder = System.getProperty("java.io.tmpdir");
        File file = new File(folder + File.separator + name);
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }

    @RequestMapping(value = "/cars/{id}", method = RequestMethod.POST)
    public void showInfoCar(@ModelAttribute("id") Car car, HttpServletResponse resp) throws IOException {
        String json = new Gson().toJson(service.getCar(car.getId()));
        resp.setContentType("json");
        resp.getWriter().write(json);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public void showInfoOwner(@ModelAttribute("id") User user, HttpServletResponse resp) throws IOException {
        String json = new Gson().toJson(service.getUser(user.getId()));
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
