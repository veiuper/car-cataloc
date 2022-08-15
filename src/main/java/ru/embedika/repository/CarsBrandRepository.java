package ru.embedika.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.embedika.model.CarsBrand;

import java.util.Optional;

@Repository
public interface CarsBrandRepository extends PagingAndSortingRepository<CarsBrand, Integer> {
    Optional<CarsBrand> findByNameIgnoreCase(String name);
}
