package ru.embedika.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.embedika.model.CarsColor;

import java.util.Optional;

@Repository
public interface CarsColorRepository extends PagingAndSortingRepository<CarsColor, Integer> {
    Optional<CarsColor> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
