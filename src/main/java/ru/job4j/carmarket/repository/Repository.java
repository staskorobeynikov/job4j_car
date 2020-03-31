package ru.job4j.carmarket.repository;

import ru.job4j.carmarket.model.*;

import java.util.List;

public interface Repository<T, E, V> {

    List<T> findAll();

    List<T> showWithPhoto();

    List<T> showLastDay();

    List<T> showWithSpecificMark(Mark mark);

    List<E> getAllCars();

    List<V> getUsers();

    List<T> getAdvertsUser(V v);

    void updateStatus(T t);

    void addNewAdvert(CarBody carBody, Engine engine, Transmission transmission,
                      Mark mark, Model model, V v, E e, T t);

    V addUser(V v, Account account);
}
