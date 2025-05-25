package com.example.demo.services.animal;

import com.example.demo.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {

    AnimalDTO create(AnimalDTO animalDTO);

    List<AnimalDTO> findByCageId(Long cageId);

    AnimalDTO findById(Long id);

    List<AnimalDTO> findByCharacterId(Long characterId);
}