
package ru.embedika.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.dto.util.PageRequestOfCarsDtoParser;
import ru.embedika.exception.BusinessException;
import ru.embedika.model.Car;
import ru.embedika.model.CarsBrand;
import ru.embedika.model.Color;
import ru.embedika.service.CarService;
import ru.embedika.service.CarsBrandService;
import ru.embedika.service.ColorService;

import java.util.Optional;

@RestController
public class CarController {
    private final CarService carService;
    private final CarsBrandService carsBrandService;
    private final ColorService colorService;

    public CarController(CarService carService, CarsBrandService carsBrandService, ColorService colorService) {
        this.carService = carService;
        this.carsBrandService = carsBrandService;
        this.colorService = colorService;
    }

    @GetMapping("/cars")
    public Slice<Car> findAll(
            @RequestBody(required = false) PageRequestOfCarsDto dto,
            @PageableDefault Pageable pageableDefault
    ) throws BusinessException {
        PageRequest request = null;
        if (dto != null) {
            PageRequestOfCarsDtoParser parser = new PageRequestOfCarsDtoParser(dto);
            request = parser.pars();
        }
        if (request != null) {
            return carService.findAll(request);
        } else {
            return carService.findAll(pageableDefault);
        }
    }

    @PostMapping(value = "/cars")
    public ResponseEntity<?> save(@RequestBody Car car) {
        if (car.getCarNumber() != null && carService.existsByCarNumberIgnoreCase(car.getCarNumber())) {
            //TODO Http статус, что данные найдены и не обновлены, запрет модифицирования HttpStatus.NOT_IMPLEMENTED HttpStatus.METHOD_NOT_ALLOWED НЕ СРАБАТЫВАЕТ
            return new ResponseEntity<>("The object already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        //TODO Нужна транзакция на все изменения
        Optional<CarsBrand> optionalCarsBrand = carsBrandService.findByNameIgnoreCase(car.getCarsBrand().getName());
        optionalCarsBrand.ifPresent(carsBrand -> car.getCarsBrand().setId(carsBrand.getId()));
        if (car.getCarsBrand().getId() != null && !carsBrandService.existById(car.getCarsBrand().getId())) {
            car.getCarsBrand().setId(0);
            carsBrandService.save(car.getCarsBrand());
        } else if (car.getCarsBrand().getId() == null) {
            carsBrandService.save(car.getCarsBrand());
        }
        Optional<Color> optionalColor = colorService.findByNameIgnoreCase(car.getColor().getName());
        optionalColor.ifPresent(color -> car.getColor().setId(color.getId()));
        if (car.getColor().getId() != null && !colorService.existById(car.getColor().getId())) {
            car.getColor().setId(0);
            colorService.save(car.getColor());
        } else if (car.getColor().getId() == null) {
            colorService.save(car.getColor());
        }
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/cars")
//    public ResponseEntity<?> findAll(
//            @RequestParam(name = "page", required = false) Integer page,
//            @RequestParam(name = "size", required = false) Integer size,
//            @PageableDefault Pageable pageableDefault) {
//        Pageable pageable = pageableDefault;
//        if (page != null || size != null) {
//            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
//        }
//        Slice<Color> slice = carService.findAll(pageable);
//        return (slice.isEmpty()) ?
//                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(slice, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/cars/{id}")
//    public ResponseEntity<?> findById(@PathVariable(name = "id") int id) {
//        final Optional<Color> optionalColor = carService.findById(id);
//        return optionalColor
//                .map(color -> new ResponseEntity<>(color, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping(value = "/cars/{id}")
//    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
//        final Optional<Color> optionalColor = carService.findById(id);
//        if (optionalColor.isPresent()) {
//            carService.deleteById(id);
//            //TODO Может ли следующая строка выполниться, если удаление не произошло: исключение поглощено
//            //TODO Http статус, что данные модифицированы
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
