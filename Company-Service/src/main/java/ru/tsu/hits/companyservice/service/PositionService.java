package ru.tsu.hits.companyservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.dto.CreateUpdatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.converter.PositionDtoConverter;
import ru.tsu.hits.companyservice.exception.PositionNotFoundException;
import ru.tsu.hits.companyservice.model.PositionEntity;
import ru.tsu.hits.companyservice.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionDtoConverter dtoConverter;

    public PositionDto createPosition(CreateUpdatePositionDto dto, HttpServletRequest request) {
        log.info("Creating new position");
        return saveAndConvertToDto(dtoConverter.convertToEntity(dto), request);
    }

    public PositionDto getPositionById(String id, HttpServletRequest request) {
        log.info("Fetching position with id: {}", id);
        PositionEntity position = fetchPositionEntity(id);
        return dtoConverter.convertToDto(position, request);
    }

    public List<PositionDto> getAllPositions(HttpServletRequest request) {
        log.info("Fetching all positions");
        return positionRepository.findAll()
                .stream()
                .map(entity -> dtoConverter.convertToDto(entity, request))
                .collect(Collectors.toList());
    }

    public List<String> getPositionsByCompanyId(String companyId) {
        List<PositionEntity> positionEntities = positionRepository.findByCompanyId(companyId);

        //convert PositionEntity to PositionDto
        List<String> positionIds = new ArrayList<>();
        for (PositionEntity positionEntity : positionEntities) {
            positionIds.add(positionEntity.getId());
        }

        return positionIds;
    }

    public PositionDto updatePosition(String id, CreateUpdatePositionDto dto, HttpServletRequest request) {
        log.info("Updating position with id: {}", id);
        PositionEntity existingPosition = fetchPositionEntity(id);
        existingPosition = updatePositionEntity(existingPosition, dto);
        return saveAndConvertToDto(existingPosition, request);
    }

    public void deletePosition(String id) {
        log.info("Deleting position with id: {}", id);
        positionRepository.delete(fetchPositionEntity(id));
    }

    public void decrementPlacesLeft(String id) {
        log.info("Decrementing places left for position with id: {}", id);
        PositionEntity existingPosition = fetchPositionEntity(id);
        existingPosition.setNumberOfPlacesLeft(Math.max(0, existingPosition.getNumberOfPlacesLeft() - 1));
        positionRepository.save(existingPosition);
    }

    private PositionEntity fetchPositionEntity(String id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException("Position not found"));
    }

    private PositionDto saveAndConvertToDto(PositionEntity entity, HttpServletRequest request) {
        return dtoConverter.convertToDto(saveEntity(entity), request);
    }

    private PositionEntity saveEntity(PositionEntity entity) {
        return positionRepository.save(entity);
    }

    private PositionEntity updatePositionEntity(PositionEntity existingPosition, CreateUpdatePositionDto dto) {
        return dtoConverter.updateEntityFromDto(existingPosition, dto);
    }
}

