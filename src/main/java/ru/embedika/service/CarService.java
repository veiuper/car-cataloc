package ru.embedika.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.model.Car;
import ru.embedika.repository.CarRepository;

import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Slice<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Optional<Car> findByCarNumberIgnoreCase(String carNumber) {
        return carRepository.findByCarNumberIgnoreCase(carNumber);
    }

    public long count() {
        return carRepository.count();
    }

    public void deleteById(String carNumber) {
        carRepository.deleteById(carNumber);
    }

    public boolean existsByCarNumberIgnoreCase(String carNumber) {
        return carRepository.existsByCarNumberIgnoreCase(carNumber);
    }
}
