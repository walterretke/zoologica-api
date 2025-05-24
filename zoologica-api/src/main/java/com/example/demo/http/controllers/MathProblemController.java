package com.example.demo.http.controllers;

import com.example.demo.dto.CageDTO;
import com.example.demo.dto.MathProblemDTO;
import com.example.demo.services.cage.CageService;
import com.example.demo.services.mathProblem.MathProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/math-problem")
public class MathProblemController {

    private final MathProblemService mathProblemService;

    @PostMapping("/create")
    public ResponseEntity<MathProblemDTO> create(@RequestBody MathProblemDTO mathProblemDTO) {
        MathProblemDTO createdMathProblem = mathProblemService.create(mathProblemDTO);
        return ResponseEntity.status(200).body(createdMathProblem);
    }
}
