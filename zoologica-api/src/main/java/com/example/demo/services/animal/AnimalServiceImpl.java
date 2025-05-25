package com.example.demo.services.animal;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.models.Animal;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.repositories.AnimalRepository;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.CharacterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final CageRepository cageRepository;
    private final CharacterRepository characterRepository;

    @Override
    public AnimalDTO create(AnimalDTO animalDTO) {
        Cage cage = cageRepository.findById(animalDTO.getCageId())
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + animalDTO.getCageId()));

        Character character = cage.getCharacter();

        if (character.getTotalCoins() < animalDTO.getBasePrice()) {
            throw new RuntimeException("Insufficient coins. Required: " + animalDTO.getBasePrice() +
                    ", Available: " + character.getTotalCoins());
        }

        character.setTotalCoins(character.getTotalCoins() - animalDTO.getBasePrice());
        characterRepository.save(character);

        Animal newAnimal = new Animal();
        newAnimal.setCage(cage);
        newAnimal.setType(animalDTO.getType());
        newAnimal.setName(animalDTO.getName());
        newAnimal.setBasePrice(animalDTO.getBasePrice());

        Animal savedAnimal = animalRepository.save(newAnimal);

        return convertToDTO(savedAnimal);
    }


    @Override
    public List<AnimalDTO> findByCageId(Long cageId) {
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        List<Animal> animals = animalRepository.findByCage(cage);

        return animals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTO findById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with id: " + id));

        return convertToDTO(animal);
    }

    @Override
    public List<AnimalDTO> findByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<Animal> animals = character.getCages().stream()
                .flatMap(cage -> cage.getAnimals().stream())
                .collect(Collectors.toList());

        return animals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnimalDTO convertToDTO(Animal animal) {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setId(animal.getId());

        if (animal.getCage() != null) {
            animalDTO.setCageId(animal.getCage().getId());
        }

        animalDTO.setName(animal.getName());
        animalDTO.setType(animal.getType());
        animalDTO.setBasePrice(animal.getBasePrice());

        return animalDTO;
    }
}