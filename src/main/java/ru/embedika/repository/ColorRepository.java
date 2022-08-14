package ru.embedika.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.embedika.model.Color;

import java.util.Optional;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color, Integer> {
    public Optional<Color> findByNameIgnoreCase(String name);
}
