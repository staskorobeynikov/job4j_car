package ru.job4j.carmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carmarket.model.*;
import ru.job4j.carmarket.repository.Repository;

import java.util.List;

@Component
public class AdvertsService implements Service<Advert, Car, User> {

    private final Repository<Advert, Car, User> repository;

    @Autowired
    public AdvertsService(Repository<Advert, Car, User> repository) {
        this.repository = repository;
    }

    @Override
    public List<Advert> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Advert> showWithPhoto() {
        return repository.showWithPhoto();
    }

    @Override
    public List<Advert> showLastDay() {
        return repository.showLastDay();
    }

    @Override
    public List<Advert> showWithSpecificMark(Mark mark) {
        return repository.showWithSpecificMark(mark);
    }

    @Override
    public Car getCar(int id) {
        Car result = new Car();
        for (Car car : repository.getAllCars()) {
            if (car.getId() == id) {
                result = car;
                break;
            }
        }
        return result;
    }

    @Override
    public User getUser(int id) {
        User result = new User();
        for (User user : repository.getUsers()) {
            if (user.getId() == id) {
                result = user;
                break;
            }
        }
        return result;
    }

    @Override
    public User isCredential(Account account) {
        User result = null;
        Account check;
        for (User user : repository.getUsers()) {
            check = user.getAccount();
            if (check.getLogin().equals(account.getLogin())
                    && check.getPassword().equals(account.getPassword())) {
                result = user;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Advert> getAdvertsUser(User user) {
        return repository.getAdvertsUser(user);
    }

    @Override
    public void updateStatus(Advert advert) {
        repository.updateStatus(advert);
    }

    @Override
    public void addNewAdvert(CarBody carBody, Engine engine,
                             Transmission transmission, Mark mark, Model model, User user, Car car, Advert advert) {
        repository.addNewAdvert(carBody, engine, transmission, mark, model, user, car, advert);
    }

    @Override
    public User addUser(User user, Account account) {
        return repository.addUser(user, account);
    }

    @Override
    public boolean validateAccount(Account account) {
        boolean result = true;
        Account check;
        for (User user : repository.getUsers()) {
            check = user.getAccount();
            if (check.getLogin().equals(account.getLogin())) {
                result = false;
                break;
            }
        }
        return result;
    }
}
