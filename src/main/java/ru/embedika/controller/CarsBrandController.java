package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.embedika.model.CarsBrand;
import ru.embedika.service.CarsBrandService;

@RestController
public class CarsBrandController {
    private final CarsBrandService carsBrandService;

    public CarsBrandController(CarsBrandService carsBrandService) {
        this.carsBrandService = carsBrandService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/car-brands")
    public CarsBrand save(@RequestBody CarsBrand carsBrand) {
        return carsBrandService.save(carsBrand);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/car-brands")
    public Slice<CarsBrand> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        return carsBrandService.findAll(page, size, pageableDefault);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/car-brands/{id}")
    public CarsBrand findById(@PathVariable(name = "id") int id) {
        return carsBrandService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/car-brands/{id}")
    public void deleteById(@PathVariable(name = "id") int id) {
        carsBrandService.deleteById(id);
    }
}
