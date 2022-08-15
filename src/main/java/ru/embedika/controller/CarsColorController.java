package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.embedika.model.CarsColor;
import ru.embedika.service.CarsColorService;

import java.util.Optional;

@RestController
public class CarsColorController {
    private final CarsColorService carsColorService;

    public CarsColorController(CarsColorService carsColorService) {
        this.carsColorService = carsColorService;
    }

    @PostMapping(value = "/colors")
    public ResponseEntity<?> save(@RequestBody CarsColor carsColor) {
        CarsColor saved = carsColorService.save(carsColor);
        if (saved == null) {
            new ResponseEntity<>("The object already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/colors")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        Slice<CarsColor> slice = carsColorService.findAll(page, size, pageableDefault);
        return (slice.isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(slice, HttpStatus.OK);
    }

    @GetMapping(value = "/colors/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") int id) {
        Optional<CarsColor> optionalCarsColor = carsColorService.findById(id);
        return optionalCarsColor
                .map(carsColor -> new ResponseEntity<>(carsColor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/colors/{id}")
    public ResponseEntity<?> deleteId(@PathVariable(name = "id") int id) {
        if (carsColorService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
