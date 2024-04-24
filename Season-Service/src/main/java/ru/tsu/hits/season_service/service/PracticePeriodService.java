package ru.tsu.hits.season_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.season_service.dto.CreateUpdatePracticePeriodDto;
import ru.tsu.hits.season_service.dto.PracticePeriodDto;
import ru.tsu.hits.season_service.dto.converter.PracticePeriodConverter;
import ru.tsu.hits.season_service.exception.PracticePeriodNotFoundException;
import ru.tsu.hits.season_service.model.PracticePeriodEntity;
import ru.tsu.hits.season_service.repository.PracticePeriodRepository;

@Service
@RequiredArgsConstructor
public class PracticePeriodService {

    private final PracticePeriodRepository practiceRepository;
    private final PracticePeriodConverter practiceConverter;

    public PracticePeriodDto createPracticePeriod(CreateUpdatePracticePeriodDto dto) {
        PracticePeriodEntity entity = practiceConverter.convertToEntity(dto);
        PracticePeriodEntity savedEntity = practiceRepository.save(entity);
        return practiceConverter.convertToDto(savedEntity);
    }

    public PracticePeriodDto getPracticePeriodById(String id) {
        PracticePeriodEntity entity = practiceRepository.findById(id)
                .orElseThrow(() -> new PracticePeriodNotFoundException("Practice period not found")); // Consider creating a custom exception
        return practiceConverter.convertToDto(entity);
    }

    public void activatePracticePeriod(String id) {
        PracticePeriodEntity entity = practiceRepository.findById(id)
                .orElseThrow(() -> new PracticePeriodNotFoundException("Practice period not found"));
        entity.setActive(true);
        practiceRepository.save(entity);
    }
}
