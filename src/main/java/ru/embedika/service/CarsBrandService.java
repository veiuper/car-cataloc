package ru.embedika.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.exception.NoObjectCreated;
import ru.embedika.exception.NoObjectDeleted;
import ru.embedika.exception.ResourceNotFoundException;
import ru.embedika.model.CarsBrand;
import ru.embedika.repository.CarsBrandRepository;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class CarsBrandService {
    private final CarsBrandRepository carsBrandRepository;

    public CarsBrandService(CarsBrandRepository carsBrandRepository) {
        this.carsBrandRepository = carsBrandRepository;
    }

    public CarsBrand save(CarsBrand carsBrand) {
        if (carsBrand.getName() != null && carsBrandRepository.existsByNameIgnoreCase(carsBrand.getName())) {
            throw new NoObjectCreated("The object already exists");
        }
        carsBrand.setId(0);
        CarsBrand saved;
        try {
            saved = carsBrandRepository.save(carsBrand);
        } catch (RuntimeException e) {
            throw new NoObjectCreated("The object has not been created");
        }
        return saved;
    }

    public Slice<CarsBrand> findAll(Integer page, Integer size, Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        Slice<CarsBrand> carsBrandSlice = carsBrandRepository.findAll(pageable);
        if (carsBrandSlice.isEmpty()) {
            throw new ResourceNotFoundException("No data on car brands found");
        }
        return carsBrandSlice;
    }

    public CarsBrand findById(int id) {
        return carsBrandRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageFormat.format(
                                        "The brand of cars with id {0} was not found", id
                                )));
    }

    public boolean existsById(int id) {
        return carsBrandRepository.existsById(id);
    }

    public long count() {
        return carsBrandRepository.count();
    }

    public void deleteById(int id) {
        boolean existsById = carsBrandRepository.existsById(id);
        if (!existsById) {
            throw new ResourceNotFoundException("Object not found");
        }
        try {
            carsBrandRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new NoObjectDeleted("The object could not be deleted");
        }
    }

    public Optional<CarsBrand> findByNameIgnoreCase(String name) {
        return carsBrandRepository.findByNameIgnoreCase(name);
    }
}
