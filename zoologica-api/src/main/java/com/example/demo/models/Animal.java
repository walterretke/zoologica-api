package com.example.demo.models;

import com.example.demo.common.enums.AnimalType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AnimalType type;

    private Integer basePrice;

    @ManyToOne
    @JoinColumn(name = "cage_id")
    private Cage cage;
}