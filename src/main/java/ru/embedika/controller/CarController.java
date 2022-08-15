
package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.exception.ParsException;
import ru.embedika.model.Car;
import ru.embedika.service.CarService;

@RestController
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/cars")
    public Slice<Car> findAll(
            @RequestBody(required = false) PageRequestOfCarsDto dto,
            @PageableDefault Pageable pageableDefault
    ) throws ParsException {
        return carService.findAll(dto, pageableDefault);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/cars")
    public Car save(@RequestBody Car car) {
        return carService.save(car);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/cars/{car-number}")
    public Car findById(@PathVariable(name = "car-number") String carNumber) {
        return carService.findById(carNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/cars/{car-number}")
    public void deleteById(@PathVariable(name = "car-number") String carNumber) {
        carService.deleteById(carNumber);
    }
}
