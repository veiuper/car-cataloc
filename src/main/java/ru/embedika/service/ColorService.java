package ru.embedika.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.model.CarsBrand;
import ru.embedika.model.Color;
import ru.embedika.repository.ColorRepository;

import java.util.Optional;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color save(Color color) {
        return colorRepository.save(color);
    }

    public Slice<Color> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    public boolean existById(int id) {
        return colorRepository.existsById(id);
    }

    public long count() {
        return colorRepository.count();
    }

    public void deleteById(int id) {
        colorRepository.deleteById(id);
    }

    public Optional<Color> findByNameIgnoreCase(String name) {
        return colorRepository.findByNameIgnoreCase(name);
    }
}
