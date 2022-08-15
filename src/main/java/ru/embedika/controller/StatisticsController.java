package ru.embedika.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.embedika.service.CarService;
import ru.embedika.service.CarsBrandService;
import ru.embedika.service.CarsColorService;

@RestController
public class StatisticsController {
    private final CarService carService;
    private final CarsBrandService carsBrandService;
    private final CarsColorService carsColorService;

    public StatisticsController(CarService carService,
                                CarsBrandService carsBrandService,
                                CarsColorService carsColorService) {
        this.carService = carService;
        this.carsBrandService = carsBrandService;
        this.carsColorService = carsColorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/statistics")
    public ResponseEntity<?> statistic() {
        return new ResponseEntity<>(
                "Number of cars: " + carService.count() + System.lineSeparator() +
                        "Number of brands: " + carsBrandService.count() + System.lineSeparator() +
                        "Number of colors: " + carsColorService.count(),
                HttpStatus.OK
        );
    }
}
