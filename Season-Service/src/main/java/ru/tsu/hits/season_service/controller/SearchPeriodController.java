package ru.tsu.hits.season_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.season_service.dto.CreateUpdateSearchPeriodDto;
import ru.tsu.hits.season_service.dto.SearchPeriodDto;
import ru.tsu.hits.season_service.service.SearchPeriodService;

@RestController
@RequestMapping("/api/searchPeriods")
@RequiredArgsConstructor
public class SearchPeriodController {

    private final SearchPeriodService searchService;

    @PostMapping
    public SearchPeriodDto createSearchPeriod(@RequestBody CreateUpdateSearchPeriodDto dto) {
        return searchService.createSearchPeriod(dto);
    }

    @GetMapping("/{id}")
    public SearchPeriodDto getSearchPeriodById(@PathVariable String id) {
        return searchService.getSearchPeriodById(id);
    }

    @GetMapping("/currentActiveId")
    public String getCurrentActiveSearchPeriodId() {
        return searchService.getCurrentActiveSearchPeriodId();
    }
}
