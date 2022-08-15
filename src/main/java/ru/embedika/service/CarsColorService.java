package ru.embedika.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.embedika.model.CarsColor;
import ru.embedika.repository.CarsColorRepository;

import java.util.Optional;

@Service
public class CarsColorService {
    private final CarsColorRepository carsColorRepository;

    public CarsColorService(CarsColorRepository carsColorRepository) {
        this.carsColorRepository = carsColorRepository;
    }

    public CarsColor save(CarsColor carsColor) {
        if (carsColor.getId() != null && carsColorRepository.existsById(carsColor.getId())) {
            return null;
        }
        carsColor.setId(0);
        return carsColorRepository.save(carsColor);
    }

    public Slice<CarsColor> findAll(Integer page, Integer size, Pageable pageableDefault) {
        Pageable pageable = pageableDefault;
        if (page != null || size != null) {
            pageable = PageRequest.of((page == null) ? 0 : page, size == null ? pageableDefault.getPageSize() : size);
        }
        return carsColorRepository.findAll(pageable);
    }

    public Optional<CarsColor> findById(int id) {
        return carsColorRepository.findById(id);
    }

    public boolean existsById(int id) {
        return carsColorRepository.existsById(id);
    }

    public long count() {
        return carsColorRepository.count();
    }

    public boolean deleteById(int id) {
        if (carsColorRepository.existsById(id)) {
            //TODO Поглощенные исключения
            carsColorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CarsColor> findByNameIgnoreCase(String name) {
        return carsColorRepository.findByNameIgnoreCase(name);
    }
}
