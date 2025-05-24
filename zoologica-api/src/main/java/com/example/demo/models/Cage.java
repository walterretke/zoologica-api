package com.example.demo.models;

import com.example.demo.common.enums.CageType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class  Cage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @Enumerated(EnumType.STRING)
    private CageType type;

    private Integer coinPrice;

    private Integer difficultyLevel;

    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    private List<Animal> animals;

    @OneToMany(mappedBy = "cage")
    private List<MathProblem> mathProblems;
}