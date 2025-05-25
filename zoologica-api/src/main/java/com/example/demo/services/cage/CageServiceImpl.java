package com.example.demo.services.cage;

import com.example.demo.dto.CageDTO;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CageServiceImpl implements CageService {

    private final CageRepository cageRepository;
    private final CharacterRepository characterRepository;

    @Override
    public CageDTO create(CageDTO cageDTO) {
        Character character = characterRepository.findById(cageDTO.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + cageDTO.getCharacterId()));

        if (character.getTotalCoins() < cageDTO.getCoinPrice()) {
            throw new RuntimeException("Insufficient coins. Required: " + cageDTO.getCoinPrice() +
                    ", Available: " + character.getTotalCoins());
        }

        character.setTotalCoins(character.getTotalCoins() - cageDTO.getCoinPrice());
        characterRepository.save(character);

        Cage newCage = new Cage();
        newCage.setCharacter(character);
        newCage.setType(cageDTO.getType());
        newCage.setCoinPrice(cageDTO.getCoinPrice());
        newCage.setDifficultyLevel(cageDTO.getDifficultyLevel());
        newCage.setAnimals(new ArrayList<>());
        newCage.setMathProblems(new ArrayList<>());

        Cage savedCage = cageRepository.save(newCage);

        return convertToDTO(savedCage);
    }

    @Override
    public List<CageDTO> findByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<Cage> cages = cageRepository.findByCharacter(character);

        return cages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CageDTO findById(Long id) {
        Cage cage = cageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + id));

        return convertToDTO(cage);
    }

    private CageDTO convertToDTO(Cage cage) {
        List<Long> animalIds = cage.getAnimals() != null ?
                cage.getAnimals().stream().map(animal -> animal.getId()).collect(Collectors.toList()) :
                new ArrayList<>();

        List<Long> mathProblemIds = cage.getMathProblems() != null ?
                cage.getMathProblems().stream().map(problem -> problem.getId()).collect(Collectors.toList()) :
                new ArrayList<>();

        CageDTO dto = new CageDTO();
        dto.setId(cage.getId());
        dto.setCharacterId(cage.getCharacter().getId());
        dto.setType(cage.getType());
        dto.setCoinPrice(cage.getCoinPrice());
        dto.setDifficultyLevel(cage.getDifficultyLevel());
        dto.setAnimalIds(animalIds);
        dto.setMathProblemIds(mathProblemIds);

        return dto;
    }
}