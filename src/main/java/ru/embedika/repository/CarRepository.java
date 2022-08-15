package ru.embedika.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.embedika.model.Car;

import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, String> {
    Optional<Car> findByCarNumberIgnoreCase(String carNumber);

    boolean existsByCarNumberIgnoreCase(String carNumber);
}
