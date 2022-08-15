package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.embedika.model.CarsBrand;
import ru.embedika.service.CarsBrandService;

import java.util.Optional;

@RestController
public class CarsBrandController {
    private final CarsBrandService carsBrandService;

    public CarsBrandController(CarsBrandService carsBrandService) {
        this.carsBrandService = carsBrandService;
    }

    @PostMapping(value = "/car-brands")
    public ResponseEntity<?> save(@RequestBody CarsBrand carsBrand) {
        CarsBrand saved = carsBrandService.save(carsBrand);
        if (saved == null) {
            return new ResponseEntity<>("The object already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/car-brands")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        Slice<CarsBrand> slice = carsBrandService.findAll(page, size, pageableDefault);
        if (slice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slice, HttpStatus.OK);
    }

    @GetMapping(value = "/car-brands/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") int id) {
        Optional<CarsBrand> optionalCarsBrand = carsBrandService.findById(id);
        return optionalCarsBrand
                .map(carBrands -> new ResponseEntity<>(carBrands, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/car-brands/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") int id) {
        if (carsBrandService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
