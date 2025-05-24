package com.example.demo.dto;

import com.example.demo.common.enums.CageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CageDTO {

    private Long id;

    private Long characterId;

    private CageType type;

    private Integer coinPrice;

    private Integer difficultyLevel;

    private List<Long> animalIds;

    private List<Long> mathProblemIds;

}