package ru.job4j.carmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carmarket.model.*;
import ru.job4j.carmarket.repository.*;

import java.sql.Timestamp;
import java.util.List;

@Component(value = "crud")
public class CrudAdvertsService implements Service<Advert, Car, User> {

    private final AccountRepository accountRepository;

    private final AdvertRepository advertRepository;

    private final CarBodyRepository carBodyRepository;

    private final CarRepository carRepository;

    private final EngineRepository engineRepository;

    private final MarkRepository markRepository;

    private final ModelRepository modelRepository;

    private final UserRepository userRepository;

    private final TransmissionRepository transmissionRepository;

    @Autowired
    public CrudAdvertsService(AccountRepository accountRepository, AdvertRepository advertRepository,
                              CarBodyRepository carBodyRepository, CarRepository carRepository,
                              EngineRepository engineRepository, MarkRepository markRepository,
                              ModelRepository modelRepository, UserRepository userRepository,
                              TransmissionRepository transmissionRepository) {
        this.accountRepository = accountRepository;
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
        return advertRepository.findByCar_Mark_Name(mark.getName());
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
    public User isCredential(Account account) {
        return userRepository.findUserByAccount_LoginAndAccount_Password(account.getLogin(), account.getPassword());
    }

    @Override
    public List<Advert> getAdvertsUser(User user) {
        return advertRepository.findAdvertByOwner_Id(user.getId());
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
        Mark findMark = markRepository.findMarkByName(mark.getName());
        if (findMark == null) {
            findMark = markRepository.save(mark);
        }

        Model findModel = modelRepository.findModelByName(model.getName());
        if (findModel == null) {
            findModel = modelRepository.save(model);
        }

        Engine findEngine = engineRepository.findEngineByVolumeAndTypeAndPower(
                engine.getVolume(),
                engine.getType(),
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
    public User addUser(User user, Account account) {
        Account addAccount = accountRepository.save(account);
        user.setAccount(addAccount);
        return userRepository.save(user);
    }

    @Override
    public boolean validateAccount(Account account) {
        boolean result = true;
        Account findAccount = accountRepository.findAccountByLogin(account.getLogin());
        if (findAccount != null) {
            result = false;
        }
        return result;
    }
}
