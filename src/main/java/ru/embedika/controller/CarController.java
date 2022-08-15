
package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.exception.BusinessException;
import ru.embedika.model.Car;
import ru.embedika.service.CarService;

import java.util.Optional;

@RestController
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<?> findAll(
            @RequestBody(required = false) PageRequestOfCarsDto dto,
            @PageableDefault Pageable pageableDefault
    ) throws BusinessException {
        Slice<Car> carSlice = carService.findAll(dto, pageableDefault);
        if (carSlice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carSlice, HttpStatus.OK);
    }

    @PostMapping(value = "/cars")
    public ResponseEntity<?> save(@RequestBody Car car) {
        Car saved = carService.save(car);
        if (saved == null) {
            return new ResponseEntity<>("The object already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/cars/{car-number}")
    public ResponseEntity<?> findById(@PathVariable(name = "car-number") String carNumber) {
        Optional<Car> optionalCar = carService.findById(carNumber);
        return optionalCar
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/cars/{car-number}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "car-number") String carNumber) {
        boolean deleted = carService.deleteById(carNumber);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
