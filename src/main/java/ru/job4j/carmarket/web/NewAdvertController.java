package ru.job4j.carmarket.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.carmarket.domain.*;
import ru.job4j.carmarket.service.ServiceInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

@Controller
public class NewAdvertController {

    private final ServiceInterface<Advert, Car, User> service;

    @Autowired
    private Environment environment;

    @Autowired
    public NewAdvertController(ServiceInterface<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/adds", method = RequestMethod.POST)
    public String addAdvert(
            @ModelAttribute Mark mark, @ModelAttribute Model model,
            @ModelAttribute CarBody carBody, @ModelAttribute Engine engine,
            @ModelAttribute Transmission transmission, @ModelAttribute Car car,
            @RequestParam(name = "price") String price, @RequestParam(name = "createdDate") String created,
            @ModelAttribute(name = "photo") MultipartFile photo, Authentication authentication
    ) throws IOException {
        String imageName = "";

        if (photo != null && !photo.isEmpty()) {
            String repository = environment.getProperty("folder.image");
            String photoName = this.getNamePhoto(photo.getOriginalFilename());
            Path path = Paths.get(repository, photoName);
            if (Files.notExists(Paths.get(repository))) {
                Files.createDirectories(Paths.get(repository));
            }
            Files.write(path, photo.getBytes());
            imageName = photoName;
        }

        String[] split = created.split("-");
        String created1 = String.format("%s-%s-%s %s", split[0], split[1], split[2], "00:00:00.0");
        car.setCreated(Timestamp.valueOf(created1));

        User user = service.findByUsername(authentication.getName());

        Advert advert = new Advert();
        advert.setPrice(Integer.parseInt(price));
        advert.setStatus(false);
        advert.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        advert.setImageName(imageName);

        service.addNewAdvert(carBody, engine, transmission, mark, model, user, car, advert);

        return "redirect:/";
    }

    private boolean isDuplicateName(String fileName) {
        boolean result = false;
        for (Advert advert : service.findAll()) {
            if (advert.getImageName().equals(fileName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private String getNamePhoto(String fileName) {
        while (isDuplicateName(fileName)) {
            String[] photoSplit = fileName.split("\\.");
            fileName = String.format("%s_1.%s", photoSplit[0], photoSplit[1]);
        }
        return fileName;
    }
}
