package ru.job4j.carmarket.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carmarket.model.*;
import ru.job4j.carmarket.service.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class NewAdvertController {

    private final Service<Advert, Car, User> service;

    private static final Logger LOG = LogManager.getLogger(NewAdvertController.class.getName());

    @Autowired
    public NewAdvertController(Service<Advert, Car, User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/adds", method = RequestMethod.POST)
    public String addAdvert(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = req.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String photo = null, id = null, mark = null, model = null, mileAge = null, created = null,
                gearType = null, gearBox = null, carBodyType = null, carBodyColor = null, carBodyDoors = null,
                engineVolume = null, enginePower = null, engineType = null, price = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("c:/images");
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    photo = this.getNamePhoto(item.getName());
                    if (!photo.equals("")) {
                        File file = new File(folder + File.separator + photo);
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "id":
                            id = item.getString();
                            break;
                        case "mark":
                            mark = item.getString();
                            break;
                        case "model":
                            model = item.getString();
                            break;
                        case "mile_age":
                            mileAge = item.getString();
                            break;
                        case "created":
                            created = item.getString();
                            break;
                        case "price":
                            price = item.getString();
                            break;
                        case "gear_type":
                            gearType = item.getString();
                            break;
                        case "gear_box":
                            gearBox = item.getString();
                            break;
                        case "car_body_type":
                            carBodyType = item.getString();
                            break;
                        case "car_body_color":
                            carBodyColor = item.getString();
                            break;
                        case "car_body_doors":
                            carBodyDoors = item.getString();
                            break;
                        case "engine_volume":
                            engineVolume = item.getString();
                            break;
                        case "engine_power":
                            enginePower = item.getString();
                            break;
                        case "engine_type":
                            engineType = item.getString();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getFieldName());
                    }
                }
            }
        } catch (FileUploadException e) {
            LOG.error(e.getMessage(), e);
        }

        CarBody carBody = new CarBody();
        carBody.setType(carBodyType);
        carBody.setColor(carBodyColor);
        carBody.setCountDoor(Integer.parseInt(carBodyDoors));

        Engine engine = new Engine();
        engine.setVolume(Double.parseDouble(engineVolume));
        engine.setPower(Integer.parseInt(enginePower));
        engine.setType(engineType);

        Transmission transmission = new Transmission();
        transmission.setGearBox(gearBox);
        transmission.setGearType(gearType);

        Mark mark1 = new Mark();
        mark1.setName(mark);

        Model model1 = new Model();
        model1.setName(model);

        User user = new User();
        user.setId(Integer.parseInt(id));

        Car car = new Car();
        car.setMileAge(Integer.parseInt(mileAge));
        String[] split = created.split("-");
        String created1 = String.format("%s-%s-%s %s", split[0], split[1], split[2], "00:00:00.0");
        car.setCreated(Timestamp.valueOf(created1));

        Advert advert = new Advert();
        advert.setPrice(Integer.parseInt(price));
        advert.setStatus(false);
        advert.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        advert.setImageName(photo);

        service.addNewAdvert(carBody, engine, transmission, mark1, model1, user, car, advert);

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
