package ru.embedika.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.dto.util.PageRequestOfCarsDtoParser;
import ru.embedika.model.Car;
import ru.embedika.model.CarsBrand;
import ru.embedika.model.CarsColor;
import ru.embedika.repository.CarRepository;

import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarsBrandService carsBrandService;
    private final CarsColorService carsColorService;

    public CarService(CarRepository carRepository, CarsBrandService carsBrandService, CarsColorService carsColorService) {
        this.carRepository = carRepository;
        this.carsBrandService = carsBrandService;
        this.carsColorService = carsColorService;
    }

    public Slice<Car> findAll(PageRequestOfCarsDto dto, Pageable pageableDefault) {
        PageRequest request = null;
        if (dto != null) {
            request = PageRequestOfCarsDtoParser.pars(dto);
        }
        if (request != null) {
            return carRepository.findAll(request);
        } else {
            return carRepository.findAll(pageableDefault);
        }
    }

    public Car save(Car car) {
        if (car.getCarNumber() != null && carRepository.existsByCarNumberIgnoreCase(car.getCarNumber())) {
            return null;
        }
        //TODO Нужна транзакция на все изменения

        // Car's brand exists
        Optional<CarsBrand> optionalCarsBrand = carsBrandService.findByNameIgnoreCase(car.getCarsBrand().getName());
        if (optionalCarsBrand.isPresent()) {
            optionalCarsBrand.ifPresent(carsBrand -> car.getCarsBrand().setId(carsBrand.getId()));
        } else {
            // New car's brand
            car.getCarsBrand().setId(0);
            CarsBrand carsBrand = carsBrandService.save(car.getCarsBrand());
            if (carsBrand == null) {
                return null;
            }
            car.setCarsBrand(carsBrand);
        }
        // Car's color exists
        Optional<CarsColor> optionalColor = carsColorService.findByNameIgnoreCase(car.getCarsColor().getName());
        if (optionalColor.isPresent()) {
            optionalColor.ifPresent(color -> car.getCarsColor().setId(color.getId()));
        } else {
            // New car's color
            car.getCarsColor().setId(0);
            CarsColor carsColor = carsColorService.save(car.getCarsColor());
            if (carsColor == null) {
                return null;
            }
            car.setCarsColor(carsColor);
        }
        return carRepository.save(car);
    }

    public Optional<Car> findById(String carNumber) {
        return carRepository.findByCarNumberIgnoreCase(carNumber);
    }

    public long count() {
        return carRepository.count();
    }

    public boolean deleteById(String carNumber) {
        final Optional<Car> optionalCar = carRepository.findByCarNumberIgnoreCase(carNumber);
        if (optionalCar.isPresent()) {
            carRepository.deleteById(carNumber);
            return true;
        }
        return false;
    }
}
