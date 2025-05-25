package com.example.demo.services.problemMatch;

import com.example.demo.dto.ProblemMatchDTO;
import com.example.demo.dto.SolveProblemRequest;
import com.example.demo.models.Character;
import com.example.demo.models.MathProblem;
import com.example.demo.models.ProblemMatch;
import com.example.demo.repositories.CharacterRepository;
import com.example.demo.repositories.MathProblemRepository;
import com.example.demo.repositories.ProblemMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemMatchServiceImpl implements ProblemMatchService {

    private final ProblemMatchRepository problemMatchRepository;
    private final CharacterRepository characterRepository;
    private final MathProblemRepository mathProblemRepository;

    @Override
    public ProblemMatchDTO solveProblem(SolveProblemRequest request) {
        Character character = characterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + request.getCharacterId()));

        MathProblem problem = mathProblemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Math problem not found with id: " + request.getProblemId()));

        boolean isCorrect = request.getGivenAnswer().equals(problem.getCorrectAnswer());

        ProblemMatch match = new ProblemMatch();
        match.setCharacter(character);
        match.setMathProblem(problem);
        match.setGivenAnswer(request.getGivenAnswer());
        match.setCorrect(isCorrect);
        match.setSolutionTime(request.getSolutionTime());
        match.setDateTime(LocalDateTime.now());

        if (isCorrect) {
            int coinsEarned = calculateCoins(request.getSolutionTime(), problem.getCage().getDifficultyLevel());
            match.setCoinsEarned(coinsEarned);

            character.setTotalCoins(character.getTotalCoins() + coinsEarned);
            characterRepository.save(character);
        } else {
            match.setCoinsEarned(0);
        }

        ProblemMatch savedMatch = problemMatchRepository.save(match);

        return convertToDTO(savedMatch);
    }

    @Override
    public List<ProblemMatchDTO> getPlayerStats(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<ProblemMatch> matches = problemMatchRepository.findByCharacterOrderByDateTimeDesc(character);

        return matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private int calculateCoins(Long solutionTime, Integer difficultyLevel) {
        // Fórmula de cálculo de moedas baseada no tempo e dificuldade
        int baseCoins = difficultyLevel * 10; // Nível 1 = 10, Nível 2 = 20, Nível 3 = 30

        // Bonus por velocidade (menos tempo = mais moedas)
        if (solutionTime <= 5000) { // 5 segundos
            return baseCoins + 15; // Bonus de 15 moedas
        } else if (solutionTime <= 10000) { // 10 segundos
            return baseCoins + 10; // Bonus de 10 moedas
        } else if (solutionTime <= 20000) { // 20 segundos
            return baseCoins + 5; // Bonus de 5 moedas
        } else {
            return baseCoins; // Apenas as moedas base
        }
    }

    private ProblemMatchDTO convertToDTO(ProblemMatch match) {
        ProblemMatchDTO dto = new ProblemMatchDTO();
        dto.setId(match.getId());
        dto.setCharacterId(match.getCharacter().getId());
        dto.setProblemId(match.getMathProblem().getId());
        dto.setGivenAnswer(match.getGivenAnswer());
        dto.setCorrect(match.getCorrect());
        dto.setCoinsEarned(match.getCoinsEarned());
        dto.setSolutionTime(match.getSolutionTime());
        dto.setDateTime(match.getDateTime());

        if (match.getCorrect()) {
            dto.setMessage("Parabéns! Resposta correta! Você ganhou " + match.getCoinsEarned() + " moedas!");
        } else {
            dto.setMessage("Resposta incorreta! A resposta correta era: " + match.getMathProblem().getCorrectAnswer() +
                    ". Você respondeu: " + match.getGivenAnswer());
        }

        return dto;
    }
}