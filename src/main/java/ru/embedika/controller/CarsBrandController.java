package ru.embedika.controller;

import org.springframework.data.domain.PageRequest;
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
        if (carsBrand.getId() != null && carsBrandService.existById(carsBrand.getId())) {
            //TODO Http статус, что данные найдены и не обновлены, запрет модифицирования HttpStatus.NOT_IMPLEMENTED HttpStatus.METHOD_NOT_ALLOWED НЕ СРАБАТЫВАЕТ
            return new ResponseEntity<>("The object already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        carsBrand.setId(0);
        carsBrandService.save(carsBrand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/car-brands")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        Slice<CarsBrand> slice = carsBrandService.findAll(pageable);
        return (slice.isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(slice, HttpStatus.OK);
    }

    @GetMapping(value = "/car-brands/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") int id) {
        final Optional<CarsBrand> optionalCarsBrand = carsBrandService.findById(id);
        return optionalCarsBrand
                .map(color -> new ResponseEntity<>(color, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/car-brands/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        if (carsBrandService.existById(id)) {
            carsBrandService.deleteById(id);
            //TODO Может ли следующая строка выполниться, если удаление не произошло: исключение поглощено
            //TODO Http статус, что данные модифицированы
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
