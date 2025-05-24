package com.example.demo.http.controllers;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.CageDTO;
import com.example.demo.models.Cage;
import com.example.demo.services.animal.AnimalService;
import com.example.demo.services.cage.CageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cage")
public class CageController {

    private final CageService cageService;

    @PostMapping("/create")
    public ResponseEntity<CageDTO> create(@RequestBody CageDTO cageDTO) {
        CageDTO createdCageDTO = cageService.create(cageDTO);
        return ResponseEntity.status(200).body(createdCageDTO);
    }
}