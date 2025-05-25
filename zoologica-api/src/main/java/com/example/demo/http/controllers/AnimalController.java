package com.example.demo.http.controllers;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.services.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    // serve como compra
    @PostMapping("/create")
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO animalDTO) {
        AnimalDTO createdAnimal = animalService.create(animalDTO);
        return ResponseEntity.ok(createdAnimal);
    }

    @GetMapping("/cage/{cageId}")
    public ResponseEntity<List<AnimalDTO>> findByCageId(@PathVariable Long cageId) {
        List<AnimalDTO> animals = animalService.findByCageId(cageId);
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Long id) {
        AnimalDTO animal = animalService.findById(id);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<AnimalDTO>> findByCharacterId(@PathVariable Long characterId) {
        List<AnimalDTO> animals = animalService.findByCharacterId(characterId);
        return ResponseEntity.ok(animals);
    }
}