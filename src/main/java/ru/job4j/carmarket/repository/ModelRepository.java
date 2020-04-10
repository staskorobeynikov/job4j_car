package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.Model;

public interface ModelRepository extends CrudRepository<Model, Integer> {

    Model findModelByModelName(String name);
}
