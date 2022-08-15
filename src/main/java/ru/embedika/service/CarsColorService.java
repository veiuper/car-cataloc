package ru.embedika.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.exception.NoObjectCreatedException;
import ru.embedika.exception.NoObjectDeletedException;
import ru.embedika.exception.ResourceNotFoundException;
import ru.embedika.model.CarsColor;
import ru.embedika.repository.CarsColorRepository;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class CarsColorService {
    private final CarsColorRepository carsColorRepository;

    public CarsColorService(CarsColorRepository carsColorRepository) {
        this.carsColorRepository = carsColorRepository;
    }

    public CarsColor save(CarsColor carsColor) {
        if (carsColor.getName() != null && carsColorRepository.existsByNameIgnoreCase(carsColor.getName())) {
            throw new NoObjectCreatedException("The object already exists");
        }
        carsColor.setId(0);
        CarsColor saved;
        try {
            saved = carsColorRepository.save(carsColor);
        } catch (RuntimeException e) {
            throw new NoObjectCreatedException("The object has not been created");
        }
        return saved;
    }

    public Slice<CarsColor> findAll(Integer page, Integer size, Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        Slice<CarsColor> carsColorSlice = carsColorRepository.findAll(pageable);
        if (carsColorSlice.isEmpty()) {
            throw new ResourceNotFoundException("No car color data found");
        }
        return carsColorSlice;
    }

    public CarsColor findById(int id) {
        return carsColorRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageFormat.format("Color of cars with id {0} not found", id)
                        ));
    }

    public boolean existsById(int id) {
        return carsColorRepository.existsById(id);
    }

    public long count() {
        return carsColorRepository.count();
    }

    public void deleteById(int id) {
        boolean existsById = carsColorRepository.existsById(id);
        if (!existsById) {
            throw new ResourceNotFoundException("Object not found");
        }
        try {
            carsColorRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new NoObjectDeletedException("The object could not be deleted");
        }
    }

    public Optional<CarsColor> findByNameIgnoreCase(String name) {
        return carsColorRepository.findByNameIgnoreCase(name);
    }
}
