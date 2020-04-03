package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.model.Transmission;

public interface TransmissionRepository extends CrudRepository<Transmission, Integer> {

    Transmission findTransmissionByGearBoxAndGearType(String gearBox, String gearType);
}
