package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.Engine;

public interface EngineRepository extends CrudRepository<Engine, Integer> {

    Engine findEngineByVolumeAndEngineTypeAndPower(Double volume, String type, Integer power);
}
