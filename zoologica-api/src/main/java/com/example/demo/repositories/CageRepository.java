package com.example.demo.repositories;

import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CageRepository extends JpaRepository<Cage, Long> {
    List<Cage> findByCharacter(Character character);
}