package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.CarBody;

public interface CarBodyRepository extends CrudRepository<CarBody, Integer> {

    CarBody findCarBodyByColorAndTypeAndCountDoor(String color, String type, Integer countDoor);
}
