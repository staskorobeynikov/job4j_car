package ru.job4j.carmarket.service;

import ru.job4j.carmarket.model.*;

import java.util.List;

public interface Service<T, E, V> {

    List<T> findAll();

    List<T> showWithPhoto();

    List<T> showLastDay();

    List<T> showWithSpecificMark(Mark mark);

    E getCar(int id);

    V getUser(int id);

    V isCredential(Account account);

    List<T> getAdvertsUser(V v);

    void updateStatus(T t);

    void addNewAdvert(CarBody carBody, Engine engine, Transmission transmission,
                      Mark mark, Model model, V v, E e, T t);

    V addUser(V v, Account account);

    boolean validateAccount(Account account);
}
