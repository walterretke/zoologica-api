package com.example.demo.services.cage;

import com.example.demo.dto.CageDTO;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CageServiceImpl implements CageService {

    private final CharacterRepository characterRepository;

    @Override
    public CageDTO create(CageDTO cage) {
        Cage newCage = new Cage();

        Character character = characterRepository.findById(cage.getCharacterId()).orElseThrow();

        newCage.setCharacter(character);

        return null;
    }

}