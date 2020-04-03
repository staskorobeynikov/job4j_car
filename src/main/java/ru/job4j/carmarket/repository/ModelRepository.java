package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.model.Model;

public interface ModelRepository extends CrudRepository<Model, Integer> {

    Model findModelByName(String name);
}
