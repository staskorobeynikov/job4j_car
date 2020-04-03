package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.model.Mark;

public interface MarkRepository extends CrudRepository<Mark, Integer> {

    Mark findMarkByName(String name);
}
