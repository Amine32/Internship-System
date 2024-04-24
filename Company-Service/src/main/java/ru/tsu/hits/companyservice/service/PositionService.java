package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.dto.CreateUpdatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.converter.PositionDtoConverter;
import ru.tsu.hits.companyservice.exception.PositionNotFoundException;
import ru.tsu.hits.companyservice.model.PositionEntity;
import ru.tsu.hits.companyservice.repository.PositionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionDtoConverter dtoConverter;

    public PositionDto createPosition(CreateUpdatePositionDto dto) {
        log.info("Creating new position");
        return saveAndConvertToDto(dtoConverter.convertToEntity(dto));
    }

    public PositionDto getPositionById(String id) {
        log.info("Fetching position with id: {}", id);
        PositionEntity position = fetchPositionEntity(id);
        return dtoConverter.convertToDto(position);
    }

    public Page<PositionDto> getAllPositions(Pageable pageable) {
        log.info("Fetching all positions");
        return positionRepository.findAll(pageable)
                .map(dtoConverter::convertToDto);
    }

    public Page<PositionDto> getPositionsByCompanyId(String companyId, Pageable pageable) {
        log.info("Fetching positions for company ID: {}", companyId);
        return positionRepository.findAllByCompanyId(companyId, pageable)
                .map(dtoConverter::convertToDto);
    }

    public PositionDto updatePosition(String id, CreateUpdatePositionDto dto) {
        log.info("Updating position with id: {}", id);
        PositionEntity existingPosition = fetchPositionEntity(id);
        existingPosition = updatePositionEntity(existingPosition, dto);
        return saveAndConvertToDto(existingPosition);
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

    private PositionDto saveAndConvertToDto(PositionEntity entity) {
        return dtoConverter.convertToDto(saveEntity(entity));
    }

    private PositionEntity saveEntity(PositionEntity entity) {
        return positionRepository.save(entity);
    }

    private PositionEntity updatePositionEntity(PositionEntity existingPosition, CreateUpdatePositionDto dto) {
        return dtoConverter.updateEntityFromDto(existingPosition, dto);
    }

    public PositionDto updateSearchPeriod(String id, String searchPeriodId) {
        PositionEntity position = positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException("Position not found with ID: " + id));
        position.setSearchPeriodId(searchPeriodId);
        PositionEntity updatedPosition = positionRepository.save(position);
        return dtoConverter.convertToDto(updatedPosition);
    }

    public Page<PositionDto> getPositionsBySearchPeriodId(String searchPeriodId, Pageable pageable) {
        return positionRepository.findAllBySearchPeriodId(searchPeriodId, pageable)
                .map(dtoConverter::convertToDto);
    }
}

