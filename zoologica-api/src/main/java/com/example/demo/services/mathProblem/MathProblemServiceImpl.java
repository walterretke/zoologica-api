package com.example.demo.services.mathProblem;

import com.example.demo.dto.MathProblemDTO;
import com.example.demo.models.Cage;
import com.example.demo.models.MathProblem;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.MathProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MathProblemServiceImpl implements MathProblemService {

    private final CageRepository cageRepository;
    private final MathProblemRepository mathProblemRepository;

    @Override
    public MathProblemDTO create(MathProblemDTO dto) {
        MathProblem newMathProblem = new MathProblem();

        Cage cage = cageRepository.findById(dto.getCageId()).orElseThrow();

        newMathProblem.setCage(cage);
        newMathProblem.setType(dto.getType());
        newMathProblem.setQuestion(dto.getQuestion());
        newMathProblem.setCorrectAnswer(dto.getCorrectAnswer());

        newMathProblem.setProblemMatches(new ArrayList<>());

        MathProblem savedProblem = mathProblemRepository.save(newMathProblem);

        return convertToDTO(savedProblem);
    }

    private MathProblemDTO convertToDTO(MathProblem mathProblem) {
        MathProblemDTO dto = new MathProblemDTO();
        dto.setId(mathProblem.getId());
        dto.setType(mathProblem.getType());
        dto.setQuestion(mathProblem.getQuestion());
        dto.setCorrectAnswer(mathProblem.getCorrectAnswer());

        if (mathProblem.getCage() != null) {
            dto.setCageId(mathProblem.getCage().getId());
        }

        return dto;
    }
}
