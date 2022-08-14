package ru.embedika.repository;

import ru.embedika.model.Car;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, String> {
    public Optional<Car> findByCarNumberIgnoreCase(String carNumber);

    public boolean existsByCarNumberIgnoreCase(String carNumber);
}
