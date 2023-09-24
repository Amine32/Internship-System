package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.dto.CreatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.UpdatePositionDto;
import ru.tsu.hits.companyservice.dto.converter.PositionDtoConverter;
import ru.tsu.hits.companyservice.exception.PositionNotFoundException;
import ru.tsu.hits.companyservice.model.PositionEntity;
import ru.tsu.hits.companyservice.repository.PositionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private final PositionDtoConverter dtoConverter;

    public PositionDto createPosition(CreatePositionDto createPositionDto) {
        PositionEntity newPosition = dtoConverter.convertToEntity(createPositionDto);
        positionRepository.save(newPosition);
        return dtoConverter.convertToDto(newPosition);
    }

    public PositionDto getPositionById(String id) {
        PositionEntity position = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
        return dtoConverter.convertToDto(position);
    }

    public List<PositionDto> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(dtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public PositionDto updatePosition(String id, UpdatePositionDto dto) {
        PositionEntity existingPosition = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));

        existingPosition.setTitle(dto.getTitle());
        existingPosition.setDescription(dto.getDescription());
        existingPosition.setRequirements(dto.getRequirements());
        existingPosition.setNumberOfPlaces(dto.getNumberOfPlaces());
        existingPosition.setSalaryRange(dto.getSalaryRange());

        positionRepository.save(existingPosition);
        return dtoConverter.convertToDto(existingPosition);
    }

    public void deletePosition(String id) {
        PositionEntity existingPosition = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
        positionRepository.delete(existingPosition);
    }
}

