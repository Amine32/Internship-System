package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.stackservice.model.PositionEntity;
import ru.tsu.hits.stackservice.repository.PositionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public List<PositionEntity> getAllPositions() {
        return positionRepository.findAll();

    }

    public List<PositionEntity> getAllPositionsByIds(List<String> ids) {
        return  positionRepository.findAllById(ids);
    }
}
