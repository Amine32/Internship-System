package ru.tsu.hits.season_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.season_service.dto.CreateUpdateInternshipSeasonDto;
import ru.tsu.hits.season_service.dto.InternshipSeasonDto;
import ru.tsu.hits.season_service.dto.converter.InternshipSeasonConverter;
import ru.tsu.hits.season_service.exception.InternshipSeasonNotFoundException;
import ru.tsu.hits.season_service.model.InternshipSeasonEntity;
import ru.tsu.hits.season_service.model.SeasonPhase;
import ru.tsu.hits.season_service.repository.InternshipSeasonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipSeasonService {

    private final InternshipSeasonRepository seasonRepository;
    private final InternshipSeasonConverter seasonConverter;

    public InternshipSeasonDto createInternshipSeason(CreateUpdateInternshipSeasonDto dto) {
        InternshipSeasonEntity entity = seasonConverter.convertToEntity(dto);
        InternshipSeasonEntity savedEntity = seasonRepository.save(entity);
        return seasonConverter.convertToDto(savedEntity);
    }

    public InternshipSeasonDto getInternshipSeasonById(String id) {
        InternshipSeasonEntity entity = seasonRepository.findById(id)
                .orElseThrow(() -> new InternshipSeasonNotFoundException("Internship season not found"));
        return seasonConverter.convertToDto(entity);
    }

    public InternshipSeasonDto updateInternshipSeason(String id, CreateUpdateInternshipSeasonDto dto) {
        InternshipSeasonEntity entity = seasonRepository.findById(id)
                .orElseThrow(() -> new InternshipSeasonNotFoundException("Internship season not found"));
        seasonConverter.updateEntityFromDto(dto, entity);
        InternshipSeasonEntity updatedEntity = seasonRepository.save(entity);
        return seasonConverter.convertToDto(updatedEntity);
    }

    public List<InternshipSeasonDto> getInternshipSeasonsByPhase(SeasonPhase phase) {
        List<InternshipSeasonEntity> entities = seasonRepository.findByPhase(phase);
        if (entities.isEmpty()) {
            throw new InternshipSeasonNotFoundException("No Internship Seasons found for phase " + phase);
        }
        return entities.stream()
                .map(seasonConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public InternshipSeasonEntity getUniqueInternshipSeasonInSearchPhase() {
        List<InternshipSeasonEntity> entities = seasonRepository.findByPhase(SeasonPhase.SEARCH);
        if (entities.isEmpty()) {
            throw new InternshipSeasonNotFoundException("No Internship Season in SEARCH phase found");
        }
        if (entities.size() > 1) {
            throw new IllegalStateException("More than one Internship Season in SEARCH phase exists");
        }
        return entities.get(0);
    }
}
