package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.Car;

public interface CarRepository extends CrudRepository<Car, Integer> {

    Car findCarById(Integer id);
}
