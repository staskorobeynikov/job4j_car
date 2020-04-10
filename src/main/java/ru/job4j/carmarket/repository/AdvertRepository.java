package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.Advert;

import java.sql.Timestamp;
import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Integer> {

    List<Advert> findByCar_Mark_MarkName(String name);

    List<Advert> findByImageNameIsNot(String string);

    List<Advert> findByCreatedDateAfter(Timestamp date);

    List<Advert> findAdvertByOwner_Id(Integer id);

    Advert findAdvertById(Integer id);
}
