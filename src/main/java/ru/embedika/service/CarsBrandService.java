package ru.embedika.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.model.CarsBrand;
import ru.embedika.repository.CarsBrandRepository;

import java.util.Optional;

@Service
public class CarsBrandService {
    private final CarsBrandRepository carsBrandRepository;

    public CarsBrandService(CarsBrandRepository carsBrandRepository) {
        this.carsBrandRepository = carsBrandRepository;
    }

    public CarsBrand save(CarsBrand carsBrand) {
        return carsBrandRepository.save(carsBrand);
    }

    public Slice<CarsBrand> findAll(Pageable pageable) {
        return carsBrandRepository.findAll(pageable);
    }

    public Optional<CarsBrand> findById(int id) {
        return carsBrandRepository.findById(id);
    }

    public boolean existById(int id) {
        return carsBrandRepository.existsById(id);
    }

    public long count() {
        return carsBrandRepository.count();
    }

    public void deleteById(int id) {
        carsBrandRepository.deleteById(id);
    }

    public Optional<CarsBrand> findByNameIgnoreCase(String name) {
        return carsBrandRepository.findByNameIgnoreCase(name);
    }
}
