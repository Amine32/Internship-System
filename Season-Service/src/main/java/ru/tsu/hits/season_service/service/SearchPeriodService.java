package ru.tsu.hits.season_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.season_service.dto.CreateUpdateSearchPeriodDto;
import ru.tsu.hits.season_service.dto.SearchPeriodDto;
import ru.tsu.hits.season_service.dto.converter.SearchPeriodConverter;
import ru.tsu.hits.season_service.exception.SearchPeriodNotFoundException;
import ru.tsu.hits.season_service.model.InternshipSeasonEntity;
import ru.tsu.hits.season_service.model.SearchPeriodEntity;
import ru.tsu.hits.season_service.repository.SearchPeriodRepository;

@Service
@RequiredArgsConstructor
public class SearchPeriodService {

    private final SearchPeriodRepository searchRepository;
    private final SearchPeriodConverter searchConverter;
    private final InternshipSeasonService internshipSeasonService;

    public SearchPeriodDto createSearchPeriod(CreateUpdateSearchPeriodDto dto) {
        SearchPeriodEntity entity = searchConverter.convertToEntity(dto);
        SearchPeriodEntity savedEntity = searchRepository.save(entity);
        return searchConverter.convertToDto(savedEntity);
    }

    public SearchPeriodDto getSearchPeriodById(String id) {
        SearchPeriodEntity entity = searchRepository.findById(id)
                .orElseThrow(() -> new SearchPeriodNotFoundException("Search period not found")); // Consider creating a custom exception
        return searchConverter.convertToDto(entity);
    }

    public String getCurrentActiveSearchPeriodId() {
        InternshipSeasonEntity currentSearchSeason = internshipSeasonService.getUniqueInternshipSeasonInSearchPhase();
        SearchPeriodEntity searchPeriod = searchRepository.findBySeasonId(currentSearchSeason.getId())
                .orElseThrow(() -> new SearchPeriodNotFoundException("No active search period found for the current SEARCH phase season"));

        return searchPeriod.getId();
    }
}
