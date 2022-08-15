package ru.embedika.service;

import org.springframework.data.domain.PageRequest;
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
        if (carsBrand.getId() != null && carsBrandRepository.existsById(carsBrand.getId())) {
            return null;
        }
        carsBrand.setId(0);
        return carsBrandRepository.save(carsBrand);
    }

    public Slice<CarsBrand> findAll(Integer page, Integer size, Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        return carsBrandRepository.findAll(pageable);
    }

    public Optional<CarsBrand> findById(int id) {
        return carsBrandRepository.findById(id);
    }

    public boolean existsById(int id) {
        return carsBrandRepository.existsById(id);
    }

    public long count() {
        return carsBrandRepository.count();
    }

    public boolean deleteById(int id) {
        if (carsBrandRepository.existsById(id)) {
            //TODO Поглощенные исключения
            carsBrandRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CarsBrand> findByNameIgnoreCase(String name) {
        return carsBrandRepository.findByNameIgnoreCase(name);
    }
}
