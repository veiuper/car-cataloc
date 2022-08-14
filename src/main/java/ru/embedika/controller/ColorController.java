package ru.embedika.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.embedika.model.Color;
import ru.embedika.service.ColorService;

import java.util.Optional;

@RestController
public class ColorController {
    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @PostMapping(value = "/colors")
    public ResponseEntity<?> save(@RequestBody Color color) {
        if (color.getId() != null && colorService.existById(color.getId())) {
            //TODO Http статус, что данные найдены и не обновлены, запрет модифицирования HttpStatus.NOT_IMPLEMENTED HttpStatus.METHOD_NOT_ALLOWED НЕ СРАБАТЫВАЕТ
            return new ResponseEntity<>("The object already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        color.setId(0);
        colorService.save(color);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/colors")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PageableDefault Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        Slice<Color> slice = colorService.findAll(pageable);
        return (slice.isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(slice, HttpStatus.OK);
    }

    @GetMapping(value = "/colors/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") int id) {
        final Optional<Color> optionalColor = colorService.findById(id);
        return optionalColor
                .map(color -> new ResponseEntity<>(color, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/colors/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        if (colorService.existById(id)) {
            colorService.deleteById(id);
            //TODO Может ли следующая строка выполниться, если удаление не произошло: исключение поглощено
            //TODO Http статус, что данные модифицированы
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
