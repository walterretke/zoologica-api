package com.example.demo.repositories;

import com.example.demo.models.MathProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MathProblemRepository extends JpaRepository<MathProblem, Long> {
}
