package ru.job4j.carmarket.service;

import ru.job4j.carmarket.domain.*;

import java.util.List;

public interface ServiceInterface<T, E, V> {

    List<T> findAll();

    List<T> showWithPhoto();

    List<T> showLastDay();

    List<T> showWithSpecificMark(Mark mark);

    E getCar(int id);

    V getUser(int id);

    List<T> getAdvertsUser(V v);

    void updateStatus(T t);

    void addNewAdvert(CarBody carBody, Engine engine, Transmission transmission,
                      Mark mark, Model model, V v, E e, T t);

    V addUser(V v);

    boolean validateUser(V v);

    V findByUsername(String username);

    List<Mark> findAllMarks();
}
