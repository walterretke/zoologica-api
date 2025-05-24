package com.example.demo.services.animal;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.models.Animal;
import com.example.demo.models.Cage;
import com.example.demo.repositories.AnimalRepository;
import com.example.demo.repositories.CageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final CageRepository cageRepository;

    @Override
    public AnimalDTO create(AnimalDTO animal) {
        Cage cage = cageRepository.findById(animal.getCageId()).orElseThrow();

        Animal newAnimal = new Animal();
        newAnimal.setCage(cage);
        newAnimal.setType(animal.getType());
        newAnimal.setName(animal.getName());
        newAnimal.setBasePrice(animal.getBasePrice());

        Animal savedAnimal = animalRepository.save(newAnimal);

        return convertToDTO(savedAnimal);
    }

    public AnimalDTO convertToDTO(Animal animal) {
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