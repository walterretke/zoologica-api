package com.example.demo.dto;

import com.example.demo.common.enums.AnimalType;
import lombok.Data;

@Data
public class AnimalDTO {
    private Long id;
    private String name;
    private AnimalType type;
    private Integer basePrice;
    private Long cageId; // We keep this as Long in the DTO
}