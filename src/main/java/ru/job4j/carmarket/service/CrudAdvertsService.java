package ru.job4j.carmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carmarket.domain.*;
import ru.job4j.carmarket.repository.*;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CrudAdvertsService implements ServiceInterface<Advert, Car, User> {

    private final AdvertRepository advertRepository;

    private final CarBodyRepository carBodyRepository;

    private final CarRepository carRepository;

    private final EngineRepository engineRepository;

    private final MarkRepository markRepository;

    private final ModelRepository modelRepository;

    private final UserRepository userRepository;

    private final TransmissionRepository transmissionRepository;

    @Autowired
    public CrudAdvertsService(AdvertRepository advertRepository,
                              CarBodyRepository carBodyRepository, CarRepository carRepository,
                              EngineRepository engineRepository, MarkRepository markRepository,
                              ModelRepository modelRepository, UserRepository userRepository,
                              TransmissionRepository transmissionRepository) {
        this.advertRepository = advertRepository;
        this.carBodyRepository = carBodyRepository;
        this.carRepository = carRepository;
        this.engineRepository = engineRepository;
        this.markRepository = markRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.transmissionRepository = transmissionRepository;
    }

    @Override
    public List<Advert> findAll() {
        return (List<Advert>) advertRepository.findAll();
    }

    @Override
    public List<Advert> showWithPhoto() {
        return advertRepository.findByImageNameIsNot("");
    }

    @Override
    public List<Advert> showLastDay() {
        Timestamp findDate = new Timestamp(System.currentTimeMillis() - 86400000L);
        return advertRepository.findByCreatedDateAfter(findDate);
    }

    @Override
    public List<Advert> showWithSpecificMark(Mark mark) {
        return advertRepository.findByCar_Mark_MarkName(mark.getMarkName());
    }

    @Override
    public Car getCar(int id) {
        return carRepository.findCarById(id);
    }

    @Override
    public User getUser(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public List<Advert> getAdvertsUser(User user) {
        User find = userRepository.findByUsername(user.getUsername());
        return advertRepository.findAdvertByOwner_Id(find.getId());
    }

    @Override
    public void updateStatus(Advert advert) {
        Advert update = advertRepository.findAdvertById(advert.getId());
        update.setStatus(advert.isStatus());
        advertRepository.save(update);
    }

    @Override
    public void addNewAdvert(CarBody carBody, Engine engine, Transmission transmission,
                             Mark mark, Model model, User user, Car car, Advert advert) {
        Mark findMark = markRepository.findMarkByMarkName(mark.getMarkName());
        if (findMark == null) {
            findMark = markRepository.save(mark);
        }

        Model findModel = modelRepository.findModelByModelName(model.getModelName());
        if (findModel == null) {
            findModel = modelRepository.save(model);
        }

        Engine findEngine = engineRepository.findEngineByVolumeAndEngineTypeAndPower(
                engine.getVolume(),
                engine.getEngineType(),
                engine.getPower()
        );

        if (findEngine == null) {
            findEngine = engineRepository.save(engine);
        }

        Transmission findTransmission = transmissionRepository.findTransmissionByGearBoxAndGearType(
                transmission.getGearBox(),
                transmission.getGearType()
        );

        if (findTransmission == null) {
            findTransmission = transmissionRepository.save(transmission);
        }

        CarBody findCarBody = carBodyRepository.findCarBodyByColorAndTypeAndCountDoor(
                carBody.getColor(),
                carBody.getType(),
                carBody.getCountDoor()
        );

        if (findCarBody == null) {
            findCarBody = carBodyRepository.save(carBody);
        }

        car.setMark(findMark);
        car.setModel(findModel);
        car.setEngine(findEngine);
        car.setTransmission(findTransmission);
        car.setCarBody(findCarBody);

        Car saveCar = carRepository.save(car);

        User findUser = userRepository.findUserById(user.getId());

        advert.setCar(saveCar);
        advert.setOwner(findUser);

        advertRepository.save(advert);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean validateUser(User user) {
        boolean result = true;
        User findUser = userRepository.findByUsername(user.getUsername());
        if (findUser != null) {
            result = false;
        }
        return result;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
