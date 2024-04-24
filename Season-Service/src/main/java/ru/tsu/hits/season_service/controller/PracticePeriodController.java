package ru.tsu.hits.season_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.season_service.dto.CreateUpdatePracticePeriodDto;
import ru.tsu.hits.season_service.dto.PracticePeriodDto;
import ru.tsu.hits.season_service.service.PracticePeriodService;

@RestController
@RequestMapping("/api/practicePeriods")
@RequiredArgsConstructor
public class PracticePeriodController {

    private final PracticePeriodService practiceService;

    @PostMapping
    public PracticePeriodDto createPracticePeriod(@RequestBody CreateUpdatePracticePeriodDto dto) {
        return practiceService.createPracticePeriod(dto);
    }

    @GetMapping("/{id}")
    public PracticePeriodDto getPracticePeriodById(@PathVariable String id) {
        return practiceService.getPracticePeriodById(id);
    }

    @PutMapping("/{id}/activate")
    public void activatePracticePeriod(@PathVariable String id) {
        practiceService.activatePracticePeriod(id);
    }
}
