package ru.embedika.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.embedika.model.CarsColor;
import ru.embedika.service.CarsColorService;

@RestController
public class CarsColorController {
    private final CarsColorService carsColorService;

    public CarsColorController(CarsColorService carsColorService) {
        this.carsColorService = carsColorService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/colors")
    public CarsColor save(@RequestBody CarsColor carsColor) {
        return carsColorService.save(carsColor);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/colors")
    public Slice<CarsColor> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        return carsColorService.findAll(page, size, pageableDefault);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/colors/{id}")
    public CarsColor findById(@PathVariable(name = "id") int id) {
        return carsColorService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/colors/{id}")
    public void deleteId(@PathVariable(name = "id") int id) {
        carsColorService.deleteById(id);
    }
}
