package com.example.demo.http.controllers;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.models.Animal;
import com.example.demo.services.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/create")
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO animal) {
        AnimalDTO createdAnimal = animalService.create(animal);
        return ResponseEntity.status(200).body(createdAnimal);
    }
}