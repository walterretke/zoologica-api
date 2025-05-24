package com.example.demo.repositories;

import com.example.demo.common.enums.AnimalType;
import com.example.demo.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByType(AnimalType type);

    List<Animal> findByNameContainingIgnoreCase(String name);

    List<Animal> findByCageId(Long cageId);

    @Query("SELECT a FROM Animal a WHERE a.basePrice BETWEEN :minPrice AND :maxPrice")
    List<Animal> findByPriceRange(Integer minPrice, Integer maxPrice);

    @Query("SELECT a FROM Animal a WHERE a.cage IS NULL")
    List<Animal> findAnimalsWithoutCage();

    Optional<Animal> findByNameAndType(String name, AnimalType type);
}