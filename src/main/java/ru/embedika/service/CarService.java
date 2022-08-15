package ru.embedika.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.dto.util.PageRequestOfCarsDtoParser;
import ru.embedika.exception.NoObjectCreatedException;
import ru.embedika.exception.NoObjectDeletedException;
import ru.embedika.exception.ResourceNotFoundException;
import ru.embedika.model.Car;
import ru.embedika.model.CarsBrand;
import ru.embedika.model.CarsColor;
import ru.embedika.repository.CarRepository;

import java.text.MessageFormat;
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
        Pageable request = (dto == null) ? pageableDefault : PageRequestOfCarsDtoParser.pars(dto);
        Slice<Car> carSlice = carRepository.findAll(request);
        if (carSlice.isEmpty()) {
            throw new ResourceNotFoundException("No car data found");
        }
        return carSlice;
    }

    public Car save(Car car) {
        // Car exists
        if (car.getCarNumber() != null && carRepository.existsByCarNumberIgnoreCase(car.getCarNumber())) {
            throw new NoObjectCreatedException("The object already exists");
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
            car.setCarsColor(carsColor);
        }
        Car saved;
        try {
            saved = carRepository.save(car);
        } catch (RuntimeException e) {
            throw new NoObjectCreatedException("The object has not been created");
        }
        return saved;
    }

    public Car findById(String carNumber) {
        return carRepository.findByCarNumberIgnoreCase(carNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageFormat.format(
                                        "The car with the license plate {0} was not found", carNumber
                                )));
    }

    public long count() {
        return carRepository.count();
    }

    public void deleteById(String carNumber) {
        final Optional<Car> optionalCar = carRepository.findByCarNumberIgnoreCase(carNumber);
        if (optionalCar.isEmpty()) {
            throw new ResourceNotFoundException("Object not found");
        }
        try {
            carRepository.deleteById(carNumber);
        } catch (RuntimeException e) {
            throw new NoObjectDeletedException("The object could not be deleted");
        }
    }
}
