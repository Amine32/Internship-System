package ru.tsu.hits.season_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.season_service.dto.CreateUpdateInternshipSeasonDto;
import ru.tsu.hits.season_service.dto.InternshipSeasonDto;
import ru.tsu.hits.season_service.service.InternshipSeasonService;

@RestController
@RequestMapping("/api/internshipSeasons")
@RequiredArgsConstructor
public class InternshipSeasonController {

    private final InternshipSeasonService seasonService;

    @PostMapping
    public InternshipSeasonDto createInternshipSeason(@RequestBody CreateUpdateInternshipSeasonDto dto) {
        return seasonService.createInternshipSeason(dto);
    }

    @GetMapping("/{id}")
    public InternshipSeasonDto getInternshipSeasonById(@PathVariable String id) {
        return seasonService.getInternshipSeasonById(id);
    }

    @PutMapping("/{id}")
    public InternshipSeasonDto updateInternshipSeason(@PathVariable String id, @RequestBody CreateUpdateInternshipSeasonDto dto) {
        return seasonService.updateInternshipSeason(id, dto);
    }
}
