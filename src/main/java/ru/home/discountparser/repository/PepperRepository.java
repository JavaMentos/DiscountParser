package ru.home.discountparser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.discountparser.parser.pepper.dto.Pepper;

@Repository
public interface PepperRepository extends CrudRepository<Pepper, String> {
}